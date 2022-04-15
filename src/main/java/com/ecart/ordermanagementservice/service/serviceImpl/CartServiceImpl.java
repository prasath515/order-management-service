package com.ecart.ordermanagementservice.service.serviceImpl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.ecart.ordermanagementservice.dto.ProductDto;
import com.ecart.ordermanagementservice.dto.ProductResponse;
import com.ecart.ordermanagementservice.exception.CustomException;
import com.ecart.ordermanagementservice.exception.RestConnectorException;
import com.ecart.ordermanagementservice.model.Cart;
import com.ecart.ordermanagementservice.model.CartEmptyAction;
import com.ecart.ordermanagementservice.model.CartItems;
import com.ecart.ordermanagementservice.model.CustomResponse;
import com.ecart.ordermanagementservice.model.Product;
import com.ecart.ordermanagementservice.model.ShippingCost;
import com.ecart.ordermanagementservice.model.Warehouse;
import com.ecart.ordermanagementservice.respository.CartRepository;
import com.ecart.ordermanagementservice.respository.ShippingCostRepository;
import com.ecart.ordermanagementservice.service.CartService;
import com.google.gson.Gson;

@Service
public class CartServiceImpl implements CartService {

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private ShippingCostRepository shippingCostRepository;

  @Value("${shipping.service.wareHouseDistance}")
  private String wareHouseDistanceURL;

  @Value("${shipping.service.productInfo}")
  private String productInfoURL;

  @Autowired
  private Gson gson;

  @Autowired
  private RestConnector connector;

  @Override
  public CustomResponse addToCart(Cart cart) {
    CustomResponse response = null;
    try {
      connector.serviceCall(productInfoURL + cart.getProduct_id(), HttpMethod.GET);
      Optional<Cart> existingCart = cartRepository.findByProductId(cart.getProduct_id());
      if (existingCart.isPresent()) {
        cart.setQuantity(cart.getQuantity() + existingCart.get().getQuantity());
        cart.setId(existingCart.get().getId());
      }
      cartRepository.save(cart);
      response = new CustomResponse("success", "Item has been added to cart");
    } catch (HttpClientErrorException ex) {
      ex.printStackTrace();
      throw new RestConnectorException(ex.getResponseBodyAsString());
    }

    return response;
  }

  @Override
  public CartItems fetchCartItems() {
    CartItems cartItems = new CartItems();
    Set<ProductDto> products = new HashSet<>();
    List<Cart> items = cartRepository.findAll();
    if (items.size() > 0) {
      cartItems.setStatus("success");
      cartItems.setMessage("Item available in the cart");
      for (Cart cart : items) {
        try {
          ProductResponse response = gson.fromJson(connector.serviceCall(productInfoURL + cart.getProduct_id(),
              HttpMethod.GET).getBody(),ProductResponse.class);
          Product product = response.getProduct();
          products.add(new ProductDto(cart.getProduct_id(), product.getDescription(), cart.getQuantity()));
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      cartItems.setItems(products);
    } else {
      cartItems.setMessage("Cart is empty.");
    }

    return cartItems;
  }

  @Override
  public CustomResponse clearCart(CartEmptyAction action) {
    if (action.getAction().equalsIgnoreCase("empty_cart")) {
      cartRepository.deleteAll();
      return new CustomResponse("success", "All items have been removed from the cart !");
    } else {
      throw new CustomException("error", "Invalid Action");
    }
  }

  @Override
  public CustomResponse calculatePrice(long postal_code) {
    Map<String, Double> map = getCartPriceAfterDiscountAndTotalWeight();
    if (!map.isEmpty()) {
      double shippingCost = findShippingCost(postal_code, map.get("totalWeight"));
      CustomResponse customResponse = new CustomResponse();
      customResponse.setStatus("success");
      customResponse.setMessage("Total value of your shopping cart is - $"
          + new DecimalFormat("0.00").format(map.get("totalPriceAfterDiscount") + shippingCost));
      return customResponse;
    } else {
      return new CustomResponse("success", "Cart is empty.");
    }
  }

  private Map<String, Double> getCartPriceAfterDiscountAndTotalWeight() {
    double totalWeight = 0;
    double totalPriceAfterDiscount = 0;
    Map<String, Double> map = new HashMap<>();
    List<Cart> items = cartRepository.findAll();
    if (items.size() > 0) {
      for (Cart cart : items) {
        try {
          Product product = gson.fromJson(connector.serviceCall(productInfoURL + cart.getProduct_id(),
              HttpMethod.GET).getBody(),ProductResponse.class).getProduct();
          double discount_price = 0;
          if (product.getDiscount_percentage() > 0) {
            discount_price = (product.getDiscount_percentage() / 100) * product.getPrice();
          }
          totalPriceAfterDiscount += ((product.getPrice() - discount_price) * cart.getQuantity());
          if (product.getWeight_in_grams() > 0) {
            totalWeight += (product.getWeight_in_grams() * cart.getQuantity());
          }
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      map.put("totalWeight", totalWeight / 1000);
      map.put("totalPriceAfterDiscount", totalPriceAfterDiscount);
    }

    return map;
  }

  protected double findShippingCost(long postal_code, double totalWeight) {
    long totalDistance = 0;
    double amount = 0;
    try {
      Warehouse warehouse = gson.fromJson(connector.serviceCall(wareHouseDistanceURL + postal_code,
          HttpMethod.GET).getBody(),Warehouse.class);
      if (warehouse.getDistance_in_kilometers() > 0) {
        totalDistance += warehouse.getDistance_in_kilometers();
      }
    } catch (HttpClientErrorException ex) {
      ex.printStackTrace();
      throw new RestConnectorException(ex.getResponseBodyAsString());
    }
    ShippingCost shippingCost = shippingCostRepository.findShippingAmount(totalWeight, totalDistance);
    if (shippingCost != null) {
      amount = shippingCost.getAmount();
    }

    return amount;
  }

}

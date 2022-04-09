package com.ecart.ordermanagementservice.service.serviceImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ecart.ordermanagementservice.dto.ProductDto;
import com.ecart.ordermanagementservice.dto.ProductResponse;
import com.ecart.ordermanagementservice.exception.CustomException;
import com.ecart.ordermanagementservice.exception.InvalidProductException;
import com.ecart.ordermanagementservice.model.Cart;
import com.ecart.ordermanagementservice.model.CartEmptyAction;
import com.ecart.ordermanagementservice.model.CartItems;
import com.ecart.ordermanagementservice.model.CustomResponse;
import com.ecart.ordermanagementservice.model.Product;
import com.ecart.ordermanagementservice.model.ShippingCharge;
import com.ecart.ordermanagementservice.model.Warehouse;
import com.ecart.ordermanagementservice.respository.CartRepository;
import com.ecart.ordermanagementservice.respository.ShippingChargeRepository;
import com.ecart.ordermanagementservice.service.CartService;


@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ShippingChargeRepository shippingChargeRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public CustomResponse addToCart(Cart cart) {
		CustomResponse response = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		try {
			restTemplate.exchange("https://e-commerce-api-recruitment.herokuapp.com/product/" + cart.getProduct_id(),
					HttpMethod.GET, entity, String.class).getBody();
			Optional<Cart> existingCart = cartRepository.findByProductId(cart.getProduct_id());
			if (existingCart.isPresent()) {
				cart.setQuantity(cart.getQuantity() + existingCart.get().getQuantity());
				cart.setId(existingCart.get().getId());
			}
			cartRepository.save(cart);
			response = new CustomResponse("success", "Item has been added to cart");
		} catch (Exception ex) {
			throw new InvalidProductException("error", "Invalid product id. Valid product id range is 100 to 110.");
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
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			for (Cart cart : items) {
				try {
					ResponseEntity<ProductResponse> response = restTemplate.exchange(
							"https://e-commerce-api-recruitment.herokuapp.com/product/" + cart.getProduct_id(),
							HttpMethod.GET, entity, ProductResponse.class);
					Product product = response.getBody().getProduct();
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
		if(action.getAction().equalsIgnoreCase("empty_cart")) {
			cartRepository.deleteAll();
			return new CustomResponse("success", "All items have been removed from the cart !");
		}else {
			throw new CustomException("error", "Invalid Action");
		}
	}

	@Override
	public CustomResponse calculatePrice(Warehouse warehouse) {
	    Map<String, Double> map = getCartPriceAfterDiscountAndTotalWeight();
	     if(!map.isEmpty()) {
		    double shippingCost = findShippingCost(warehouse.getPostal_code(),map.get("totalWeight"));
		    CustomResponse customResponse = new CustomResponse();
		    customResponse.setStatus("success");
		    customResponse.setMessage("Total value of your shopping cart is - $"+(map.get("totalPriceAfterDiscount")+shippingCost));
		    return customResponse;
	     }else {
	    	 return new CustomResponse("success","Cart is empty.");
	     }
		
	}
	
	private Map<String,Double> getCartPriceAfterDiscountAndTotalWeight(){
		double totalWeight = 0;
		double totalPriceAfterDiscount = 0;
		Map<String,Double> map = new HashMap<>();
		List<Cart> items = cartRepository.findAll();
		if (items.size() > 0) {
			for (Cart cart : items) {
				try {
					HttpHeaders headers = new HttpHeaders();
					headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
					HttpEntity<String> entity = new HttpEntity<String>(headers);
					ResponseEntity<ProductResponse> response = restTemplate.exchange(
							"https://e-commerce-api-recruitment.herokuapp.com/product/" + cart.getProduct_id(),
							HttpMethod.GET, entity, ProductResponse.class);
					Product product = response.getBody().getProduct();
					double price = product.getPrice() * cart.getQuantity();
					double discount_price = 0;
					if (product.getDiscount_percentage() > 0) {
						discount_price = ((product.getDiscount_percentage() / 100) * price);
					}
					totalPriceAfterDiscount += (price - discount_price);
					if(product.getWeight_in_grams()>0) {
						totalWeight += product.getWeight_in_grams(); 
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			map.put("totalWeight", totalWeight);
			map.put("totalPriceAfterDiscount", totalPriceAfterDiscount);
		}

		return map;
	}
	
	protected double findShippingCost(long postal_code, double totalWeight) {
		long totalDistance = 0;
		double shippingCost = 0;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			String url = "https://e-commerce-api-recruitment.herokuapp.com/warehouse/distance?postal_code="+postal_code;
			System.out.println(url);
			Warehouse response = restTemplate.exchange(url, HttpMethod.GET,entity, Warehouse.class).getBody();
			if (response.getDistance_in_kilometers() > 0) {
				totalDistance += response.getDistance_in_kilometers();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new CustomException("error", "Invalid postal code, valid ones are 465535 to 465545.");
		}

		List<ShippingCharge> charges = shippingChargeRepository.findAll();
		if (charges.size() > 0) {
	        for(ShippingCharge sc : charges) {
	        	if((totalWeight >= sc.getFrom_weight() && totalWeight <= sc.getTo_weight()) || (sc.getTo_weight() > 20.01 && totalWeight > 20.01) ) {
	        		if( (totalDistance >= sc.getFrom_distance() && totalDistance <= sc.getTo_distance()) || ( sc.getTo_distance() > 800 && totalDistance > 800)) {
	        			shippingCost = sc.getAmount();
	        			return shippingCost;
	        		}
	        	}
	        }
		}

		return shippingCost;
	}

}

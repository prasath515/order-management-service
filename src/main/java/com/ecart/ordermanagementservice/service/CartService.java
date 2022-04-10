package com.ecart.ordermanagementservice.service;

import com.ecart.ordermanagementservice.model.Cart;
import com.ecart.ordermanagementservice.model.CartEmptyAction;
import com.ecart.ordermanagementservice.model.CartItems;
import com.ecart.ordermanagementservice.model.CustomResponse;

public interface CartService {
	
	CustomResponse addToCart(Cart cart);
	
	CartItems fetchCartItems();
	
	CustomResponse clearCart(CartEmptyAction action);
	
	CustomResponse calculatePrice(long postal_code);

}

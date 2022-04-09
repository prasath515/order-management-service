package com.ecart.ordermanagementservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecart.ordermanagementservice.model.Cart;
import com.ecart.ordermanagementservice.model.CartEmptyAction;
import com.ecart.ordermanagementservice.model.CartItems;
import com.ecart.ordermanagementservice.model.CustomResponse;
import com.ecart.ordermanagementservice.model.Warehouse;
import com.ecart.ordermanagementservice.service.CartService;


@RestController
@RequestMapping("/cart")
public class CartContoller {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/item")
	private ResponseEntity<CustomResponse> addProductToCart(@RequestBody Cart cart){
		CustomResponse customResponse = cartService.addToCart(cart);
		return new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK);
	}
	
	@GetMapping("/items")
	private ResponseEntity<CartItems> getAllCartItems(){
		CartItems cartItems = cartService.fetchCartItems();
		return new ResponseEntity<CartItems>(cartItems, HttpStatus.OK);
	}
	
	@PostMapping("/items")
	private ResponseEntity<CustomResponse> emptyCart(@RequestBody CartEmptyAction action){
		return new ResponseEntity<CustomResponse>(cartService.clearCart(action), HttpStatus.OK);
	}
	
	@GetMapping("/checkout-value")
	private ResponseEntity<CustomResponse> getTotalAmount(@RequestBody Warehouse warehouse){
		CustomResponse cartItems = cartService.calculatePrice(warehouse);
		return new ResponseEntity<CustomResponse>(cartItems, HttpStatus.OK);
	}
}

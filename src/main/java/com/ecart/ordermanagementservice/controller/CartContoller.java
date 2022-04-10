package com.ecart.ordermanagementservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecart.ordermanagementservice.model.Cart;
import com.ecart.ordermanagementservice.model.CartEmptyAction;
import com.ecart.ordermanagementservice.model.CartItems;
import com.ecart.ordermanagementservice.model.CustomResponse;
import com.ecart.ordermanagementservice.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/cart")
@Tag(name = "Cart")
public class CartContoller {
	
	@Autowired
	private CartService cartService;
	
	@Operation(summary = "Add a item in cart")
	@PostMapping(value="/item")
	private ResponseEntity<CustomResponse> addProductToCart(@RequestBody Cart cart){
		CustomResponse customResponse = cartService.addToCart(cart);
		return new ResponseEntity<CustomResponse>(customResponse, HttpStatus.OK);
	}
	
	@Operation(summary = "Get all the items in cart")
	@GetMapping("/items")
	private ResponseEntity<CartItems> getAllCartItems(){
		CartItems cartItems = cartService.fetchCartItems();
		return new ResponseEntity<CartItems>(cartItems, HttpStatus.OK);
	}
	
	@Operation(summary = "Empty the cart")
	@PostMapping("/items")
	private ResponseEntity<CustomResponse> emptyCart(@RequestBody CartEmptyAction action){
		return new ResponseEntity<CustomResponse>(cartService.clearCart(action), HttpStatus.OK);
	}
	
	@Operation(summary = "Get total amount")
	@GetMapping(value = "/checkout-value")
	private ResponseEntity<CustomResponse> getTotalAmount(@RequestParam long postal_code){
		CustomResponse cartItems = cartService.calculatePrice(postal_code);
		return new ResponseEntity<CustomResponse>(cartItems, HttpStatus.OK);
	}
}

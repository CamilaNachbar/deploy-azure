package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@CrossOrigin
@RestController
public class ProductController {

	@Autowired
	ProductRepository product;

	@GetMapping("/products")
	public List<Product> getProducts() {
		return product.findAll();
	}

	@GetMapping("/")
	public String getWelcome() {
		return "DateTime: " + new Date();
	}

}

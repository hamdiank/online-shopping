package com.onepoint.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onepoint.test.entities.Product;
import com.onepoint.test.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/products")
	public List<Product> getAllproducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/{id}")
	public Product getProductById(@PathVariable(value = "id") Long productId) {
		return productService.getProductById(productId);

	}

	@PostMapping("/add")
	public Product createEmployee(@Valid @RequestBody Product product) {
		return productService.createProduct(product);
	}

	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable(value = "id") Long productId) {
		productService.deleteProduct(productId);
	}

	@GetMapping("/products/{param}")
	public List<Product> listSortedProducts(@RequestParam(value = "param") String sortAttribute) {
		return productService.getSortedProductList(sortAttribute);
	}

}

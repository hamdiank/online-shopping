package com.onepoint.test.services;

import java.util.List;

import com.onepoint.test.entities.Product;

public interface ProductService {

	public List<Product> getAllProducts();

	public Product getProductById(Long id);

	public Product createProduct(Product product);

	public void deleteProduct(Long id);

	public List<Product> getSortedProductList(String sortAttribute);
}

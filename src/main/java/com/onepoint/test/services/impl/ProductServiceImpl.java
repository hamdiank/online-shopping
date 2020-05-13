package com.onepoint.test.services.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onepoint.test.entities.Order;
import com.onepoint.test.entities.Product;
import com.onepoint.test.enumeration.ProductSortingAttributes;
import com.onepoint.test.exceptions.EntityNotFoundException;
import com.onepoint.test.repositories.ProductRepository;
import com.onepoint.test.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Long id) throws EntityNotFoundException {

		return productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Product.class, id.toString()));

	}

	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(Long id) throws EntityNotFoundException {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			productRepository.delete(product.get());
		} else {
			throw new EntityNotFoundException(Product.class, id.toString());
		}
	}

	/**
	 * @param sortAttribute
	 * @return sorted product list based on each product attribute
	 */
	@Override
	public List<Product> getSortedProductList(String sortAttribute) {
		if (ProductSortingAttributes.contains(sortAttribute)) {
			return getAllProducts().stream().sorted(Objects.requireNonNull(getSpecificComparator(sortAttribute)))
					.collect(Collectors.toList());
		} else {
			return getAllProducts();
		}
	}

	/**
	 * @param sortAttribute
	 * @return the specific comparator for each product attribute
	 */
	private Comparator<Product> getSpecificComparator(String sortAttribute) {
		for (ProductSortingAttributes attribute : ProductSortingAttributes.values()) {
			if (attribute.getAttributeLowerCase().equals(sortAttribute)) {
				return attribute.getComparator();
			}
		}
		return null;
	}

}

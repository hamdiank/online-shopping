package com.test.onepoint.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.onepoint.test.entities.Product;
import com.onepoint.test.exceptions.EntityNotFoundException;
import com.onepoint.test.repositories.ProductRepository;
import com.onepoint.test.services.ProductService;
import com.onepoint.test.services.impl.ProductServiceImpl;

@SpringBootTest(classes = ProductServiceImpl.class)
public class ProductServiceTest {

	@Autowired
	ProductService productservice;
	@MockBean
	ProductRepository productRepository;

	@Test
	public void getAllProducts_shouldReturnAllProducts() {
		// given
		Product product1 = new Product(1l, "Swiss cheese", new BigDecimal(5.70), new BigDecimal(2.50));
		Product product2 = new Product(2l, "strawberry", new BigDecimal(10.50), new BigDecimal(1.50));
		List<Product> givenProducts = Arrays.asList(product1, product2);
		// when
		when(productRepository.findAll()).thenReturn(givenProducts);

		List<Product> expectedProducts = productservice.getAllProducts();
		// then
		Assertions.assertEquals(givenProducts, expectedProducts);
	}

	@Test
	public void getProductById_shouldReturnProduct() {
		// given
		Long productId = 1l;
		Product givenProduct = new Product(productId, "strawberry", new BigDecimal(10.50), new BigDecimal(1.50));

		// when
		when(productRepository.findById(productId)).thenReturn(Optional.of(givenProduct));

		Product expectedProducts = productservice.getProductById(productId);

		// then
		Assertions.assertEquals(expectedProducts, givenProduct);
	}

	@Test
	public void getProductById_shouldThrowEntityNotFoundExceptionIfNotFound() {
		// given
		Long productId = 1l;

		Assertions.assertThrows(EntityNotFoundException.class, () -> {

			productservice.getProductById(productId);
		});
	}

	@Test
	public void deleteProduct_shouldBeExcuted() {
		// given
		Long productId = 1l;
		Product givenProduct = new Product(productId, "strawberry", new BigDecimal(10.50), new BigDecimal(1.50));

		// when
		when(productRepository.findById(productId)).thenReturn(Optional.of(givenProduct));

		productservice.deleteProduct(productId);

		// then
		verify(productRepository).delete(givenProduct);
	}

	@Test
	public void deleteProduct_shouldThrowEntityNotFoundExceptionIfNotFound() {
		// given
		Long productId = 1l;
		// when
		when(productRepository.findById(productId)).thenReturn(Optional.empty());

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			productservice.deleteProduct(productId);
		});
	}

	@Test
	public void createProduct_shouldReturnProduct() {

		// given
		Long productId = 1l;
		Product givenProduct = new Product(productId, "strawberry", new BigDecimal(10.50), new BigDecimal(1.50));
		// when
		when(productRepository.save(givenProduct)).thenReturn(givenProduct);

		Product expectedProduct = productservice.createProduct(givenProduct);
		// then
		Assertions.assertEquals(expectedProduct, givenProduct);
	}

	@Test
	public void getSortedProducts_shouldReturnProductsSortedWithName() {

		// given
		String attribute = "name";
		String ProductNameA = "A";
		String ProductNameB = "B";
		String ProductNameC = "C";
		Product productA = new Product(1l, ProductNameA, new BigDecimal(5.70), new BigDecimal(2.50));
		Product productB = new Product(2l, ProductNameB, new BigDecimal(17.50), new BigDecimal(3.80));
		Product productC = new Product(2l, ProductNameC, new BigDecimal(10.50), new BigDecimal(1.50));

		List<Product> nonSortedProductList = Arrays.asList(productC, productA, productB);
		// when
		when(productRepository.findAll()).thenReturn(nonSortedProductList);
		// when

		List<Product> expectedProducts = productservice.getSortedProductList(attribute);
		// then
		Assertions.assertEquals(ProductNameA, expectedProducts.get(0).getName());
		Assertions.assertEquals(ProductNameB, expectedProducts.get(1).getName());
		Assertions.assertEquals(ProductNameC, expectedProducts.get(2).getName());

	}

	@Test
	public void getSortedProducts_shouldReturnProductsSortedWithPrice() {

		// given
		String attribute = "price";
		BigDecimal ProductPriceA = new BigDecimal(5.70);
		BigDecimal ProductPriceB = new BigDecimal(10.50);
		BigDecimal ProductPriceC = new BigDecimal(37.80);
		Product productA = new Product(1l, "strawberry", ProductPriceA, new BigDecimal(2.50));
		Product productB = new Product(2l, "Swiss cheese", ProductPriceB, new BigDecimal(3.80));
		Product productC = new Product(2l, "fish", ProductPriceC, new BigDecimal(1.50));

		List<Product> nonSortedProductList = Arrays.asList(productB, productC, productA);
		// when
		when(productRepository.findAll()).thenReturn(nonSortedProductList);
		// when

		List<Product> expectedProducts = productservice.getSortedProductList(attribute);
		// then
		Assertions.assertEquals(ProductPriceA, expectedProducts.get(0).getPrice());
		Assertions.assertEquals(ProductPriceB, expectedProducts.get(1).getPrice());
		Assertions.assertEquals(ProductPriceC, expectedProducts.get(2).getPrice());

	}

	@Test
	public void getSortedProducts_shouldReturnProductsSortedWithWeight() {

		// given
		String attribute = "weight";
		BigDecimal ProductweightA = new BigDecimal(1.50);
		BigDecimal ProductweightB = new BigDecimal(2.80);
		BigDecimal ProductweightC = new BigDecimal(8.77);
		Product productA = new Product(1l, "strawberry", new BigDecimal(5.70), ProductweightA);
		Product productB = new Product(2l, "Swiss cheese", new BigDecimal(10.50), ProductweightB);
		Product productC = new Product(2l, "fish", new BigDecimal(37.80), ProductweightC);

		List<Product> nonSortedProductList = Arrays.asList(productB, productC, productA);
		// when
		when(productRepository.findAll()).thenReturn(nonSortedProductList);

		List<Product> expectedProducts = productservice.getSortedProductList(attribute);
		// then
		Assertions.assertEquals(ProductweightA, expectedProducts.get(0).getWeight());
		Assertions.assertEquals(ProductweightB, expectedProducts.get(1).getWeight());
		Assertions.assertEquals(ProductweightC, expectedProducts.get(2).getWeight());

	}

	@Test
	public void getSortedProducts_shouldReturnSameList() {

		// given
		String attribute = "notAttribute";
		String ProductNameA = "A";
		String ProductNameB = "B";
		String ProductNameC = "C";
		Product productA = new Product(1l, ProductNameA, new BigDecimal(5.70), new BigDecimal(2.50));
		Product productB = new Product(2l, ProductNameB, new BigDecimal(17.50), new BigDecimal(3.80));
		Product productC = new Product(2l, ProductNameC, new BigDecimal(10.50), new BigDecimal(1.50));

		List<Product> nonSortedProductList = Arrays.asList(productC, productA, productB);
		// when
		when(productRepository.findAll()).thenReturn(nonSortedProductList);
		// when

		List<Product> expectedProducts = productservice.getSortedProductList(attribute);
		// then
		Assertions.assertArrayEquals(expectedProducts.toArray(), nonSortedProductList.toArray());

	}
}

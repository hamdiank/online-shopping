package com.onepoint.test.enumeration;

import java.util.Comparator;

import com.onepoint.test.entities.Product;

public enum ProductSortingAttributes {
	NAME("name", Comparator.comparing(Product::getName)), PRICE("price", Comparator.comparing(Product::getPrice)),
	WEIGHT("weight", Comparator.comparing(Product::getWeight));

	private String attributeLowerCase;
	private Comparator<Product> comparator;

	ProductSortingAttributes(String attributeLowerCase, Comparator<Product> comparator) {
		this.attributeLowerCase = attributeLowerCase;
		this.comparator = comparator;
	}

	public String getAttributeLowerCase() {
		return attributeLowerCase;
	}

	public Comparator<Product> getComparator() {
		return comparator;
	}

	public static boolean contains(String value) {

		for (ProductSortingAttributes attribute : ProductSortingAttributes.values()) {
			if (attribute.getAttributeLowerCase().equals(value)) {
				return true;
			}
		}

		return false;
	}
}

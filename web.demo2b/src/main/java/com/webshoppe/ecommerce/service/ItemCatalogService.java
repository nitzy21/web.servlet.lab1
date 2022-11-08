package com.webshoppe.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import com.webshoppe.ecommerce.entity.Item;

public abstract class ItemCatalogService {

	
	public abstract List<Item> getItemCatalog();
	
	public abstract List<Item> getItemCatalog(BigDecimal minimumPrice, BigDecimal maximumPrice);
}

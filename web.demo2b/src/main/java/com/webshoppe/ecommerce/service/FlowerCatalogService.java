package com.webshoppe.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;
import com.webshoppe.ecommerce.entity.Item;
import com.webshoppe.ecommerce.exception.DataAccessException;
import com.webshoppe.ecommerce.exception.ServiceException;
import com.webshoppe.ecommerce.repository.ItemRepository;

public class FlowerCatalogService extends ItemCatalogService {

	private ItemRepository flowerRepository;

	private final static String FLOWER_FIND_ALL = "SELECT fid, fname, fdesc, fprice FROM flowersdetails";
	private final static String FLOWER_FIND_BY_PRICE = FLOWER_FIND_ALL + " WHERE fprice BETWEEN ? AND ?";
	
	private List<Item> flowers;

	public FlowerCatalogService(ItemRepository flowerRepository) {
		this.flowerRepository = flowerRepository;
	}

	@Override
	public List<Item> getItemCatalog() {
		try {
			return flowerRepository.findAll(FLOWER_FIND_ALL, "flowers", flowers);
		} catch (DataAccessException e) {
			throw ServiceException.instance(e.getMessage());
		}
	}

	@Override
	public List<Item> getItemCatalog(BigDecimal minimumPrice, BigDecimal maximumPrice) {
		try {
			return flowerRepository.findByPrice(minimumPrice, maximumPrice, FLOWER_FIND_BY_PRICE, "flowers");
		} catch (DataAccessException e) {
			throw ServiceException.instance(e.getMessage());
		}
	}

}

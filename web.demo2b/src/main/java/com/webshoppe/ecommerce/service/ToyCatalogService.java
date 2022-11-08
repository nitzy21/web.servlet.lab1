package com.webshoppe.ecommerce.service;

import java.math.BigDecimal;
import com.webshoppe.ecommerce.entity.Item;
import java.util.List;
import com.webshoppe.ecommerce.exception.DataAccessException;
import com.webshoppe.ecommerce.exception.ServiceException;
import com.webshoppe.ecommerce.repository.ItemRepository;

// you can put validations here. Service is more on business actions 
public class ToyCatalogService extends ItemCatalogService{
	
    private ItemRepository toyRepository;
    private List<Item> toys;
    
    private final static String TOY_FIND_ALL = "SELECT tid, tname, tdesc, tprice FROM ToysDetails";
    private final static String TOY_FIND_BY_PRICE = TOY_FIND_ALL + " WHERE tprice BETWEEN ? AND ?";
    
    public ToyCatalogService(ItemRepository toyRepository) {
        this.toyRepository = toyRepository;
    }
    

	@Override
	public List<Item> getItemCatalog() {
		  try {
	            return toyRepository.findAll(TOY_FIND_ALL, "toys", toys);
	        } catch (DataAccessException e) {
	            throw ServiceException.instance(e.getMessage());
	        }
	}

	@Override
	public List<Item> getItemCatalog(BigDecimal minimumPrice, BigDecimal maximumPrice) {
		 try {
	            return toyRepository.findByPrice(minimumPrice, maximumPrice, TOY_FIND_BY_PRICE, "toys");
	        } catch (DataAccessException e) {
	            throw ServiceException.instance(e.getMessage());
	        }
	}
}

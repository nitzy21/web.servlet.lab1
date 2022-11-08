package com.webshoppe.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;
import com.webshoppe.ecommerce.entity.Item;
import com.webshoppe.ecommerce.exception.DataAccessException;
import com.webshoppe.ecommerce.exception.ServiceException;
import com.webshoppe.ecommerce.repository.ItemRepository;

public class BooksCatalogService extends ItemCatalogService{

	private ItemRepository bookRepository;

	private final static String BOOK_FIND_ALL = "SELECT bid, title, bookdesc, bookprice FROM booksdetails";
	private final static String BOOK_FIND_BY_PRICE = BOOK_FIND_ALL + " WHERE bookprice BETWEEN ? AND ?";
	private List<Item> books;

	public BooksCatalogService(ItemRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public List<Item> getItemCatalog() {
		try {
			return bookRepository.findAll(BOOK_FIND_ALL, "books", books);
		} catch (DataAccessException e) {
			throw ServiceException.instance(e.getMessage());
		}
	}

	@Override
	public List<Item> getItemCatalog(BigDecimal minimumPrice, BigDecimal maximumPrice) {
		try {
			return bookRepository.findByPrice(minimumPrice, maximumPrice, BOOK_FIND_BY_PRICE, "books");
		} catch (DataAccessException e) {
			throw ServiceException.instance(e.getMessage());
		}
	}

}

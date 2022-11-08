package com.webshoppe.ecommerce.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webshoppe.ecommerce.entity.Item;
import com.webshoppe.ecommerce.jdbc.JdbcConnectionManager;
import com.webshoppe.ecommerce.repository.ItemRepository;
import com.webshoppe.ecommerce.service.BooksCatalogService;
import com.webshoppe.ecommerce.service.FlowerCatalogService;
import com.webshoppe.ecommerce.service.ToyCatalogService;

public class ItemCatalogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ToyCatalogService toyCatalogService;
	private FlowerCatalogService flowerCatalogService;
	private BooksCatalogService bookCatalogService;
	
	private PrintWriter out;

	@Override
	public void init() throws ServletException {
		final JdbcConnectionManager jdbcConnectionManager = new JdbcConnectionManager();
		final ItemRepository itemRepository = new ItemRepository(jdbcConnectionManager);
		toyCatalogService = new ToyCatalogService(itemRepository);
		flowerCatalogService = new FlowerCatalogService(itemRepository);
		bookCatalogService = new BooksCatalogService(itemRepository);
	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// StringBuilder is better kesa printwriter. Creates a string that is mutable.cConcatenation is not recommended.
		final StringBuilder stringBuilder = new StringBuilder();
		response.setContentType("text/html");	

		String paramType = request.getParameter("value");
		
		if (paramType.equals("toys")) {
			final List<Item> toys = toyCatalogService.getItemCatalog();
			if (toys.isEmpty()) {
				emptyMessage("<b>Toy Catalog Empty</b>", stringBuilder);
			} else {
				createTable(toys, stringBuilder);
			}
		} 
		else if (paramType.equals("books")) {
			final List<Item> books = bookCatalogService.getItemCatalog();
			if (books.isEmpty()) {
				emptyMessage("<b>Books Catalog Empty</b>", stringBuilder);
			} else {
				createTable(books, stringBuilder);
			}
		} else {
			final List<Item> flowers = flowerCatalogService.getItemCatalog();
			if (flowers.isEmpty()) {
				emptyMessage("<b>Flower Catalog Empty</b>", stringBuilder);
			} else {
				createTable(flowers, stringBuilder);
			}
		}
		
		this.close(response, stringBuilder);

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		final String minimumPriceParam = request.getParameter("minimum-price");
		final BigDecimal minimumPrice = new BigDecimal(minimumPriceParam);
		final String maximumPriceParam = request.getParameter("maximum-price");
		final BigDecimal maximumPrice = new BigDecimal(maximumPriceParam);

		final StringBuilder stringBuilder = new StringBuilder();

		String dropDownChoice = request.getParameter("category");

		if (dropDownChoice.equals("choice-toy")) {
			final List<Item> toys = toyCatalogService.getItemCatalog(minimumPrice, maximumPrice);

			if (toys.isEmpty()) {
				emptyMessage("<b>Cannot find toys that met the price range.</b>", stringBuilder);
			} else {
				createTable(toys, stringBuilder);
			}

		} else if (dropDownChoice.equals("choice-book")) {

			final List<Item> book = bookCatalogService.getItemCatalog(minimumPrice, maximumPrice);

			if (book.isEmpty()) {
				emptyMessage("<b>Cannot find books that met the price range.</b>", stringBuilder);
			} else {
				createTable(book, stringBuilder);
			}

		} else if (dropDownChoice.equals("choice-flower")) {

			final List<Item> flower = flowerCatalogService.getItemCatalog(minimumPrice, maximumPrice);

			if (flower.isEmpty()) {
				emptyMessage("<b>Cannot find flowers that met the price range.</b>", stringBuilder);
			} else {
				createTable(flower, stringBuilder);
			}
			
		}

		this.close(response, stringBuilder);

	}

	protected StringBuilder emptyMessage(String message, StringBuilder stringBuilder) {
		return stringBuilder.append(message);
	}
	
	protected void createTable(List<Item> item, StringBuilder stringBuilder) {
		stringBuilder.append("<table class='table'>");
		stringBuilder.append("<thead>");
		stringBuilder.append("<th scope='col'>ID</th>");
		stringBuilder.append("<th scope='col'>Name</th>");
		stringBuilder.append("<th scope='col'>Description</th>");
		stringBuilder.append("<th scope='col'>Price</th>");
		stringBuilder.append("</thead>");
		item.forEach(e -> {
			stringBuilder.append("<tr scope='row'>");
			stringBuilder.append("<td>").append(e.getId()).append("</td>");
			stringBuilder.append("<td>").append(e.getName()).append("</td>");
			stringBuilder.append("<td>").append(e.getDescription()).append("</td>");
			stringBuilder.append("<td>").append(e.getPrice()).append("</td>");
			stringBuilder.append("</tr>");
		});
		stringBuilder.append("</table>");
	}
	
	private void close(HttpServletResponse response, StringBuilder stringBuilder) {
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.println(stringBuilder.toString());
		out.flush();
		out.close();
	}
	
	
	
}

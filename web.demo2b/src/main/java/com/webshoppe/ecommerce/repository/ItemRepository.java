package com.webshoppe.ecommerce.repository;

import com.webshoppe.ecommerce.entity.Item;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.webshoppe.ecommerce.exception.DataAccessException;
import com.webshoppe.ecommerce.jdbc.JdbcConnectionManager;

// CRUD
public class ItemRepository {

	private JdbcConnectionManager jdbcConnectionManager;
	private Connection connection;
	private PreparedStatement findAllQuery;
	private ResultSet resultSet;

	// dependent to the connection manager.
	public ItemRepository(JdbcConnectionManager jdbcConnectionManager) {
		this.jdbcConnectionManager = jdbcConnectionManager;
	}

	public List<Item> findAll(String statement, String name, List<Item> items) {
		try {
			connection = jdbcConnectionManager.getConnection();
			findAllQuery = connection.prepareStatement(statement);
			resultSet = findAllQuery.executeQuery();

			items = new ArrayList<>();
			toIterateItems(resultSet, items);

			return items;

		} catch (Exception e) {
			throw DataAccessException.instance(String.format("failed_to_retrieve_%s, error:%s", name, e.getMessage()));
		} finally {
			this.close();
		}
	}

	public List<Item> findByPrice(BigDecimal minimumPrice, BigDecimal maximumPrice, String statement, String name) {
		try {
			connection = jdbcConnectionManager.getConnection();
			findAllQuery = connection.prepareStatement(statement);
			findAllQuery.setBigDecimal(1, minimumPrice);
			findAllQuery.setBigDecimal(2, maximumPrice);

			resultSet = findAllQuery.executeQuery();
			List<Item> items = new ArrayList<>();
			toIterateItems(resultSet, items);

			return items;
		} catch (Exception e) {
			throw DataAccessException.instance(String.format("failed_to_retrieve_%s_by_price error:%s", name, e.getMessage()));
		} finally {
			this.close();

		}
	}

	public void toIterateItems(ResultSet resultSet, List<Item> items) throws SQLException {
		while (resultSet.next()) {
			Item item = new Item(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
					resultSet.getBigDecimal(4));
			items.add(item);
		}
	}
	
	private void close() {
		 try {
	            if (findAllQuery != null) {
	            	findAllQuery.close();
	            }
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            logSQLException(e);
	        }
	}
	

    private void logSQLException(SQLException e) {
        System.out.println(String.format("sql_error=%s, error_code=%s", e.getMessage(), e.getErrorCode()));
    }

}

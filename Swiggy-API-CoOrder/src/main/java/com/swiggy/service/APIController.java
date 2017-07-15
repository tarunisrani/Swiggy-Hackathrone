package com.swiggy.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.model.RestaurantItems;
import com.swiggy.model.Restaurants;

@RestController
public class APIController {

	@RequestMapping(value = "/getOrderDetails", method = RequestMethod.GET, produces = "application/json")
	List<OrderDetails> orderDetailsItems(@RequestParam("order_id") long order_id) {
		List<OrderDetails> orderDetailsItems = new ArrayList<>();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/swiggy?" + "user=root&password=root");

			Statement st = connection.createStatement();

			String query = "SELECT od.order_id, od.restaurant_id, oi.item_id, oi.quantity, r.name, rm.item_name
				FROM swiggy.order_details od join swiggy.order_items oi on od.order_id = oi.order_id
				join swiggy.restaurants r on od.restaurant_id = r.id
				join swiggy.restaurant_menu rm on r.id = rm.restaurant_id
				where od.order_id = " + order_id;
			ResultSet od = st.executeQuery(query);

			while (od.next()) {
				OrderDetails item = new OrderDetails(od.getLong(1),od.getLong(2), od.getLong(3), od.getInt(4), od.getString(5), od.getString(6));
				orderDetailsItems.add(item);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderDetailsItems;
	}
}

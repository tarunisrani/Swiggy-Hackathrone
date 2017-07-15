package com.swiggy.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiggy.model.OrderAndRestaurants;
import com.swiggy.model.OrderDetails;
import com.swiggy.model.OrderItems;
import com.swiggy.model.RestaurantItems;
import com.swiggy.model.Restaurants;

@RestController
public class APIController {

	// to get nearby lat long in 1km radius-done
	double[] getLatLong(double start_latitude, double start_longitude) {

		double[] places = new double[8];
		int distance = 1000;
		int counter = 0;
		double[] degrees = { 0, 90, 180, 270 }; // degrees
		for (int i = 0; i < degrees.length; i++) {
			double dx, dy;

			dx = distance * Math.cos(Math.toRadians(degrees[i]));
			dy = distance * Math.sin(Math.toRadians(degrees[i]));

			double delta_longitude = dx / (111320 * Math.cos(Math.toRadians(start_latitude)));
			double delta_latitude = dy / 110540;

			double Final_longitude = start_longitude + delta_longitude;
			double Final_latitude = start_latitude + delta_latitude;
			places[counter++] = Final_latitude;
			places[counter++] = Final_longitude;

		}
		return places;
	}

	// API1-Fetch Nearby Restaurants-done

	@RequestMapping(value = "/getrestaurants", method = RequestMethod.GET, produces = "application/json")
	OrderAndRestaurants restaurants(@RequestParam("lat") double lat, @RequestParam("lng") double lng,
			@RequestParam("custId") long custId) {

		double places[] = getLatLong(lat, lng);

		List<Restaurants> ls = new ArrayList<>();
		OrderAndRestaurants os = new OrderAndRestaurants();

		Connection connection = null;
		try {
			// Class.forName("com.sap.db.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/swiggy?" + "user=root&password=root");

			Statement st = connection.createStatement();

			System.out.println(places[5]);
			System.out.println(places[1]);
			System.out.println(places[6]);
			System.out.println(places[2]);

			String query = "SELECT id, name, avg_rating from restaurants where lat>= " + places[6] + "and lat <="
					+ places[2] + " and lng>=" + places[5] + " and lng<=" + places[1]
					+ " order by avg_rating desc limit 50";
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Restaurants rest = new Restaurants(rs.getLong(1), rs.getString(2), rs.getFloat(3));
				ls.add(rest);
			}

			// create new customer lat long
			long orderId = (long) Math.floor((Math.random() % 10000000) * 10000000);
			query = "insert into order_cust values(" + orderId + ", " + custId + ", " + lat + ", " + lng + ")";
			Statement st1 = connection.createStatement();
			st1.execute(query);

			os.setLs(ls);
			os.setOrderId(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return os;
	}

	// API2-Fetch Restaurant Items -done

	@RequestMapping(value = "/getrestitems", method = RequestMethod.GET, produces = "application/json")
	List<RestaurantItems> restaurantItems(@RequestParam("rest_Id") long rest_Id) {
		List<RestaurantItems> ls = new ArrayList<>();
		Connection connection = null;
		try {
			// Class.forName("com.sap.db.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/swiggy?" + "user=root&password=root");

			Statement st = connection.createStatement();

			String query = "Select m.id, m.item_name, m.item_price from restaurants r inner join restaurant_menu m on r.id=m.restaurant_id and m.is_enabled=1 and r.id="
					+ rest_Id;
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				RestaurantItems item = new RestaurantItems(rs.getLong(1), rs.getString(2), rs.getDouble(3));
				ls.add(item);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ls;
	}

	// API3-Save order-working

	@RequestMapping(value = "/saveorder", method = RequestMethod.POST)
	String saveOrder(@RequestBody OrderDetails order) {

		Connection connection = null;
		try {

			connection = DriverManager.getConnection("jdbc:mysql://localhost/swiggy?" + "user=root&password=root");

			long orderId = order.getOrderId();
			List<OrderItems> oi = order.getOi();
			for (OrderItems items : oi) {
				Statement st = connection.createStatement();
				st.execute("insert into order_items(order_id, item_id, quantity) values(" + orderId + ", "
						+ items.getItemId() + ", " + items.getQty() + ")");
			}
			Statement st1 = connection.createStatement();
			ResultSet rs = st1.executeQuery(
					"select customer_id, customer_lat, customer_lng from order_cust where order_id=" + orderId);
			while (rs.next()) {
				Statement st2 = connection.createStatement();
				st2.execute(
						"INSERT INTO order_details_new(order_id, customer_id, customer_lat, customer_lng, restaurant_id) VALUES ("
								+ orderId + ", " + rs.getLong(1) + ", " + rs.getLong(2) + ", " + rs.getLong(3) + ", "
								+ order.getRestId() + ")");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Success";
	}

}

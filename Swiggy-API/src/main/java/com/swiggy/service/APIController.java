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

import com.swiggy.model.ItemSummary;
import com.swiggy.model.OrderAndRestaurants;
import com.swiggy.model.OrderDetails;
import com.swiggy.model.OrderItems;
import com.swiggy.model.OrderSummary;
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

	// API3-Save order-done

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
								+ orderId + ", " + rs.getLong(1) + ", " + rs.getDouble(2) + ", " + rs.getDouble(3)
								+ ", " + order.getRestId() + ")");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Success";
	}

	// API4- fetch second restaurant-done
	@RequestMapping(value = "/getsecondrestaurants", method = RequestMethod.GET, produces = "application/json")
	List<Restaurants> secondRestaurants(@RequestParam("orderId") long orderId) {

		double min_lat = 0, min_long = 0, max_lat = 0, max_long = 0;
		double rest_lat = 0, rest_long = 0, cust_lat = 0, cust_long = 0;

		List<Restaurants> ls = new ArrayList<>();

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/swiggy?" + "user=root&password=root");

			Statement st = connection.createStatement();

			String query = "SELECT r.lat, r.lng, o.customer_lat, o.customer_lng from order_details_new o inner join restaurants r on o.restaurant_id=r.id and o.order_id="
					+ orderId;
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				rest_lat = rs.getDouble(1);
				rest_long = rs.getDouble(2);
				cust_lat = rs.getDouble(3);
				cust_long = rs.getDouble(4);

			}

			if (rest_lat < cust_lat) {
				min_lat = rest_lat;
				max_lat = cust_lat;
			} else {
				min_lat = cust_lat;
				max_lat = rest_lat;
			}
			if (rest_long < cust_long) {
				min_long = rest_long;
				max_long = cust_long;
			} else {
				min_long = cust_long;
				max_long = rest_long;
			}

			Statement st1 = connection.createStatement();

			query = "SELECT id, name, avg_rating from restaurants where lat>= " + min_lat + "and lat <=" + max_lat
					+ " and lng>=" + min_long + " and lng<=" + max_long + " order by avg_rating desc limit 50";
			ResultSet rs1 = st1.executeQuery(query);

			while (rs1.next()) {
				Restaurants rest = new Restaurants(rs1.getLong(1), rs1.getString(2), rs1.getFloat(3));
				ls.add(rest);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ls;
	}

	// API5- fetch order summary-done
	@RequestMapping(value = "/ordersummary", method = RequestMethod.GET, produces = "application/json")
	List<OrderSummary> orderSummary(@RequestParam("orderId") long orderId) {
		List<OrderSummary> items = new ArrayList<>();
		List<ItemSummary> is = new ArrayList<>();
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/swiggy?" + "user=root&password=root");

			Statement st = connection.createStatement();

			String query = "SELECT o.restaurant_id, r.name from order_details_new o inner join restaurants r on o.restaurant_id=r.id and o.order_id="
					+ orderId;
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				OrderSummary os = new OrderSummary();
				float restAmt = 0;
				int restQty = 0;
				long rest_id = rs.getLong(1);
				String rest_name = rs.getString(2);
				Statement st1 = connection.createStatement();
				String query1 = "select o.item_id, m.item_name, m.item_price, o.quantity from order_items o inner join restaurant_menu m on o.item_id=m.id and m.restaurant_id="
						+ rest_id + " and o.order_id=" + orderId;
				ResultSet rs1 = st1.executeQuery(query1);
				is = new ArrayList<>();
				while (rs1.next()) {
					long item_id = rs1.getLong(1);
					String item_name = rs1.getString(2);
					float price = rs1.getFloat(3);
					int qty = rs1.getInt(4);

					restQty += qty;
					restAmt += (qty * price);
					ItemSummary item = new ItemSummary(item_id, item_name, price, qty);
					is.add(item);
				}
				os = new OrderSummary(rest_id, rest_name, restQty, restAmt, is);
				items.add(os);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

}

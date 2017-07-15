import java.util.*;


public class Distance {
	/*
	 * lat=asin(sin(lat1)*cos(d)+cos(lat1)*sin(d)*cos(tc))
 IF (cos(lat)=0)
    lon=lon1      // endpoint a pole
 ELSE
    lon=mod(lon1-asin(sin(tc)*sin(d)/cos(lat))+pi,2*pi)-pi
 ENDIF


 dx = R*cos(theta) 
   = 500 * cos(135 deg) 
   = -353.55 meters

dy = R*sin(theta) 
   = 500 * sin(135 deg) 
   = +353.55 meters

delta_longitude = dx/(111320*cos(latitude)) 
                = -353.55/(111320*cos(41.88592 deg))
                = -.004266 deg (approx -15.36 arcsec)

delta_latitude = dy/110540
               = 353.55/110540
               =  .003198 deg (approx 11.51 arcsec)

Final longitude = start_longitude + delta_longitude
                = -87.62788 - .004266
                = -87.632146

Final latitude = start_latitude + delta_latitude
               = 41.88592 + .003198
               = 41.889118


	 * */
	static double[] places = new double[8];
		
	public static void main(String args[]){
		double start_latitude = 12.9281026; //12.9281026, 77.6020568
		double start_longitude = 77.6020568;
		double distance = 500;
		
		/*double dx, dy, delta_lat, delta_long, R, theta;
		
		double degree = 180;
		
		dx = 500 * Math.cos(Math.toRadians(degree)); 
		dy = 500 * Math.sin(Math.toRadians(degree)); 


		double delta_longitude = dx/(111320*Math.cos(Math.toRadians(start_latitude))); 
		double delta_latitude = dy/110540;
		double Final_longitude = start_longitude + delta_longitude;
		double Final_latitude = start_latitude + delta_latitude;


		System.out.println("Final positions: "+ Final_latitude + ","+ Final_longitude);
		*/
		
		// getLatLong(start_latitude, start_longitude, 0, distance);
		// getLatLong(start_latitude, start_longitude, 90, distance);
		// getLatLong(start_latitude, start_longitude, 180, distance);
		// getLatLong(start_latitude, start_longitude, 270, distance);
		//getRestaurants(start_latitude, start_longitude);
		getLatLong(start_latitude, start_longitude);
		
		// Final positions: 12.9280026,77.60666516896325
		// Final positions: 12.932525849502442,77.6020568
		// Final positions: 12.9280026,77.59744843103675
		// Final positions: 12.923479350497557,77.6020568

		ArrayList<Restaurant> res = new ArrayList<Restaurant>();
		System.out.println(res);
		//String query = "SELECT Id, Name from swiggy.Restaurants where \
		// lat>= "+ places[5] + "and lat <=" + places[1] + \
		 //"lng >="+ places[6] + "and lng <=" + places[2];

		 //select id, name from swiggy.Restaurants where lat >= 
	}
	
	private static void getLatLong(double start_latitude, double start_longitude){
		int distance = 500;
		int counter = 0;
		double[] degrees = {0,90,180,270}; //degrees
		for(int i = 0; i<degrees.length; i++){
			double dx, dy, delta_lat, delta_long, R, theta;
		
			dx = distance * Math.cos(Math.toRadians(degrees[i])); 
			dy = distance * Math.sin(Math.toRadians(degrees[i])); 

			double delta_longitude = dx/(111320*Math.cos(Math.toRadians(start_latitude))); 
			double delta_latitude = dy/110540;
			
			double Final_longitude = start_longitude + delta_longitude;
			double Final_latitude = start_latitude + delta_latitude;
			places[counter++] = Final_latitude;
			places[counter++] = Final_longitude;
			System.out.println(counter + "Final positions: " +  places[counter-2] + ","+ places[counter-1]);
		}

		
	}

	private static void getRestaurants(double start_latitude, double start_longitude){
		
		//getLatLong(start_latitude, start_longitude);
	
	}
}

class Restaurant {
	private String name;
	private String id;
}
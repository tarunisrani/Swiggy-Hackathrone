
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

	public static void main(String args[]){
		double start_latitude = 12.9280026;
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
		
		getLatLong(start_latitude, start_longitude, 0, distance);
		getLatLong(start_latitude, start_longitude, 90, distance);
		getLatLong(start_latitude, start_longitude, 180, distance);
		getLatLong(start_latitude, start_longitude, 270, distance);
		
	}
	
	private static void getLatLong(double start_latitude, double start_longitude, double degree, double distance){
//		double start_latitude = 12.9280026;
//		double start_longitude = 77.6020568;
		double lat_2;
		double long_2;
		//		double lat_2 = Math.asin(Math.sin(lat_1)* Math.cos(a))

		double dx, dy, delta_lat, delta_long, R, theta;
		
//		double degree = 180;
		
		dx = distance * Math.cos(Math.toRadians(degree)); 
		dy = distance * Math.sin(Math.toRadians(degree)); 


		double delta_longitude = dx/(111320*Math.cos(Math.toRadians(start_latitude))); 
		double delta_latitude = dy/110540;
		double Final_longitude = start_longitude + delta_longitude;
		double Final_latitude = start_latitude + delta_latitude;


		System.out.println("Final positions: "+ Final_latitude + ","+ Final_longitude);
	}
}

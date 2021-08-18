import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import static java.lang.Math.*;

public class GPSPoint {
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	private static final double EARTH_RADIUS = 3958.75;
	
	public Double lat, lng;
	public Date date;
	
	public static GPSPoint getGPSPointFromList(List<String> points) throws ParseException {
		GPSPoint point = new GPSPoint();
		point.lat = Double.parseDouble(points.get(0));
		point.lng = Double.parseDouble(points.get(1));
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		point.date = formatter.parse(points.get(2));
		
		return point;
	}
	
	private static Double calcularDistanciaMetrosByLongLat(Double origemLong, Double longitude, Double origemLat, Double latitude) {
		double dLng = Math.toRadians(longitude - origemLong);
		double dLat = Math.toRadians(latitude - origemLat);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.cos(Math.toRadians(origemLat)) * Math.cos(Math.toRadians(latitude)) *
						Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = EARTH_RADIUS * c;

		return new Double(dist * 1609);  //conversao de milhas para metros
	}
	
	public Double calculateSpeedFromPrevious(GPSPoint previous) {
		
		double dist = calcularDistanciaMetrosByLongLat(previous.lng, lng, previous.lat, lat);
//		System.out.println(previous.lat + " " + previous.lng + " " + lat + " " + lng );
		double time_s = (date.getTime() - previous.date.getTime()) / 1000.0;
		if(time_s <= 0) {
			return 0.0;
		}
		
		double speed_mps = dist / time_s;
		double speed_kph = (speed_mps * 3600.0) / 1000.0;
		  
		return speed_kph;
	}

}

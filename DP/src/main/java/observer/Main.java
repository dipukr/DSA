package observer;

public class Main {
	public static void main(String[] args) throws Exception {
		var observer1 = new WeatherObserver();
		var observer2 = new WeatherObserver();
		var observer3 = new WeatherObserver();
		var station = new WeatherStation();
		station.attach(observer1);
		station.attach(observer2);
		station.attach(observer3);
		station.update(new Weather(43.54, 67.43));
	}
}
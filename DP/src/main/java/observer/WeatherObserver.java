package observer;

public class WeatherObserver implements Observer<Weather> {

	@Override
	public void update(Weather weather) {
		System.out.printf("Weather: %s%n", weather);
	}
}

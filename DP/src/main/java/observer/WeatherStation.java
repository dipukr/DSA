package observer;

import java.util.ArrayList;
import java.util.List;

public class WeatherStation implements Subject<Weather> {

	private List<Observer<Weather>> observers = new ArrayList<>();
	private Weather weather;
	
	public void update(Weather weather) {
		this.weather = weather;
		notifyObservers();
	}
	
	@Override
	public void attach(Observer<Weather> observer) {
		observers.add(observer);
	}

	@Override
	public void detach(Observer<Weather> observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		observers.forEach(ob -> ob.update(weather));
	}
}

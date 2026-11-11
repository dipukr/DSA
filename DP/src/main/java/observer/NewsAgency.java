package observer;

import java.util.ArrayList;
import java.util.List;

public class NewsAgency implements Subject<News> {

	private List<Observer<News>> observers = new ArrayList<>();
	private News latest;
	
	public void addNews(News news) {
		this.latest = news;
		notifyObservers();
	}
	
	@Override
	public void attach(Observer<News> observer) {
		observers.add(observer);
	}

	@Override
	public void detach(Observer<News> observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		observers.forEach(ob -> ob.update(latest));
	}
}

package observer;

public class NewsObserver implements Observer<News> {

	@Override
	public void update(News news) {
		System.out.printf("BREAKING: %s%n", news.news());
	}
}

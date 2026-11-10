package maven;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class HBUtils {
	private static SessionFactory sessionFactory;

	static {
		try {
			var configuration = new Configuration();
			var settings = new Properties();
			settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
			settings.put(Environment.URL, "jdbc:mysql://localhost:3306/testdb?createDatabaseIfNotExist=true");
			settings.put(Environment.USER, "root");
			settings.put(Environment.PASS, "P@55word");
			settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
			settings.put(Environment.SHOW_SQL, "true");
			settings.put(Environment.FORMAT_SQL, "true");
			settings.put(Environment.HBM2DDL_AUTO, "update");
			configuration.setProperties(settings);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}

	public static SessionFactory sessionFactory() {
		return sessionFactory;
	}
}

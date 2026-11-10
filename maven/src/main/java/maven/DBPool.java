package maven;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBPool {
	private static HikariDataSource dataSource;

	static {
		var config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/userdb?useSSL=false&serverTimezone=UTC");
		config.setUsername("root");
		config.setPassword("Root@123");

		config.setMaximumPoolSize(10);  // max connections in pool
		config.setMinimumIdle(2);       // keep 2 idle connections ready
		config.setIdleTimeout(30000);   // 30s before closing idle connections
		config.setMaxLifetime(1800000); // 30min max lifetime for a connection

		dataSource = new HikariDataSource(config);
	}
	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}

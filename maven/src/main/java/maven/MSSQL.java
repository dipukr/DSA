package maven;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

public class MSSQL {
	private static final String URL = "jdbc:sqlserver://103.25.173.113:14033;databaseName=myndprodbportal;"
			+ "encrypt=true;trustServerCertificate=true;sendStringParametersAsUnicode=false";

	private static final String USER = "sa";
	private static final String PASSWORD = "AQ!sw2DE#fr4GT%";

	private static final int TOTAL = 11000;
	private static final int BATCH_SIZE = 1000;
	private static final Random RANDOM = new Random(42);

	public static void main(String[] args) {
		String sql = "INSERT INTO dbo.BP_Invoice_Details (FactorId, InvoiceNumber, InvoiceAmount, ApproveAmt) "
				+ "VALUES (?, ?, ?, ?)";

		long start = System.currentTimeMillis();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
			conn.setAutoCommit(false);

			try (PreparedStatement ps = conn.prepareStatement(sql)) {
				for (int i = 1; i <= TOTAL; i++) {
					String invoiceNo = String.format("INV-%06d", i);
					BigDecimal amount = BigDecimal.valueOf(Math.round((50 + RANDOM.nextDouble() * 9950) * 100.0) / 100.0);

					ps.setLong(1, 166419L);
					ps.setString(2, invoiceNo);
					ps.setBigDecimal(3, amount);
					ps.setBigDecimal(4, amount);
					ps.addBatch();

					if (i % BATCH_SIZE == 0) {
						ps.executeBatch();
						conn.commit();
						System.out.println("Committed " + i + " / " + TOTAL);
					}
				}
				ps.executeBatch(); // flush remaining rows (11000 % 1000 = 0, but safe regardless)
				conn.commit();
			} catch (Exception e) {
				conn.rollback();
				throw e;
			}

			long secs = (System.currentTimeMillis() - start) / 1000;
			System.out.printf("Inserted %d records in %ds%n", TOTAL, secs);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
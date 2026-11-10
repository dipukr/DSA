package maven;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDB {
	public Set<String> findAllMeterNos(MongoCollection<Document> meterDetailsColl) {
		FindIterable<Document> data = meterDetailsColl.find();
		Set<String> meterNos = new TreeSet<>();
		for (Document document: data)
			meterNos.add(document.getString("meterNo"));
		return meterNos;
	}
	
	public void meterData(MongoCollection<Document> coll, Set<String> meterNos, String from, String to) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date fromDate = df.parse(from);
		Date toDate = df.parse(to);
		Bson dateFilter = Filters.and(Filters.gte("forDate", fromDate), 
				Filters.lte("forDate", toDate));
		Bson meterFilter = Filters.in("meterNo", meterNos);
		Bson filter = Filters.and(dateFilter, meterFilter);
		FindIterable<Document> meterData = coll.find(filter);
		for (Document doc: meterData)
			System.out.println(doc.toJson());
		System.out.println(meterNos);
	}

	public static void main(String[] args) throws Exception {
		String uri = "mongodb://samast_user:Samast%401212@192.168.101.24:27017/?authSource=samast";
		try (MongoClient mongoClient = MongoClients.create(uri)) {
			MongoDatabase database = mongoClient.getDatabase("samast");
			var meterDetailsColl = database.getCollection("SecureMeterDetails");
			var meterDataColl = database.getCollection("pnPR_VEELoadSuveyData");
			Set<String> meterNos = new MongoDB().findAllMeterNos(meterDetailsColl);
		}
	}
}

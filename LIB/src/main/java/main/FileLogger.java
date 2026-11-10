package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogger {
	private DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	private PrintWriter writer;

	public FileLogger(PrintWriter writer) {
		this.writer = writer;
	}
	
	public void info(String format, Object ...args) {
		log("INFO", format, args);
	}
	
	public void warn(String format, Object ...args) {
		log("WARN", format, args);
	}
	
	public void error(String format, Object ...args) {
		log("ERROR", format, args);
	}
	
	public void access(String format, Object ...args) {
		log("ACCESS", format, args);
	}
	
	public void log(String kind, String format, Object ...args) {
		var data = new StringBuilder();
		data.append(String.format("[%s: %s] ", formatter.format(new Date()), kind));
		data.append(String.format(format, args));
		data.append('\n');
		writer.write(data.toString());
		writer.flush();
	}
	
	public static FileLogger getLogger(String fileName) {
		return getLogger(new File(fileName));
	}
	
	public static FileLogger getLogger(File file) {
		try {
			FileWriter fileWriter = new FileWriter(file, true);
		 	PrintWriter writer = new PrintWriter(fileWriter, true);
			FileLogger fileLogger = new FileLogger(writer);
			return fileLogger;
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
			System.exit(0);
			return null;
		}
	}
}

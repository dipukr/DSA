package maven;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class Search {
	public Search(String projectDir, String needle) {
		File depsDir = new File(projectDir, "target/deps");
		System.out.println("Running Maven copy-dependencies...");
		if (runMavenCopy(projectDir) != 0) {
			System.out.println("Maven failed.");
			return;
		}
		System.out.println("Maven finished.");
		if (!depsDir.isDirectory()) {
			System.out.printf("No deps folder at %s%n", depsDir.getPath());
			return;
		}
		scanJars(depsDir, needle);
	}

	public int runMavenCopy(String projectDir) {
		var pb = new ProcessBuilder("mvn.cmd", "dependency:copy-dependencies",
				"-DoutputDirectory=target/deps");
		pb.directory(new File(projectDir));
		pb.inheritIO();

		try {
			Process process = pb.start();
			return process.waitFor();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("ERROR: could not start Maven.");
			return -1;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.out.println("Maven was interrupted.");
			return -1;
		}
	}

	public void scanJars(File depsDir, String needle) {
		File[] jars = depsDir.listFiles((dir, name) -> name.endsWith(".jar"));
		if (jars == null || jars.length == 0) {
			System.out.printf("No jars found in %s%n", depsDir.getPath());
			return;
		}
		Arrays.sort(jars);
		System.out.printf("Scanning %d jar(s) for %s...%n", jars.length, needle);

		int count = 0;
		for (File jar: jars) {
			if (jarContains(jar, needle)) {
				System.out.println(jar.getName());
				count++;
			}
		}
		System.out.printf("Matched: %d jar(s)%n", count);
	}

	public boolean jarContains(File jar, String needle) {
		try (var zf = new ZipFile(jar)) {
			return zf.stream()
				 .map(ZipEntry::getName)
				 .anyMatch(name -> name.endsWith(".class") && name.contains(needle));
		} catch (ZipException e) {
			System.out.printf("Skipping unreadable jar: %s%n", jar.getName());
		} catch (IOException e) {
			System.out.printf("Error reading %s : %s%n", jar.getName(), e.getMessage());
		}
		return false;
	}
	
	public static void main(String[] args) {
		String projectDir = "/C:/Users/dipu.kumar/TReDS";
		String needle = "javax/xml/parsers";
		new Search(projectDir, needle);
	}
}

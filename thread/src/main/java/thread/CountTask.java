package thread;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long> {
	private File file;

	public CountTask(File file) {
		this.file = file;
	}

	@Override
	public Long compute() {
		if (file.isFile()) {
			try {
				return Files.lines(file.toPath(), ISO_8859_1).count();
			} catch (IOException e) {
				return 0L;
			}
		} else if (file.isDirectory()) {
			List<CountTask> subTasks = new ArrayList<>();
			for (File elem: file.listFiles()) {
				CountTask task = new CountTask(elem);
				task.fork();
				subTasks.add(task);
			}
			long total = 0;
			for (CountTask task: subTasks)
				total += task.join();
			return total;
		} else return 0L;
	}

	public static void main(String[] args) {
		File root = new File("/home/dkumar/");
		ForkJoinPool pool = new ForkJoinPool();
		CountTask task = new CountTask(root);

		long start = System.currentTimeMillis();
		long lines = pool.invoke(task);
		long end = System.currentTimeMillis();

		System.out.printf("Lines: %d%n", lines);
		System.out.printf("Times: %d millis%n", end - start);
		
		pool.close();
	}
}
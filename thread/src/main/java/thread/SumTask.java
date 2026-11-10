package thread;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

@SuppressWarnings("serial")
public class SumTask extends RecursiveTask<Long> {
	private int[] data;
	private int start;
	private int end;
	
	public SumTask(int[] data, int start, int end) {
		this.data = data;
		this.start = start;
		this.end = end;
	}

	@Override
	public Long compute() {
		if (end - start <= 1_000_000) {
			long sum = 0;
			for (int i = start; i < end; i++)
				sum += data[i];
			return sum;
		} else {
			int mid = (start + end) / 2;
			SumTask left = new SumTask(data, start, mid); // asynchronous computation
			SumTask right = new SumTask(data, mid, end);  // asynchronous computation 
			left.fork();
			right.fork();
			return left.join() + right.join();
		}
	}
	
	public static void main(String[] args) {
		final int SZ = 2_000_000_000;
		int[] data = IntStream.range(0, SZ).toArray();
		Instant start = Instant.now();
		ForkJoinPool pool = new ForkJoinPool();
		Long ans = pool.invoke(new SumTask(data, 0, data.length));
		Instant end = Instant.now();
		System.out.println(ans);
		System.out.println(Duration.between(start, end).toMillis());
		pool.close();
		long sum = 0L;
		for (int i = 0; i < SZ; i++) sum += i;
		System.out.println(sum);
	}
}

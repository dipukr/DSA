package thread;

public class Worker extends Thread {

	private BlockQueue<Task> taskQueue;
	private boolean running = false;
	
	public Worker(BlockQueue<Task> taskQueue) {
		this.taskQueue = taskQueue;
	}
	
	@Override
	public void run() {
		while (running) {
			try {
				Task task = taskQueue.take();
				task.execute();
			} catch (Exception e) {
				running = false;
			}
		}
	}
	
	public void shutdown() {
		running = false;
		interrupt();
	}
}

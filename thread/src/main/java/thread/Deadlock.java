package thread;

public class Deadlock {
	public static class TaskOne implements Runnable {
		private Object resource1;
		private Object resource2;
		
		public TaskOne(Object resource1, Object resource2) {
			this.resource1 = resource1;
			this.resource2 = resource2;
		}
		
		@Override
		public void run() {
			synchronized (resource1) {
				System.out.println("Thread1: locked resource1");
				// Pause for a bit, simulating some file IO or something
				// Basically want to give the other thread a chance to run
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				synchronized (resource2) {
					System.out.println("Thread1: locked resource2");
				}
			}
		}
	}
	
	public static class TaskTwo implements Runnable {
		private Object resource1;
		private Object resource2;
		
		public TaskTwo(Object resource1, Object resource2) {
			this.resource1 = resource1;
			this.resource2 = resource2;
		}
		
		@Override
		public void run() {
			synchronized (resource2) {
				System.out.println("Thread2: locked resource2");
				try {
					Thread.sleep(100); 
				} catch (InterruptedException e) {}
				synchronized (resource1) {
					System.out.println("Thread2: locked resource1");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Object resource1 = "resource1";
		Object resource2 = "resource2";
		
		TaskOne taskOne = new TaskOne(resource1, resource2);
		TaskTwo taskTwo = new TaskTwo(resource1, resource2);

		Thread thread1 = new Thread(taskOne);
		Thread thread2 = new Thread(taskTwo);
		
		thread1.start();
		thread2.start();
	}
}
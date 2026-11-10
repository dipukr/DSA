package main;

import java.util.List;

public class Main {
	public static void main(String[] args) throws Exception {
		String fileName = "/home/dkumar/.keras/datasets/mnist.bin2";
		MNISTData data = new MNISTData(fileName);
		
		List<Mat> trainX = data.getTrainX();
		List<Mat> trainY = data.getTrainY();		
		List<Mat> testX = data.getTestX();
		List<Mat> testY = data.getTestY();
		
		NeuralNet net0 = new NNSigmoid(new int[]{784, 128, 64, 10});
		NeuralNet net = new MLPRelu(784, 32, 10);
		
		System.out.println("Training...");
		long start = System.currentTimeMillis();
		int matchedCount = 0;
		for (int epoch = 0; epoch < 10; epoch++) {
			matchedCount = 0;
			for (int m = 0; m < trainX.size(); m++) {
				Mat in = trainX.get(m);
				net.train(trainX.get(m), trainY.get(m));
				Mat out = net.predict(in);
				Mat target = trainY.get(m);
				if (CNNTools.argmax(out) == CNNTools.argmax(target))
					matchedCount += 1;
			}
			System.out.printf("Epoch %d completed with accuracy rate of %.2f.\n",
					epoch, ((double)matchedCount/(double)trainX.size()) * 100.0);
		}
		long end = System.currentTimeMillis();
		System.out.printf("Total training time: %d millis.\n", (end - start));
		System.out.println("Testing...");
		matchedCount = 0;
		for (int m = 0; m < testX.size(); m++) {
			Mat out = net.predict(testX.get(m));
			if (CNNTools.argmax(out) == CNNTools.argmax(testY.get(m)))
				matchedCount += 1;
		}
		System.out.printf("Total %d matched out of %d.\n", matchedCount, testX.size());
		System.out.printf("Accuracy rate is %.2f.\n", ((double)matchedCount/(double)testX.size()) * 100.0);
	}
}

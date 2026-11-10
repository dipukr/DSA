package main;

public class MLPRelu implements NeuralNet {

	private Mat w1, w2;
	private Mat b1, b2;
	private float lr = 0.001f;

	public MLPRelu(int inputSize, int hiddenSize, int outputSize) {
		w1 = Mats.random(hiddenSize, inputSize, -0.1f, 0.1f);
        b1 = Mats.random(hiddenSize, 1, -0.1f, 0.1f);
        w2 = Mats.random(outputSize, hiddenSize, -0.1f, 0.1f);
        b2 = Mats.random(outputSize, 1, -0.1f, 0.1f);
	}

	@Override
	public Mat predict(Mat input) {
		Mat z1 = Mats.add(Mats.dot(w1, input), b1);
		Mat a1 = Mats.relu(z1);
		Mat z2 = Mats.add(Mats.dot(w2, a1), b2);
		Mat a2 = Mats.softmax(z2);
		return a2;
	}

	@Override
	public void train(Mat input, Mat target) {
		Mat z1 = Mats.add(Mats.dot(w1, input), b1);
		Mat a1 = Mats.relu(z1);
		Mat z2 = Mats.add(Mats.dot(w2, a1), b2);
		Mat a2 = Mats.softmax(z2);

		Mat outputGrad = Mats.sub(a2, target);
		Mat deltaW2 = Mats.dot(outputGrad, Mats.transpose(a1));
		Mat hiddenError = Mats.dot(Mats.transpose(w2), outputGrad);
		Mat hiddenGrad = Mats.hadamard(Mats.drelu(z1), hiddenError);
		Mat deltaW1 = Mats.dot(hiddenGrad, Mats.transpose(input));
		
		Mats.scale(deltaW2, lr);
		Mats.scale(outputGrad, lr);
		Mats.scale(deltaW1, lr);
		Mats.scale(hiddenGrad, lr);

		w2 = Mats.sub(w2, deltaW2);
		b2 = Mats.sub(b2, outputGrad);
		w1 = Mats.sub(w1, deltaW1);
		b1 = Mats.sub(b1, hiddenGrad);
	}
}

package main;

public class MLPSigmoid implements NeuralNet {

	private Mat w1, w2;
	private Mat b1, b2;
	private float lr = 0.1f;

	public MLPSigmoid(int inputSize, int hiddenSize, int outputSize) {
		this.w1 = Mats.random(hiddenSize, inputSize, -1, 1);
		this.w2 = Mats.random(outputSize, hiddenSize, -1, 1);
		this.b1 = Mats.random(hiddenSize, 1, -1, 1);
		this.b2 = Mats.random(outputSize, 1, -1, 1);
	}

	@Override
	public Mat predict(Mat input) {
		Mat z1 = Mats.add(Mats.dot(w1, input), b1);
		Mat a1 = Mats.sigmoid(z1);
		Mat z2 = Mats.add(Mats.dot(w2, a1), b2);
		Mat a2 = Mats.sigmoid(z2);
		return a2;
	}

	@Override
	public void train(Mat input, Mat target) {
		Mat z1 = Mats.add(Mats.dot(w1, input), b1);
		Mat a1 = Mats.sigmoid(z1);
		Mat z2 = Mats.add(Mats.dot(w2, a1), b2);
		Mat a2 = Mats.sigmoid(z2);

		Mat outputError = Mats.sub(a2, target);
		Mat outputGradient = Mats.hadamard(outputError, Mats.dsigmoid(z2));
		Mat w2T = Mats.transpose(w2);
		Mat hiddenError = Mats.dot(w2T, outputGradient);
		Mat hiddenGradient = Mats.hadamard(hiddenError, Mats.dsigmoid(z1));

		Mat a1T = Mats.transpose(a1);
		Mat inputT = Mats.transpose(input);
		Mat dw2 = Mats.dot(outputGradient, a1T);
		Mat dw1 = Mats.dot(hiddenGradient, inputT);

		Mats.scale(dw2, lr);
		Mats.scale(dw1, lr);
		Mats.scale(outputGradient, lr);
		Mats.scale(hiddenGradient, lr);

		w2 = Mats.sub(w2, dw2);
		w1 = Mats.sub(w1, dw1);
		b2 = Mats.sub(b2, outputGradient);
		b1 = Mats.sub(b1, hiddenGradient);
	}
}
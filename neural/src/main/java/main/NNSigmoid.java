package main;

import java.util.ArrayList;
import java.util.List;

public class NNSigmoid implements NeuralNet {
	private Mat[] weights;
	private Mat[] biases;
	private float lr = 0.01f;

	public NNSigmoid(int[] topology) {
		this.weights = new Mat[topology.length - 1];
		this.biases = new Mat[topology.length - 1];
		for (int i = 1; i < topology.length; i++) {
			this.weights[i - 1] = Mats.random(topology[i], topology[i - 1], -1, 1);
			this.biases[i - 1] = Mats.random(topology[i], 1, -1, 1);
		}
	}

	@Override
	public Mat predict(Mat input) {
		Mat a = input;
		for (int i = 0; i < weights.length; i++) {
			Mat z = Mats.add(Mats.dot(weights[i], a), biases[i]);
			a = Mats.sigmoid(z);
		}
		return a;
	}

	@Override
	public void train(Mat input, Mat target) {
		Mat[] as = new Mat[weights.length + 1];
		Mat[] zs = new Mat[weights.length];
		
		// Forward pass
		Mat a = input;
		as[0] = a;
		for (int i = 0; i < weights.length; i++) {
			Mat z = Mats.add(Mats.dot(weights[i], a), biases[i]);
			zs[i] = z;
			a = Mats.sigmoid(z);
			as[i + 1] = a;
		}

		// Backward pass
		List<Mat> delta = new ArrayList<>();
		int last = weights.length - 1;

		Mat outputError = Mats.sub(as[last + 1], target);
		Mat outputGradient = Mats.hadamard(outputError, Mats.dsigmoid(as[last + 1]));
		delta.add(0, outputGradient);

		for (int l = last - 1; l >= 0; l--) {
			Mat wNextT = Mats.transpose(weights[l + 1]);
			Mat d = Mats.hadamard(Mats.dot(wNextT, delta.get(0)), Mats.dsigmoid(as[l + 1]));
			delta.add(0, d);
		}

		// Update weights and biases
		for (int i = 0; i < weights.length; i++) {
			Mat dw = Mats.dot(delta.get(i), Mats.transpose(as[i]));
			Mats.scale(dw, lr);
			Mats.scale(delta.get(i), lr);
			weights[i] = Mats.sub(weights[i], dw);
			biases[i] = Mats.sub(biases[i], delta.get(i));
		}
	}
}

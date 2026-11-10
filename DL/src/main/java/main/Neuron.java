package main;

public class Neuron {
	public static float sigmoid(float val) {
		return 1.0f / (1.0f + (float) Math.exp(-val));
	}
	
	public static float dsigmoid(float val) {
		val = sigmoid(val);
		return val * (1 - val);
	}
	
	public static float relu(float val) {
		return Math.max(0, val);
	}
	
	public static float drelu(float val) {
		return Math.max(0, val);
	}
}

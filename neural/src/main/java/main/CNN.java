package main;

public class CNN {

	private float[] dense1Weights = new float[128 * 13 * 13];
	private float[][] dense2Weights = new float[10][128];
	private float[][][] convFilters = new float[8][3][3];

	public CNN() {
		for (float[][] filter : convFilters)
			for (float[] row : filter)
				for (int i = 0; i < row.length; i++)
					row[i] = (float) (Math.random() - 0.5f);
		for (int i = 0; i < dense1Weights.length; i++)
			dense1Weights[i] = (float) (Math.random() - 0.5f);
		for (float[] row : dense2Weights)
			for (int i = 0; i < row.length; i++)
				row[i] = (float) (Math.random() - 0.5f);
	}

	public float[][][] conv(float[][] input, float[][][] filters) {
		int size = input.length - 2;
		float[][][] output = new float[filters.length][size][size];
		for (int f = 0; f < filters.length; f++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					float sum = 0;
					for (int x = 0; x < 3; x++)
						for (int y = 0; y < 3; y++)
							sum += filters[f][x][y] * input[i + x][j + y];
					output[f][i][j] = sum;
				}
			}
		}
		return output;
	}

	public float[][][] maxPool2x2(float[][][] input) {
		int size = input[0].length / 2;
		float[][][] output = new float[input.length][size][size];
		for (int c = 0; c < input.length; c++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					float max = Float.NEGATIVE_INFINITY;
					for (int di = 0; di < 2; di++)
						for (int dj = 0; dj < 2; dj++)
							max = Math.max(max, input[c][i * 2 + di][j * 2 + dj]);
					output[c][i][j] = max;
				}
			}
		}
		return output;
	}

	public float[][][] relu(float[][][] input) {
		for (int i = 0; i < input.length; i++)
			for (int j = 0; j < input[i].length; j++)
				for (int k = 0; k < input[i][j].length; k++)
					input[i][j][k] = Math.max(0, input[i][j][k]);
		return input;
	}

	public float[] dense(float[] input, float[][] weights) {
		float[] output = new float[weights.length];
		for (int i = 0; i < weights.length; i++)
			for (int j = 0; j < weights[i].length; j++)
				output[i] += input[j] * weights[i][j];
		return output;
	}

	public float[] flatten(float[][][] input) {
		float[] flattened = new float[input.length * input[0].length * input[0][0].length];
		int idx = 0;
		for (float[][] mat : input)
			for (float[] row : mat)
				for (float elem : row)
					flattened[idx++] = elem;
		return flattened;
	}

	public int predict(float[][] image28x28) {
		float[][][] conv1 = conv(image28x28, convFilters);
		float[][][] relu1 = relu(conv1);
		float[][][] pool1 = maxPool2x2(relu1);
		float[] flat = flatten(pool1);
		float[] dense1 = new float[128];
		for (int i = 0; i < 128; i++) {
			for (int j = 0; j < flat.length; j++)
				dense1[i] += flat[j] * dense1Weights[j];
			dense1[i] = Math.max(0, dense1[i]); // ReLU
		}
		float[] output = dense(dense1, dense2Weights);
		float[] probs = CNNTools.softmax(output);
		int predicted = 0;
		for (int i = 1; i < probs.length; i++)
			if (probs[i] > probs[predicted])
				predicted = i;
		return predicted;
	}

	public float[] copy(float[] arr) {
		float[] out = new float[arr.length];
		for (int i = 0; i < arr.length; i++)
			out[i] = arr[i];
		return out;
	}

	public float[][][] copy(float[][][] arr) {
		float[][][] out = new float[arr.length][][];
		for (int i = 0; i < arr.length; i++) {
			out[i] = new float[arr[i].length][];
			for (int j = 0; j < arr[i].length; j++)
				out[i][j] = arr[i][j].clone();
		}
		return out;
	}
}

package main;

public interface NeuralNet {
	Mat predict(Mat input);
	void train(Mat input, Mat target);
}

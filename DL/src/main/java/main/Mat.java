package main;

public class Mat {

	public float[][] data;

	public Mat(int rows, int cols) {
		this.data = new float[rows][cols];
		for (int i = 0; i < rows(); i++)
			for (int j = 0; j < cols(); j++)
				this.data[i][j] = (float) (2 * Math.random() - 1);
	}

	public Mat(float[][] data) {
		this.data = data;
	}

	public void init(float val) {
		for (int i = 0; i < rows(); i++)
			for (int j = 0; j < cols(); j++)
				data[i][j] = val;
	}
	
	public void random() {
		for (int i = 0; i < rows(); i++)
			for (int j = 0; j < cols(); j++)
				this.data[i][j] = (float) (2 * Math.random() - 1);
	}

	public int rows() {return data.length;}
	public int cols() {return data[0].length;}

	public String dim() {
		return String.format("(%d, %d)", rows(), cols());
	}

	@Override
	public String toString() {
		var text = new StringBuilder();
		for (int i = 0; i < rows(); i++) {
			text.append("[");
			for (int j = 0; j < cols(); j++) {
				text.append(data[i][j]);
				if (j < cols() - 1)
					text.append(" ");
			}
			text.append("]\n");
		}
		return text.toString();
	}
}

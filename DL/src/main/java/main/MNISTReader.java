package main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MNISTReader {

	private float[][][] dataX;
	private float[][] dataY;

	public MNISTReader(String fileName) throws Exception {
		final int dataCount = 70_000;
		final int rows = 28;
		final int cols = 28;
		System.out.append("hello");

		dataX = new float[dataCount][rows][cols];
		dataY = new float[dataCount][10];

		byte[] bytes = Files.readAllBytes(Paths.get(fileName));
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.order(ByteOrder.BIG_ENDIAN);

		for (int d = 0; d < dataCount; d++) {
			for (int i = 0; i < rows; i++)
				for (int j = 0; j < cols; j++)
					dataX[d][i][j] = (float) buffer.getDouble();
		}
		for (int d = 0; d < dataCount; d++) {
			int val = buffer.getInt();
			dataY[d][val] = 1.0F;
		}
	}

	public float[][][] getDataX() {return dataX;}
	public float[][] getDataY() {return dataY;}
}

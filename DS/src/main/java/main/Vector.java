package main;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Vector {

	private double[] data;

	public Vector(int size) {
		this.data = new double[size];
	}

	public Vector(double... data) {
		this.data = new double[data.length];
		for (int i = 0; i < data.length; i++)
			this.data[i] = data[i];
	}

	public void scale(double factor) {
		for (int i = 0; i < size(); i++)
			this.data[i] = factor * this.data[i];
	}

	public void add(Vector that) {
		verifyDim(this, that);
		for (int i = 0; i < size(); i++)
			this.data[i] += that.data[i];
	}

	public void sub(Vector that) {
		verifyDim(this, that);
		for (int i = 0; i < size(); i++)
			this.data[i] -= that.data[i];
	}

	public void mul(Vector that) {
		verifyDim(this, that);
		for (int i = 0; i < size(); i++)
			this.data[i] *= that.data[i];
	}

	public double dot(Vector that) {
		verifyDim(this, that);
		double result = 0.0;
		for (int i = 0; i < size(); i++)
			result += this.data[i] * that.data[i];
		return result;
	}

	public static Vector times(Vector a, double factor) {
		Vector vec = new Vector(a.size());
		for (int i = 0; i < a.size(); i++)
			vec.data[i] = factor * a.data[i];
		return vec;
	}

	public static Vector add(Vector a, Vector b) {
		verifyDim(a, b);
		Vector vec = new Vector(a.size());
		for (int i = 0; i < a.size(); i++)
			vec.data[i] = a.data[i] + b.data[i];
		return vec;
	}

	public static Vector sub(Vector a, Vector b) {
		verifyDim(a, b);
		Vector vec = new Vector(a.size());
		for (int i = 0; i < a.size(); i++)
			vec.data[i] = a.data[i] - b.data[i];
		return vec;
	}

	public static Vector mul(Vector a, Vector b) {
		verifyDim(a, b);
		Vector vec = new Vector(a.size());
		for (int i = 0; i < a.size(); i++)
			vec.data[i] = a.data[i] * b.data[i];
		return vec;
	}

	public static double dot(Vector a, Vector b) {
		verifyDim(a, b);
		double result = 0.0;
		for (int i = 0; i < a.size(); i++)
			result += a.data[i] * b.data[i];
		return result;
	}
	
	public static void verifyDim(Vector a, Vector b) {
		if (a.size() != b.size())
			Error.fatal("Dimension unmatched");
	}
	
	public Vector direction() {
		if (magnitude() == 0.0) Error.fatal("Zero vector has no direction.");
		return Vector.times(this, 1.0 / magnitude());
	}

	public double distanceTo(Vector that) {
		verifyDim(this, that);
		return Vector.sub(this, that).magnitude();
	}

	public double magnitude() {
		return Math.sqrt(dot(this));
	}
	
	public double cartesian(int i) {
		return data[i];
	}
	
	public int size() {
		return data.length;
	}

	@Override
	public String toString() {
		return Arrays.stream(data)
			.mapToObj(Double::toString)
			.collect(Collectors.joining(",", "[", "]"));
	}
}

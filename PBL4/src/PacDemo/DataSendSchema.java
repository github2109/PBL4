package PacDemo;

import java.io.Serializable;

public class DataSendSchema implements Serializable{
	int [][] Matrix;
	int Source;
	int Destination;
	public DataSendSchema(int [][] Matrix,int Source,int Destination) {
		this.Matrix = Matrix;
		this.Source = Source;
		this.Destination = Destination;
	}
	public int[][] getMatrix() {
		return Matrix;
	}
	public void setMatrix(int[][] matrix) {
		Matrix = matrix;
	}
	public int getSource() {
		return Source;
	}
	public void setSource(int source) {
		Source = source;
	}
	public int getDestination() {
		return Destination;
	}
	public void setDestination(int destination) {
		Destination = destination;
	}
	
}

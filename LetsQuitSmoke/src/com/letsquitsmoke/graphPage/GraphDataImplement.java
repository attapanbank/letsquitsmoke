package com.letsquitsmoke.graphPage;

import com.jjoe64.graphview.GraphViewDataInterface;

public class GraphDataImplement implements GraphViewDataInterface{

	private double x; // x value on graph
	private double y; // y value on graph
	
	public GraphDataImplement(double valX, double valY) {
		x = valX;
		y = valY;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

}

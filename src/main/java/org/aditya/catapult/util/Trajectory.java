package org.aditya.catapult.util;

/*
 * utility class to map colors to trajectories
 */
public class Trajectory {
	double angle;
	double power;
	int color;

	public Trajectory(double angle, double power, int color) {
		this.angle = angle;
		this.power = power;
		this.color = color;
	}

	public double getAngle() {
		return angle;
	}

	public double getPower() {
		return power;
	}

	public int getColor() {
		return color;
	}
}

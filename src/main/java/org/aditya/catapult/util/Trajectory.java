package org.aditya.catapult.util;

/*
 * utility class to map colors to trajectories
 */
public class Trajectory {
	double angle;
	double power;
	int color;
	double rotationAngle;

	public Trajectory(double angle, double power, int color, double rotationAngle) {
		super();
		this.angle = angle;
		this.power = power;
		this.color = color;
		this.rotationAngle = rotationAngle;
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

	public double getRotationAngle() {
		return rotationAngle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(angle);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + color;
		temp = Double.doubleToLongBits(power);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(rotationAngle);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trajectory other = (Trajectory) obj;
		if (Double.doubleToLongBits(angle) != Double.doubleToLongBits(other.angle))
			return false;
		if (color != other.color)
			return false;
		if (Double.doubleToLongBits(power) != Double.doubleToLongBits(other.power))
			return false;
		if (Double.doubleToLongBits(rotationAngle) != Double.doubleToLongBits(other.rotationAngle))
			return false;
		return true;
	}
	
	
	
}

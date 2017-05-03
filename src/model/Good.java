package model;

public class Good {
	
	/*
	 * 物品重量
	 */
	private int weight;
	
	/*
	 *物品价值
	 */
	private double value;

	public int getWeight() {
		return weight;
	}

	public double getValue() {
		return value;
	}

	public Good(int weight, double value) {
		this.weight = weight;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Good [weight=" + weight + ", value=" + value + "]";
	}

}

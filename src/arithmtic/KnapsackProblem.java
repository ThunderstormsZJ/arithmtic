package arithmtic;

import java.util.ArrayList;
import java.util.List;

import model.Good;

public class KnapsackProblem {
	/*
	 * 最优值矩阵 
	 * 	对应相应物品在小于totalWeight的最优解 
	 * 	行为所有物品,列为所有的weight
	 * 
	 */
	private double[][] bestValues;

	private double bestValue;

	private List<Good> bestGoods = new ArrayList<>();

	// 背包最大承重
	private int totalWeight;

	// 所有物品
	private Good[] goods;

	// 物品的总数量
	private int goodSize = 0;

	// 负无穷
	private double NEGATIVE = Double.NEGATIVE_INFINITY;

	public KnapsackProblem(int totalWeight, Good[] goods) {
		this.totalWeight = totalWeight;
		this.goods = goods;
		this.goodSize = goods.length;
		if (bestValues == null) {
			bestValues = new double[this.goodSize + 1][this.totalWeight + 1];
		}
	}

	/*
	 * 0-1背包问题（解法一） 
	 * 		1.当 gWeight > j:bestValues[i][j] = bestValue[i-1][j] 
	 * 		2.当 gWeight <= j:bestValues[i][j] = Max{bestValues[i-1][j], bestValues[i-1][j-gWeight]+gValue}
	 *
	 */
	public void solveZoreOnePackFirst() {
		for (int j = 0; j <= totalWeight; j++) {
			for (int i = 0; i <= goodSize; i++) {
				if (i == 0 || j == 0) {
					// 初始化。 若求恰好装满背包的最优解，初始化bestValue[0][0]=0, 此外i=0时都为负无穷
					bestValues[i][j] = 0;
				} else {
					if (j < goods[i - 1].getWeight()) {
						bestValues[i][j] = bestValues[i - 1][j];
					} else {
						int gWeight = goods[i - 1].getWeight();
						double gValue = goods[i - 1].getValue();
						bestValues[i][j] = Math.max(bestValues[i - 1][j], bestValues[i - 1][j - gWeight] + gValue);
					}
				}
			}
		}

		/*
		 *  生成结果
		 */
		bestValue = bestValues[goodSize][totalWeight];
		// 回溯找到所有物品的组合
		int tempWeight = totalWeight;
		for (int i = goodSize; i > 0; i--) {
			if (bestValues[i][tempWeight] > bestValues[i - 1][tempWeight]) {
				bestGoods.add(goods[i - 1]);
				tempWeight -= goods[i - 1].getWeight();
			}
			if (tempWeight <= 0)
				break;
		}
	}

	public void printBestValues() {
		System.out.println("最优解矩阵:");
		for (int i = 0; i < bestValues.length; i++) {
			for (int j = 0; j < bestValues[i].length; j++) {
				System.out.printf("%10.2f", bestValues[i][j]);
			}
			System.out.println();
		}
		System.out.printf("最优解:%.2f%n", bestValue);
		System.out.println("最优解背包:");
		for (int i = 0; i < bestGoods.size(); i++) {
			System.out.println(bestGoods.get(i));
		}
	}

}

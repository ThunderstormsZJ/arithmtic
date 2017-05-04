package arithmtic;

import java.util.ArrayList;
import java.util.List;

import model.Good;

abstract class BaseKnapsack {
	// 背包最大承重
	int totalWeight;

	// 所有物品
	Good[] goods;

	// 物品的总数量
	int goodSize = 0;

	// 负无穷
	double NEGATIVE = Double.NEGATIVE_INFINITY;

	/*
	 * 最优值矩阵 
	 * 	对应相应物品在小于totalWeight的最优解 
	 * 	行为所有物品,列为所有的weight
	 * 
	 */
	double[][] bestValues;

	// 保存最优解的一维数组
	double[] bestValuesArray;

	double bestValue;

	List<Good> bestGoods = new ArrayList<>();

	public BaseKnapsack(int totalWeight, Good[] goods) {
		this.totalWeight = totalWeight;
		this.goods = goods;
		this.goodSize = goods.length;
	}

	void printBestValues() {
		System.out.println("最优解矩阵:");
		for (int i = 0; i < bestValues.length; i++) {
			for (int j = 0; j < bestValues[i].length; j++) {
				System.out.printf("%10.2f", bestValues[i][j]);
			}
			System.out.println();
		}
	}

	void printBestValue() {
		System.out.printf("最优解:%.2f%n", bestValue);
	}

	void printBestGoods() {
		System.out.println("最优解背包:");
		for (int i = 0; i < bestGoods.size(); i++) {
			System.out.println(bestGoods.get(i));
		}
	}
	
	void printBestValuesArray() {
		System.out.println("最优解:");
		for (int i = 0; i < bestValuesArray.length; i++) {
			System.out.printf("%10.2f", bestValuesArray[i]);
		}
	}
}

class ZoreOneKnapsack extends BaseKnapsack {

	public ZoreOneKnapsack(int totalWeight, Good[] goods) {
		super(totalWeight, goods);
		if (bestValues == null) {
			bestValues = new double[this.goodSize + 1][this.totalWeight + 1];
		}
		if (bestValuesArray == null) {
			bestValuesArray = new double[this.totalWeight + 1];
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
		printFirstResult();
	}
	
	private void printFirstResult() {
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
		
		printBestValues();
		printBestValue();
		printBestGoods();
	}

	/*
	 * 	0-1背包问题（解法二）
	 * 		问题：对解法一进行空间复杂度上进行优化，使用一维数组代替二维数组
	 * 		思路:
	 * 			1.bestValues[i][j]的值通过一维数组保存,也就意味着第i次循环可以得到bestValuesArray[j]
	 * 			2.bestValues[i][j]主要是由{bestValue[i-1][j], bestValue[i-1][j-gWeight]}推出
	 * 			3.因此采用逆序的方式遍历totalWeight能够保证计算bestValuesArray[j]时事先已经得到bestValuesArray[j-gWeight]的值
	 * 		初始化问题:
	 * 			1.若需要得到恰好装满背包的最大物品价值,则需要将bestValuesArray[0]初始化为0,其他的初始化为负无穷
	 * 			2.若只是求出得到背包可以装下的最大的价值,则只需要将bestValuesArray全部初始化为0
	 */
	public void solveZoreOnePackSecond() {
		for (int i = 0; i < goodSize; i++) {
			double gValue = goods[i].getValue();
			int gWeight = goods[i].getWeight();
			for (int j = totalWeight; j >= gWeight; j--) {
				bestValuesArray[j] = Math.max(bestValuesArray[j], bestValuesArray[j - gWeight] + gValue);
			}
		}
		printBestValuesArray();
	}
}

public class KnapsackProblem {
	private ZoreOneKnapsack zoreOneKnapsack = null;

	public KnapsackProblem(int totalWeight, Good[] goods) {
		if (zoreOneKnapsack == null) {
			zoreOneKnapsack = new ZoreOneKnapsack(totalWeight, goods);
		}
	}

	public void zoreOneProblemPrint() {
		zoreOneKnapsack.solveZoreOnePackFirst();
		zoreOneKnapsack.solveZoreOnePackSecond();
	}

}

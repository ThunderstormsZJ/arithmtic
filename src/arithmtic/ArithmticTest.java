package arithmtic;

import model.Good;

public class ArithmticTest {

	public static void main(String[] args) {
		Good[] goods = new Good[]{
				new Good(3, 23), new Good(6, 103),
				new Good(7, 43), new Good(4, 89),
				new Good(9, 67), new Good(2, 63),
		};
		
		KnapsackProblem kProblem = new KnapsackProblem(30, goods);
		kProblem.zoreOneProblemPrint();
	}

}

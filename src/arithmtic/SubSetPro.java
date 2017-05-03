package arithmtic;

public class SubSetPro {
	// 一个包含n个元素的集合,求它的所有子集。
	public static void getPowerSet(int[] nArray, int nLength) {
		int mark = 0;
		int i = 0;
		int nStart = 0;
		// 可能会内存溢出
		int nEnd = (1 << nLength) - 1;
		for (mark = nStart; mark <= nEnd; mark++) {
			for (i = 0; i < nLength; i++) {
				if (((1 << i) & mark) != 0) // 该位有元素输出
				{
					System.out.printf("%d  ", nArray[i]);
				}
			}
			System.out.println();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int nArray[] = { 1, 3, 5 };
		getPowerSet(nArray, nArray.length);
	}

}

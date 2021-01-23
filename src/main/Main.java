package main;

/**
 * 
 * @author Gonçalves Perdigao Dylan
 * @author Gatti Khaireddine
 * 
 *         Where is Charlie Project
 */
public final class Main {

	/*
	 * This class is incomplete!!
	 * 
	 * You are expected to write at least one testcase for each required method. You
	 * will find some examples of testcases below.
	 */

	public static void main(String[] args) {
		// ImaheProcessing---------------
		testGetRed();
		testGetGreen();
		testGetBlue();
		testGetRGB();
		testGetRGBByGray();
		testGrayscale();
		// Collector--------------
		testFindNBest();
		// DistanceBasedSearch--------------
		testDistanceBasedSearch();
		// SimilarityBasedSearch--------------
		testSimilarityBasedSearch();
		findCharlie();

	}

	/*
	 * Tests for Class ImageProcessing
	 */
	// Test for red color
	public static void testGetRed() {
		int color = 0b11110000_00001111_01010101;
		int ref = 0b11110000;
		int red = ImageProcessing.getRed(color);
		if (red == ref) {
			System.out.println("getRed 			Test passed");
		} else {
			System.out.println("Test failed. Returned value = " + red + " Expected value = " + ref);
		}
	}

	// Test for green color
	public static void testGetGreen() {
		int color = 0b11110000_00001111_01010101;
		int ref = 0b00001111;
		int green = ImageProcessing.getGreen(color);
		if (green == ref) {
			System.out.println("getGreen 		Test passed");
		} else {
			System.out.println("Test failed. Returned value = " + green + " Expected value = " + ref);
		}
	}

	// Test for blue color
	public static void testGetBlue() {
		int color = 0b11110000_00001111_01010101;
		int ref = 0b01010101;
		int blue = ImageProcessing.getBlue(color);
		if (blue == ref) {
			System.out.println("getBlue 			Test passed");
		} else {
			System.out.println("Test failed. Returned value = " + blue + " Expected value = " + ref);
		}
	}

	// Test for rgb by color
	public static void testGetRGB() {
		int ref = 0b11110000_00001111_01010101;
		int red = ImageProcessing.getRed(ref);
		int green = ImageProcessing.getGreen(ref);
		int blue = ImageProcessing.getBlue(ref);
		int color = ImageProcessing.getRGB(red, green, blue);
		if (color == ref) {
			System.out.println("getRGB 			Test passed");
		} else {
			System.out.println("Test failed. Returned value = " + color + " Expected value = " + ref);
		}
	}

	// Test for rgb by gray
	public static void testGetRGBByGray() {
		int color = 0b11110000_00001111_01010101;
		int ref = 7434609;
		double gray = ImageProcessing.getGray(color);
		int rgbColor = ImageProcessing.getRGB(gray);
		if (rgbColor == ref) {
			System.out.println("getGray & getRGB 	Test passed");
		} else {
			System.out.println("Test failed. Returned value = " + color + " Expected value = " + ref);
		}
	}

	public static void testGrayscale() {
		System.out.println("Test Grayscale");
		int[][] image = Helper.read("images/food.png");
		// int[][] image = Helper.read("images/charlie_beach.png");
		// System.out.println(Arrays.deepToString(image));
		double[][] gray = ImageProcessing.toGray(image);
		// System.out.println(Arrays.deepToString(gray));
		Helper.show(ImageProcessing.toRGB(gray), "test bw");
	}
	/*
	 * Tests for Class Collector
	 */

	public static void testFindNBest() {
		System.out.println("Test findNBest");
		double[][] t = new double[][] { { 20, 30, 10, 50, 32 }, { 28, 39, 51, 78, 91 } };
		int[][] coords = Collector.findNBest(8, t, true);
		for (int[] a : coords) {
			int r = a[0];
			int c = a[1];
			switch (c) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				if (r == 0 || r == 1) {
					System.out.print("Row=" + r + " Col=" + c + " Val=" + t[r][c]);
					System.out.println("      findBest Test passed");
				} else {
					System.out.print("Row=" + r + " Col=" + c + " Val=" + t[r][c]);
					System.out.println("Test failed");
				}
				break;
			default:
				System.out.print("Row=" + r + " Col=" + c + " Val=" + t[r][c]);
				System.out.println("Test failed");
			}
		}
	}

	/*
	 * Tests for Class DistanceBasedSearch
	 */

	public static void testDistanceBasedSearch() {
		System.out.println("Test DistanceBasedSearch");

		int[][] food = Helper.read("images/food.png");
		int[][] onions = Helper.read("images/onions.png");

		double[][] distance = DistanceBasedSearch.distanceMatrix(onions, food);
		Helper.show(ImageProcessing.matrixToRGBImage(distance, 0, 255), "Distance");

		int[] p = Collector.findBest(distance, true);

		Helper.drawBox(p[0], p[1], onions[0].length, onions.length, food);
		Helper.show(food, "Found!");

	}

	/*
	 * Tests for Class SimilarityBasedSearch
	 */

	public static void testSimilarityBasedSearch() {
		System.out.println("Test SimilarityBasedSearch");
		int[][] food = Helper.read("images/food.png");
		int[][] onions = Helper.read("images/onions.png");
		double[][] foodGray = ImageProcessing.toGray(food);
		double[][] onionsGray = ImageProcessing.toGray(onions);
		double[][] similarity = SimilarityBasedSearch.similarityMatrix(onionsGray, foodGray);
		int[][] best = Collector.findNBest(8, similarity, false);
		for (int[] a : best) {
			int r = a[0];
			int c = a[1];
			Helper.drawBox(r, c, onions[0].length, onions.length, food);
		}
		Helper.show(food, "Found again!");
	}

	public static void findCharlie() {
		System.out.println("Find Charlie");
		int[][] beach = Helper.read("images/charlie_beach.png");
		int[][] charlie = Helper.read("images/charlie.png");
		double[][] beachGray = ImageProcessing.toGray(beach);
		double[][] charlieGray = ImageProcessing.toGray(charlie);

		System.out.println("Compute Similarity Matrix: expected time about 2 min");
		double[][] similarity = SimilarityBasedSearch.similarityMatrix(charlieGray, beachGray);

		System.out.println("Find N Best");
		int[] best = Collector.findBest(similarity, false);
		double max = similarity[best[0]][best[1]];

		Helper.show(ImageProcessing.matrixToRGBImage(similarity, -1, max), "Similarity");

		Helper.drawBox(best[0], best[1], charlie[0].length, charlie.length, beach);
		System.out.println("drawBox at (" + best[0] + "," + best[1] + ")");
		Helper.show(beach, "Found again!");
	}

}

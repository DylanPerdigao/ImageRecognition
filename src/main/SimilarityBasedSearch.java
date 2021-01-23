package main;

public class SimilarityBasedSearch {

	/**
	 * Computes the mean value of a gray-scale image given as a 2D array
	 * 
	 * @param image
	 *            : a 2D double array, the gray-scale Image
	 * @return a double value between 0 and 255 which is the mean value
	 */
	public static double mean(double[][] image) {

		assert image != null;
		
		double somme = 0.0;
		int width = image[0].length;
		int height = image.length;
		
		/*	on parcourt l'image
		 * 	on fait la moyenne des niveaux de gris
		 */
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				somme += image[row][col];
			}
		}
		return somme / (height * width);
	}

	public static double windowMean(double[][] matrix, int row, int col, int width, int height) {
		assert row >= 0;
		assert col >= 0;
		assert width >= 0;
		assert height >= 0;

		double somme = 0.0;
		
		/*	on parcourt une portion de l'image en row, col jusqu'à la taille du motif
		 * 	on fait la moyenne des niveaux de gris
		 */
		
		for (int i = row; i < width; i++) {
			for (int j = col; j < height; j++) {
				somme = matrix[i][j];
			}
		}
		return somme / (width * height);
	}

	/**
	 * Computes the Normalized Cross Correlation of a gray-scale pattern if
	 * positioned at the provided row, column-coordinate in a gray-scale image
	 * 
	 * @param row
	 *            : a integer, the row-coordinate of the upper left corner of the
	 *            pattern in the image.
	 * @param column
	 *            : a integer, the column-coordinate of the upper left corner of the
	 *            pattern in the image.
	 * @param pattern
	 *            : an 2D array of doubles, the gray-scale pattern to find
	 * @param image
	 *            : an 2D array of double, the gray-scale image where to look for
	 *            the pattern
	 * @return a double, the Normalized Cross Correlation value at position (row,
	 *         col) between the pattern and the part of the base image that is
	 *         covered by the pattern, if the pattern is shifted by x and y. should
	 *         return -1 if the denominator is 0
	 */
	public static double normalizedCrossCorrelation(int row, int col, double[][] pattern, double[][] image) {
		assert pattern != null;
		assert image != null;
		assert row >= 0;
		assert col >= 0;

		double somme1 = 0.0;
		double somme2 = 0.0;
		double somme3 = 0.0;
		int patternWidth = pattern[0].length;
		int patternHeight = pattern.length;

		double meanPattern = mean(pattern);
		double meanImage = windowMean(image, row, col, patternHeight, patternWidth);
		
		// on parcourt une portion de l'image de la taille du motif pour appliquer la formule du pdf
		for (int i = 0; i < patternHeight; i++) {
			for (int j = 0; j < patternWidth; j++) {

				somme1 += (image[i + row][j + col] - meanImage) * (pattern[i][j] - meanPattern);
				somme2 += Math.pow((image[i + row][j + col] - meanImage), 2);
				somme3 += Math.pow((pattern[i][j] - meanPattern), 2);
			}
		}
		double denominateur = Math.sqrt(somme2 * somme3);
		
		//on vérifie la valeur du dénominateur pour éviter la division par 0
		if (denominateur != 0) {
			double NCC = somme1 / denominateur;
			return NCC;
		} else {
			return -1;
		}
	}

	/**
	 * Compute the similarityMatrix between a gray-scale image and a gray-scale
	 * pattern
	 * 
	 * @param pattern
	 *            : an 2D array of doubles, the gray-scale pattern to find
	 * @param image
	 *            : an 2D array of doubles, the gray-scale image where to look for
	 *            the pattern
	 * @return a 2D array of doubles, containing for each pixel of a original
	 *         gray-scale image, the similarity (normalized cross-correlation)
	 *         between the image's window and the pattern placed over this pixel
	 *         (upper-left corner)
	 */
	public static double[][] similarityMatrix(double[][] pattern, double[][] image) {

		assert pattern != null;
		assert image != null;

		int imageWidth = image[0].length;
		int imageHeight = image.length;
		int patternWidth = pattern[0].length;
		int patternHeight = pattern.length;
		int matrixWidth = imageWidth - patternWidth;
		int matrixHeight = imageHeight - patternHeight;

		double[][] matrix = new double[matrixHeight][matrixWidth];
		
		//on crée la matrice de la corrélation croisée grâce à la méthode précédente
		for (int row = 0; row < matrixHeight; row++) {
			for (int col = 0; col < matrixWidth; col++) {
				matrix[row][col] = normalizedCrossCorrelation(row, col, pattern, image);
			}
		}
		return matrix;
	}

}

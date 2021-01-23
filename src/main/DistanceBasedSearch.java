package main;

public class DistanceBasedSearch {

	/**
	 * Computes the mean absolute error between two RGB pixels, channel by channel.
	 * 
	 * @param patternPixel
	 *            : a integer, the second RGB pixel.
	 * @param imagePixel
	 *            : a integer, the first RGB pixel.
	 * @return a double, the value of the error for the RGB pixel pair. (an integer
	 *         in [0, 255])
	 */
	public static double pixelAbsoluteError(int patternPixel, int imagePixel) {

		double somme = 0.0;
		double C = 3.0; // Cardinalité des composantes couleurs
		
		 //On calcule chaque composante couleur du patterne et de l'image
		int redM = ImageProcessing.getRed(patternPixel);
		int greenM = ImageProcessing.getGreen(patternPixel);
		int blueM = ImageProcessing.getBlue(patternPixel);

		int redI = ImageProcessing.getRed(imagePixel);
		int greenI = ImageProcessing.getGreen(imagePixel);
		int blueI = ImageProcessing.getBlue(imagePixel);
		
		// Calcul de la somme qui est donnée dans la formule
		somme = Math.abs(redM - redI) + Math.abs(greenM - greenI) + Math.abs(blueM - blueI); // somme de |M_c - I_c|
		
		//Renvoie l'erreur absolue moyenne
		return somme / C;
	}

	/**
	 * Computes the mean absolute error loss of a RGB pattern if positioned at the
	 * provided row, column-coordinates in a RGB image
	 * 
	 * @param row
	 *            : a integer, the row-coordinate of the upper left corner of the
	 *            pattern in the image.
	 * @param column
	 *            : a integer, the column-coordinate of the upper left corner of the
	 *            pattern in the image.
	 * @param pattern
	 *            : an 2D array of integers, the RGB pattern to find
	 * @param image
	 *            : an 2D array of integers, the RGB image where to look for the
	 *            pattern
	 * @return a double, the mean absolute error
	 */
	public static double meanAbsoluteError(int row, int col, int[][] pattern, int[][] image) { // ATTENTION: Cette methode concerne la partie 4 du pdf
		
		assert pattern != null;
		assert image != null;
		assert row >= 0;
		assert col >= 0;

		double somme = 0.0;

		int patternWidth = pattern[0].length;
		int patternHeight = pattern.length;

		double denominateur = patternWidth * patternHeight;
		
		/*
		 * On parcours l'image ligne par colonne
		 * On appelle la fonction de l'erreur absolue moyenne
		 * On fait la somme pour tout les pixels de l'image
		 * Puis on divise cette somme par le nombre de pixels de l'image
		 */
		for (int i = 0; i < patternHeight; i++) {
			for (int j = 0; j < patternWidth; j++) {
				somme += pixelAbsoluteError(pattern[i][j], image[i + row][j + col]);
			}
		}

		return somme / denominateur;
	}

	public static double meanAbsoluteError(int row, int col, int[][] pattern, int[][] image, String strategy) { // ATTENTION cette methode concerne le premier bonus du pdf

		assert pattern != null;
		assert image != null;

		assert row >= 0;
		assert col >= 0;

		int imageWidth = image[0].length;
		int imageHeight = image.length;
		int patternWidth = pattern[0].length;
		int patternHeight = pattern.length;

		int newRow = 0;
		int newCol = 0;

		double somme = 0.0;

		double denominateur = patternWidth * patternHeight;
		/*
		 * Même principe que la méthode précédente
		 * Mais le motif ne s'arrête pas au bord de l'image
		 * Mais il  continue sans tenir compte de la partie du motif en dehors de l'image
		 */
		for (int i = 0; i < patternHeight; i++) {
			for (int j = 0; j < patternWidth; j++) {

				// si l'utilisateur entre "wrap" (pour utiliser la méthode du wrapping)
				if (strategy.equals("wrap")) {
					newRow = (i + row) % imageHeight;
					newCol = (j + col) % imageWidth;
					somme += pixelAbsoluteError(pattern[i][j], image[newRow][newCol]); 

				// si l'utilisateur entre "mirror" (pour utiliser la méthode du mirroring)
				} else if (strategy.equals("mirror")) {

					newRow = i + row;
					newCol = j + col;

					if (newRow < imageHeight && newCol < imageWidth) {
						// on ne fait rien
					}

					else if (newRow >= imageHeight && newCol < imageWidth) {
						newRow = imageHeight - 2 - (newRow % imageHeight);
					}

					else if (newRow < imageHeight && newCol >= imageWidth) {
						newCol = imageWidth - 2 - (newCol % imageWidth);
					}

					else if (newRow >= imageHeight && newCol >= imageWidth) {
						newRow = imageHeight - 2 - (newRow % imageHeight);
						newCol = imageWidth - 2 - (newCol % imageWidth);
					}

					somme += pixelAbsoluteError(pattern[i][j], image[newRow][newCol]);
				}
			}
		}

		return somme / denominateur;
	}

	/**
	 * Compute the distanceMatrix between a RGB image and a RGB pattern
	 * 
	 * @param pattern
	 *            : an 2D array of integers, the RGB pattern to find
	 * @param image
	 *            : an 2D array of integers, the RGB image where to look for the
	 *            pattern
	 * @return a 2D array of doubles, containing for each pixel of a original RGB
	 *         image, the distance (meanAbsoluteError) between the image's window
	 *         and the pattern placed over this pixel (upper-left corner)
	 */
	public static double[][] distanceMatrix(int[][] pattern, int[][] image) {//ATTENTION concerne la partie 4 du pdf

		assert pattern != null;
		assert image != null;

		int imageWidth = image[0].length;
		int imageHeight = image.length;

		int patternWidth = pattern[0].length;
		int patternHeight = pattern.length;

		int matrixWidth = imageWidth - patternWidth+1;
		int matrixHeight = imageHeight - patternHeight+1;

		double[][] matrix = new double[matrixHeight][matrixWidth];
		
		//Calcule la matrice des distances grace aux methode du dessus
		for (int row = 0; row < matrixHeight; row++) {
			for (int col = 0; col < matrixWidth; col++) {
				matrix[row][col] = meanAbsoluteError(row, col, pattern, image);
			}
		}

		return matrix;
	}

	public static double[][] distanceMatrix(int[][] pattern, int[][] image, String strategy) {//ATTENTION concerne le bonus

		assert pattern != null;
		assert image != null;

		int imageWidth = image[0].length;
		int imageHeight = image.length;

		double[][] matrix = new double[imageHeight][imageWidth];

		for (int row = 0; row < imageHeight; row++) {
			for (int col = 0; col < imageWidth; col++) {
				matrix[row][col] = meanAbsoluteError(row, col, pattern, image, strategy);
			}
		}

		return matrix;

	}
}

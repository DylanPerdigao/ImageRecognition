package main;

public final class ImageProcessing {

	/**
	 * Returns red component from given packed color.
	 * 
	 * @param rgb
	 *            : a 32-bits RGB color
	 * @return an integer, between 0 and 255
	 * @see #getGreen
	 * @see #getBlue
	 * @see #getRGB(int, int, int)
	 */
	public static int getRed(int rgb) {
		int red = (rgb >> 16) & 0xFF;
		red = verificationDesBornes(red);
		return red;
	}

	/**
	 * Returns green component from given packed color.
	 * 
	 * @param rgb
	 *            : a 32-bits RGB color
	 * @return an integer between 0 and 255
	 * @see #getRed
	 * @see #getBlue
	 * @see #getRGB(int, int, int)
	 */
	public static int getGreen(int rgb) {
		int green = (rgb >> 8) & 0xFF;
		green = verificationDesBornes(green);
		return green;
	}

	/**
	 * Returns blue component from given packed color.
	 * 
	 * @param rgb
	 *            : a 32-bits RGB color
	 * @return an integer between 0 and 255
	 * @see #getRed
	 * @see #getGreen
	 * @see #getRGB(int, int, int)
	 */
	public static int getBlue(int rgb) {
		int blue = rgb & 0xFF;
		blue = verificationDesBornes(blue);
		return blue;
	}

	/**
	 * Returns the average of red, green and blue components from given packed
	 * color.
	 * 
	 * @param rgb
	 *            : 32-bits RGB color
	 * @return a double between 0 and 255
	 * @see #getRed
	 * @see #getGreen
	 * @see #getBlue
	 * @see #getRGB(int)
	 */
	public static double getGray(int rgb) {
		//pour le gris ont fait la moyenne des autres couleurs
		double gray = (getRed(rgb) + getGreen(rgb) + getBlue(rgb)) / 3.0;
		return gray;
	}

	/**
	 * Returns packed RGB components from given red, green and blue components.
	 * 
	 * @param red
	 *            : an integer
	 * @param green
	 *            : an integer
	 * @param blue
	 *            : an integer
	 * @return a 32-bits RGB color
	 * @see #getRed
	 * @see #getGreen
	 * @see #getBlue
	 */
	public static int getRGB(int red, int green, int blue) {
		red = verificationDesBornes(red);
		green = verificationDesBornes(green);
		blue = verificationDesBornes(blue);

		int color = (red << 16) | (green << 8) | blue;
		return color;
	}

	/**
	 * Returns packed RGB components from given gray-scale value.
	 * 
	 * @param gray
	 *            : a double
	 * @return a 32-bits RGB color
	 * @see #getGray
	 */
	public static int getRGB(double gray) {
		//on arrondi le gris et on enlève la partie a virgule
		int newGray = (int) Math.round(gray);
		newGray = verificationDesBornes(newGray);

		int color = (newGray << 16) | (newGray << 8) | newGray;
		return color;
	}

	/**
	 * Converts packed RGB image to gray-scale image.
	 * 
	 * @param image
	 *            : a HxW integer array
	 * @return a HxW double array
	 * @see #encode
	 * @see #getGray
	 */
	public static double[][] toGray(int[][] image) {

		assert image != null;

		int width = image[0].length;
		int height = image.length;
		double[][] imageGris = new double[height][width];
		//parcourt l'image pour transformer chaque pixel en gris
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				double pixelGris = getGray(image[row][col]);
				imageGris[row][col] = pixelGris;
			}
		}
		return imageGris;
	}

	/**
	 * Converts gray-scale image to packed RGB image.
	 * 
	 * @param channels
	 *            : a HxW double array
	 * @return a HxW integer array
	 * @see #decode
	 * @see #getRGB(double)
	 */
	public static int[][] toRGB(double[][] gray) {
		int width = gray[0].length;
		int height = gray.length;
		int[][] imageCouleur = new int[height][width];
		//parcourt l'image pour transformer chaque pixel en couleur
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				int pixelCouleur = getRGB(gray[row][col]);
				imageCouleur[row][col] = pixelCouleur;

			}
		}
		return imageCouleur;
	}

	/**
	 * Convert an arbitrary 2D double matrix into a 2D integer matrix which can be
	 * used as RGB image
	 * 
	 * @param matrix
	 *            : the arbitrary 2D double array to convert into integer
	 * @param min
	 *            : a double, the minimum value the matrix could theoretically
	 *            contains
	 * @param max
	 *            : a double, the maximum value the matrix could theoretically
	 *            contains
	 * @return an 2D integer array, containing a RGB mapping of the matrix
	 */
	public static int[][] matrixToRGBImage(double[][] matrix, double min, double max) {

		int matrixWidth = matrix[0].length;
		int matrixHeight = matrix.length;

		int[][] matrixRGB = new int[matrixHeight][matrixWidth];

		for (int row = 0; row < matrixHeight; row++) {
			for (int col = 0; col < matrixWidth; col++) {
				double valMatrix = matrix[row][col];
				matrixRGB[row][col] = getRGB(255 * (valMatrix - min) / (max - min));
			}
		}
		return matrixRGB;
	}

	public static int verificationDesBornes(int color) {
		/*
		 * Permet de vérifier si la couleur se trouve bien dans les bornes [0,255] sinon
		 * on le place dans la borne la plus proche
		 */
		if (color < 0) {
			return 0;
		} else if (color > 255) {
			return 255;
		} else {
			return color;
		}
	}
}

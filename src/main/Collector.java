package main;

import java.util.ArrayList;

public class Collector {

	/**
	 * Find the row, column coordinates of the best element (biggest or smallest)
	 * for the given matrix
	 * 
	 * @param matrix
	 *            : an 2D array of doubles
	 * @param smallestFirst
	 *            : a boolean, indicates if the smallest element is the best or not
	 *            (biggest is then the best)
	 * @return an array of two integer coordinates, row first and then column
	 */
	public static int[] findBest(double[][] matrix, boolean smallestFirst) {
		int width = matrix[0].length;
		int height = matrix.length;
		int smallRow = 0;
		int smallCol = 0;
		int bigRow = 0;
		int bigCol = 0;
		int[] coordinates = new int[2];
		double smallestElement = Double.POSITIVE_INFINITY;
		double biggestElement = Double.NEGATIVE_INFINITY;
		/*	On va parcourrir l'image par ligne et par colonne
		 * 	Puis on campare la valeur de la coordonnée pour voir s'il s'agit du plus petit élement (resp. plus grand element selon le booléen Smallestfirst)
		 * 	Si c'est le cas il devient le plus petit element (resp. plus grand)
		 */
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (smallestFirst && matrix[row][col] < smallestElement) {
					smallestElement = matrix[row][col];
					smallRow = row;
					smallCol = col;
				}
				if (!smallestFirst && matrix[row][col] > biggestElement) {
					biggestElement = matrix[row][col];
					bigRow = row;
					bigCol = col;
				}
			}
		}
		// On enregistre la plus petite valeur (resp. la plus grande) dans un petit tableau
		if (smallestFirst) {
			coordinates[0] = smallRow;
			coordinates[1] = smallCol;
		} else {
			coordinates[0] = bigRow;
			coordinates[1] = bigCol;
		}
		return coordinates;
	}

	/**
	 * Find the row, column coordinate-pairs of the n best (biggest or smallest)
	 * elements of the given matrix
	 * 
	 * @param n
	 *            : an integer, the number of best elements we want to find
	 * @param matrix
	 *            : an 2D array of doubles
	 * @param smallestFirst
	 *            : a boolean, indicates if the smallest element is the best or not
	 *            (biggest is the best)
	 * @return an array of size n containing row, column-coordinate pairs
	 */
	public static int[][] findNBest(int n, double[][] matrix, boolean smallestFirst) {
		int width = matrix[0].length;
		int height = matrix.length;
		int[][] Ncoordinates = new int[n][2];
		int[] coordinates = new int[2];
		double[][] copyMatrix = new double[height][width];
		// On effectue une copie de la matrice
		for (int i = 0; i < height; i++) {
				copyMatrix[i] = matrix[i].clone();
		}
		/*	On appelle la fonction findNBest pour avoir les coordonnées de la plus petite (resp. plus grande valeur selon smallestfirst) 
		 * 	À préciser que l'on envoie la copie de la matrice dans cette fonction
		 * 	Puis dans cette matrice copiée on remplace la valeur de la plus petite (resp. la plus grande) par l'infini (resp. - l'infini)
		 * 	Ces coordonnées sont stockées dans un autre tableau qui regroupe les n meilleures coordonnées
		 */
		for (int k = 0; k < n; k++) {
			coordinates = findBest(copyMatrix, smallestFirst);
			if (smallestFirst) {
				copyMatrix[coordinates[0]][coordinates[1]] = Double.POSITIVE_INFINITY;
			} else {
				copyMatrix[coordinates[0]][coordinates[1]] = Double.NEGATIVE_INFINITY;
			}
			Ncoordinates[k] = coordinates;
		}
		return Ncoordinates;
	}

	/**
	 * BONUS Notice : Bonus points are underpriced !
	 * 
	 * Sorts all the row, column coordinates based on their pixel value Hint : Use
	 * recursion !
	 * 
	 * @param matrix
	 *            : an 2D array of doubles
	 * @return A list of points, each point is an array of length 2.
	 */
	public static ArrayList<int[]> quicksortPixelCoordinates(double[][] matrix) {

		// TODO implement me correctly for "underpriced" bonus!
		return new ArrayList<int[]>();
	}

	/**
	 * BONUS Notice : Bonus points are underpriced !
	 * 
	 * Use a quick sort to find the row, column coordinate-pairs of the n best
	 * (biggest or smallest) elements of the given matrix Hint : return the n first
	 * or n last elements of a sorted ArrayList
	 * 
	 * @param n
	 *            : an integer, the number of best elements we want to find
	 * @param matrix
	 *            : an 2D array of doubles
	 * @param smallestFirst
	 *            : a boolean, indicate if the smallest element is the best or not
	 *            (biggest is the best)
	 * @return an array of size n containing row, column-coordinate pairs
	 */
	public static int[][] findNBestQuickSort(int n, double[][] matrix, boolean smallestFirst) {

		// TODO implement me correctly for underpriced bonus!
		return new int[][] {};
	}
}

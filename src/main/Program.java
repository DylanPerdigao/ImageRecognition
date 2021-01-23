/*
 * Auteurs : Dylan Gonçalves Perdigao et Khaireddine Gatti 
 * Date: 30 oct. 2017
 *
 */
package main;

import java.util.Scanner;

public class Program {
	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Entrez un nombre pour choisir une image: ");

		System.out.println("");
		System.out.println("(0	<=>	Food)");
		System.out.println("(1	<=>	Voiture)");
		System.out.println("(2	<=>	Voiture Light)");
		System.out.println("(3	<=>	Voiture Dark)");
		System.out.println("(4	<=>	Charlie)");

		System.out.println("");
		System.out.print("Votre Nombre: ");

		int image = scanner.nextInt();
		int[][] fond = new int[][] {};
		int[][] motif = new int[][] {};

		switch (image) {

		case 0:
			fond = Helper.read("images/food.png");
			motif = Helper.read("images/onions.png");
			choix(fond, motif);
			break;

		case 1:
			fond = Helper.read("images/image.png");
			motif = Helper.read("images/pattern.png");
			choix(fond, motif);
			break;

		case 2:
			fond = Helper.read("images/image-light.png");
			motif = Helper.read("images/pattern.png");
			System.out.println("");
			System.out.println("Le calcul se fera avec la matrice de similarité");
			similarityMatrix(fond, motif);
			break;

		case 3:
			fond = Helper.read("images/image-dark.png");
			motif = Helper.read("images/pattern.png");
			System.out.println("");
			System.out.println("Le calcul se fera avec la matrice de similarité");
			similarityMatrix(fond, motif);
			break;

		case 4:
			fond = Helper.read("images/charlie_beach.png");
			motif = Helper.read("images/charlie.png");
			System.out.println("");
			System.out.println("Le calcul se fera avec la matrice de similarité");
			similarityMatrix(fond, motif);
			break;

		default:
			System.out.print("Votre Nombre n'est pas valide, relancez le programme");

		}

		scanner.close();
	}

	public static void choix(int[][] fond, int[][] motif) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("");
		System.out.println("Entrez un nombre pour choisir une méthode pour trouver le motif: ");

		System.out.println("");
		System.out.println("(0	<=>	Distance Based Search)");
		System.out.println("(1	<=>	Similarity Based Search)");

		System.out.println("");
		System.out.print("Votre nombre : ");

		int choix = scanner.nextInt();

		switch (choix) {

		case 0:
			System.out.println("");
			System.out.println("Vous avez selectionné: Distance Based Search");
			strategy(fond, motif);
			break;

		case 1:
			System.out.println("");
			System.out.println("Vous avez selectionné: Similarity Based Search");
			similarityMatrix(fond, motif);
			break;

		default:
			System.out.print("Votre nombre n'est pas valide, relancez le programme");
		}
		
		scanner.close();
	}

	public static void strategy(int[][] fond, int[][] motif) {

		Scanner scanner = new Scanner(System.in);

		System.out.println("");
		System.out.println("Quelle stratégie voulez-vous que le programme utilise ?");
		System.out.println("basic	<=>	méthode basique");
		System.out.println("wrap	<=>	wrapping");
		System.out.println("mirror	<=>	mirroring");
		System.out.println("");
		System.out.print("Votre sélection : ");

		String strategy = scanner.nextLine();
		double[][] distanceMatrix;

		switch (strategy) {
		case "wrap":
			System.out.println("");
			System.out.println("Vous avez selectionné le wrapping.");
			distanceMatrix = DistanceBasedSearch.distanceMatrix(motif, fond, "wrap");
			distanceMatrix(fond, motif, distanceMatrix);
			break;
		case "mirror":
			System.out.println("");
			System.out.println("Vous avez selectionné le mirroring.");
			distanceMatrix = DistanceBasedSearch.distanceMatrix(motif, fond, "mirror");
			distanceMatrix(fond, motif, distanceMatrix);
			break;
		case "basic":
			System.out.println("");
			System.out.println("Vous avez selectionné la méthode basique.");
			distanceMatrix = DistanceBasedSearch.distanceMatrix(motif, fond);
			distanceMatrix(fond, motif, distanceMatrix);
			break;
		default:
			System.out.print("La stratégie entrée n'est pas proposée, relancez le programme");
		}

		scanner.close();
	}

	public static void distanceMatrix(int[][] fond, int[][] motif, double[][] distanceMatrix) {
		int[][] Dbest = Collector.findNBest(8, distanceMatrix, true);
		for (int[] a : Dbest) {
			int r = a[0];
			int c = a[1];
			Helper.drawBox(r, c, motif[0].length, motif.length, fond);
		}
		Helper.show(fond, "Projet: Où est Charlie?");
	}

	public static void similarityMatrix(int[][] fond, int[][] motif) {
		double[][] fondGray = ImageProcessing.toGray(fond);
		double[][] motifGray = ImageProcessing.toGray(motif);
		double[][] similarity = SimilarityBasedSearch.similarityMatrix(motifGray, fondGray);

		int[] Sbest = Collector.findBest(similarity, false);
		Helper.drawBox(Sbest[0], Sbest[1], motif[0].length, motif.length, fond);
		Helper.show(fond, "Le motif se trouve en (" + Sbest[0] + "," + Sbest[1] + ")");
	}
}
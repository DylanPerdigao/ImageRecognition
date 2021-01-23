package main;

/**
 * Check if the signatures of all required functions are correct
 * This file should complied but not run ! (i.e, not indicate any error) 
 * For your own good, please do not change anything inside this file !
 * Check this file is complied before submit your project on moodle 
 */
public class SignatureChecks {

    @SuppressWarnings("unused")
	public static void main(String[] argv) {
        int[][] image = new int[0][0];
        double[][] grayImage = new double[0][0];

        int rgb = 0;
        int red = 0;
        int green = 0;
        int blue = 0;
        double gray = 0;
        double factor = 0;
        
        int intResult;
        double doubleResult;
        boolean boolResult;
        
        //ImageProcessing
        intResult = ImageProcessing.getRed(rgb);
        intResult = ImageProcessing.getGreen(rgb);	
        intResult = ImageProcessing.getBlue(rgb);
        doubleResult =ImageProcessing.getGray(rgb);
        intResult = ImageProcessing.getRGB(red, green, blue);
        intResult = ImageProcessing.getRGB(gray);
        
        grayImage = ImageProcessing.toGray(image);
        image = ImageProcessing.toRGB(grayImage);

        int[][] img = ImageProcessing.matrixToRGBImage(grayImage, 0, 255);
        
        
        
        //Collector
        int[] best = Collector.findBest(grayImage, false);
        int[][] nBests = Collector.findNBest(3, grayImage, false);
        
        
        //DistanceBasedSearch
        double error = DistanceBasedSearch.pixelAbsoluteError(0xffffff, 0x000000);
        error = DistanceBasedSearch.meanAbsoluteError(0, 0, image, image);        
        double[][] res = DistanceBasedSearch.distanceMatrix(image, image);
        
        
        //CrossCorrelation
        double m = SimilarityBasedSearch.mean(res);
        SimilarityBasedSearch.normalizedCrossCorrelation(0, 0, grayImage, grayImage);
        SimilarityBasedSearch.similarityMatrix(grayImage, grayImage);
    }
}

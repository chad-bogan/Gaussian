/**
 * The GaussianBayes program reads a .txt file of training data
 * arranged in into classes. The data is then used in multiple
 * math operations alongside a user-inputed point to determine the
 * probability of the given point in each class. If the probability is too low, the 
 * program will print 0.0, just due to rounding. Some code is unused and/or commented
 * out due to testing. The program works as intended.
 * 
 * @author Patrick Hogan
 * 
 */
import java.util.*;
import java.util.Scanner;
import java.io.*;
public class GaussianBayes {
	public static void main(String [] args) {
		int classToken;
		ArrayList classes = new ArrayList<Integer>();
		ArrayList<ArrayList<Double>> dataX = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> dataY = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> meansX = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> meansY = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> varX = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> varY = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> gaussX = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> gaussY = new ArrayList<ArrayList<Double>>();
		int counter = 0;
		int temp = 0;
		int temp2;
		int counter2 = 0;
		Scanner scan = new Scanner(System.in);
		System.out.println("Input the X value you would like to try.");
		double xInput = scan.nextDouble();
		System.out.println("You are trying: " + xInput);
		System.out.println("Now input your Y value.");
		double yInput = scan.nextDouble();
		System.out.println("You are trying: " + yInput);
		try {
			Scanner fileScan = new Scanner(new BufferedReader(new FileReader("trainer.txt")));
			while (fileScan.hasNextInt()) {
				classes.add(fileScan.nextInt());
				//System.out.println(counter);
				counter++;
				fileScan.nextLine();
			
			}
			Set<Integer> classesNoDupes = new LinkedHashSet<Integer>(classes);
			ArrayList<Integer> classes2 = new ArrayList();
			//classes.clear();
			classes2.addAll(classesNoDupes);
			int classAmount = classes2.size();
			//System.out.println(classes.size());
			//System.out.println(classAmount);
			/**
			 * These loops initialize the ArrayLists of ArrayLists
			 */
			for (int i = 0; i < classAmount; i++) {
				ArrayList<Double> storeX = new ArrayList<Double>();
				dataX.add(storeX);
			}
			for (int i = 0; i < classAmount; i++) {
				ArrayList<Double> storeY = new ArrayList<Double>();
				dataY.add(storeY);
			}
			for (int i = 0; i < classAmount; i++) {
				ArrayList<Double> storeMeansX = new ArrayList<Double>();
				meansX.add(storeMeansX);
			}
			for (int i = 0; i < classAmount; i++) {
				ArrayList<Double> storeMeansY = new ArrayList<Double>();
				meansY.add(storeMeansY);
			}
			for (int i = 0; i < classAmount; i++) {
				ArrayList<Double> storeVarX = new ArrayList<Double>();
				varX.add(storeVarX);
			}
			for (int i = 0; i < classAmount; i++) {
				ArrayList<Double> storeVarY = new ArrayList<Double>();
				varY.add(storeVarY);
			}
			for (int i = 0; i < classAmount; i++) {
				ArrayList<Double> storeGaussX = new ArrayList<Double>();
				gaussX.add(storeGaussX);
			}
			for (int i = 0; i < classAmount; i++) {
				ArrayList<Double> storeGaussY = new ArrayList<Double>();
				gaussY.add(storeGaussY);
			}
			Scanner fileScan2 = new Scanner(new BufferedReader(new FileReader("trainer.txt")));
			int y = 0; 
			while(y < classes.size()) {
				int tracker = (int)fileScan2.nextInt();
				//System.out.println(tracker);
				dataX.get(tracker).add(fileScan2.nextDouble());
				//System.out.println(dataX.get(tracker));
				
				dataY.get(tracker).add(fileScan2.nextDouble());
				//System.out.println(dataY.get(tracker));
				y++;
				fileScan2.nextLine();
				
			}
			//System.out.println(dataX.size() + " POWERGAP " + classAmount);
			for(int i = 0; i < classAmount; i++) {
				//System.out.println(meansX.get(0));
				double mean = meanX(dataX.get(i));
				meansX.get(i).add(mean);
				double mean2 = meanY(dataY.get(i));
				meansY.get(i).add(mean2);
			}
			for(int i = 0; i < classAmount; i++) {
				//System.out.println(varX.get(0));
				double variance1 = varianceX(dataX.get(i));
				varX.get(i).add(variance1);
				double variance2 = varianceY(dataY.get(i));
				varY.get(i).add(variance2);
			}
			for(int k = 0; k < classAmount; k++) {
				double mean = meansX.get(k).get(0);
				//System.out.println(mean);
				double variance = varX.get(k).get(0);
				double value = gaussianMath(mean, variance, xInput);
				gaussX.get(k).add(value);
				
			}
			for(int k = 0; k < classAmount; k++) {
				double mean = meansY.get(k).get(0);
				
				double variance = varY.get(k).get(0);
				double value = gaussianMath(mean, variance, yInput);
				gaussY.get(k).add(value);
				
			}
			for(int b = 0; b < classAmount; b++) {
				double finalProb = gaussX.get(b).get(0) * gaussY.get(b).get(0);
				System.out.println("Class " + b + " value: " + finalProb);
			}
			//System.out.println(meansX.get(0));
			//System.out.println(varY.get(0));
			//System.out.println(gaussX.get(0));
			for(int l = 0; l < classAmount; l++) {
				double prob = classProb(dataX.get(l), counter);
				//System.out.println(prob);
				//System.out.println(prob);
				//System.out.println(gaussX.get(l).get(0));
				//System.out.println(gaussY.get(l).get(0));
				double probability = prob*gaussX.get(l).get(0)*gaussY.get(l).get(0);
				//System.out.println("Class " + l + " Probability: " + probability);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//System.out.println(varX.get(1));
}
	/**
	 * 
	 * @param x is the list of x data for a given class
	 * @return the mean of the class
	 */
	public static double meanX(ArrayList<Double> x) {
		double sum = 0;
		for (int i = 0; i < x.size(); i++) {
			sum += x.get(i);
		}
		sum /= x.size();
		return sum;
		
	}
	/**
	 * 
	 * @param y is the list of y data for a given class
	 * @return the mean of the class
	 */
	public static double meanY(ArrayList<Double> y) {
		double sum = 0;
		for (int i = 0; i < y.size(); i++) {
			sum += y.get(i);
		}
		sum /= y.size();
		return sum;
	}
	/**
	 * 
	 * @param x is the list of x data for a given class
	 * @return the variance of the class
	 */
	public static double varianceX(ArrayList<Double> x) {
		double mean = meanX(x);
		//System.out.println(mean);
        double temp = 0;
        for(double a : x) {
            temp += (a-mean)*(a-mean);
            //System.out.println(a);
        }
        
        return temp/(x.size());
        
		
		
	}
	/**
	 * 
	 * @param y is the list of y data for a given class
	 * @return the variance of the class
	 */
	public static double varianceY(ArrayList<Double> y) {
		double mean = meanY(y);
        double temp = 0;
        for(double a : y)
            temp += (a-mean)*(a-mean);
        return temp/(y.size());
		
		
	}
	/**
	 * 
	 * @param x is the mean
	 * @param y is the variance
	 * @param z is the input
	 * @return finalCalc is the probability using the Gaussian Naive Bayes formula
	 */
	public static double gaussianMath(double x, double y, double z) {
		//System.out.println(x);
		//System.out.println(y);
		//System.out.println(z);
		double radicand = 2*Math.PI*y;
		double radicand2 = Math.sqrt(radicand);
		double radicand3 = 1/radicand2;
		//System.out.println(radicand2);
		double expo = z - x;
		double expo2 = Math.pow(expo, 2);
		double expo3 = 2*y;
		double expo4 = -(expo2/expo3);
		//System.out.println(expo4);
		double e = Math.pow(Math.E, expo4);
		//System.out.println(e);
		double finalCalc = radicand3*e;
		//System.out.println(finalCalc);
		return finalCalc;
		
	}
	/**
	 * Can pass either dataX or dataY through to find the probability of the given class.
	 */
	public static double classProb(ArrayList<Double> x, double y) {
		double nPoints = x.size();
		
		double probab = (nPoints / y);
		//System.out.println(nPoints);
		//System.out.println(y);
		//System.out.println(probab);
		return probab;
		
	}
}
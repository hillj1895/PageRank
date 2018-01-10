import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		// Change Damping Factor Here!!!
		go(0.85);
		
	}
	
	
	public static void go(double damping)
	{
		WebPageMap wpMap = readFile();
		double[][] tMatrix = wpMap.constructMatrix();
		double[] pageRanks = PageRankIterative(tMatrix, damping, 100);
		ArrayList<WebPage> rankings = setRankings(pageRanks, wpMap);
		for(WebPage w : rankings)
		{
			System.out.println(w.getIndex() + ", " +  w.getAddress());
		}
	}
	
	/**
	 * Sets rankings of all web pages in page rank vector
	 * @param pageRanks
	 * @param map
	 * @return
	 */
	private static ArrayList<WebPage> setRankings(double[] pageRanks, WebPageMap map)
	{
		ArrayList<WebPage> rankedPages = new ArrayList<WebPage>();
		
		for(int i=0; i<pageRanks.length; i++)
		{
			WebPage current = map.get(i+1);
			
			current.setPageRank(pageRanks[i]);
			
			rankedPages.add(current);
		}
		
		rankedPages.sort(new Comparator<WebPage>() {

			@Override
			public int compare(WebPage o1, WebPage o2)
			{
				if (o1.getPageRank() > o2.getPageRank()) return -1;
		        if (o1.getPageRank() < o2.getPageRank()) return 1;
		        return 0;
			}
			
		});
		
		return rankedPages;
	}

	/**
	 * Runs pagerank algorithm
	 * @param array2d
	 * @param damping
	 * @param numIterations
	 * @return
	 */
	private static double[] PageRankIterative(double[][] array2d, double damping, int numIterations)
	{
		array2d = getTranspose(array2d);
		array2d = applyDamping(array2d, damping);
		
		double[] PRvector = new double[array2d.length];
		Arrays.fill(PRvector, (double) 1/PRvector.length);
		
		
		
		for(int i=0; i<numIterations; i++)
		{
			PRvector = multiply1Dby2D(PRvector, array2d);
		}
		
		return PRvector;
	}
	
	/**
	 * Multiplies 1D vector by 2D matrix and returns 1D vector
	 * @param vector
	 * @param array2d
	 * @return
	 */
	private static double[] multiply1Dby2D(double[] vector, double[][] array2d)
	{
        int m = array2d.length;
        int n = array2d[0].length;
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                y[i] += array2d[i][j] * vector[j];
            }
        }
        return y;
	}

	/**
	 * Transposes the matrix
	 * @param matrix
	 * @return
	 */
	private static double[][] getTranspose(double[][] matrix)
	{
        int m = matrix.length;
        int n = matrix[0].length;
        double[][] newMatrix = new double[n][m];
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                newMatrix[j][i] = matrix[i][j];
            }
        }
        return newMatrix;
	}

	/**
	 * Applies the damping factor to an input matrix
	 * @param matrix
	 * @param damping
	 * @return
	 */
	private static double[][] applyDamping(double[][] matrix, double damping)
	{
		double dampCompliment = 1 - damping;
		double size = (double) matrix.length;
		double value = dampCompliment / size;
		
		for(int i=0; i<size; i++)
		{
			for(int j=0; j<size; j++)
			{
				matrix[i][j] *= damping;
				matrix[i][j] += value;
			}
		}
		
		return matrix;
	}
	
	/**
	 * Reads the input .dat file
	 * @return
	 */
	private static WebPageMap readFile()
	{
		WebPageMap allPages = new WebPageMap();
		String csvFile = "src/hollins.dat";
		Scanner scanner = null;
        String lineRegex = " ";
		
		try
        {
        	scanner = new Scanner(new File(csvFile));
        
        	String firstLine = scanner.nextLine();
        	String[] nums = firstLine.split(lineRegex);
        	int numWebPages = Integer.parseInt(nums[0]);
    		int numLinks = Integer.parseInt(nums[1]);
        	
    		for(int i=0; i<numWebPages; i++)
        	{
        		String[] values = scanner.nextLine().split(lineRegex);
        		int index = Integer.parseInt(values[0]);
        		String address = values[1];
        		WebPage wp = new WebPage(i, index, values[1]);
        		
        		allPages.put(wp.getIndex(), wp);
        		
        	}
    		
    		for(int i=0; i<numLinks; i++)
    		{
    			String s  = scanner.nextLine();
    			String[] values = s.split(lineRegex);
    			WebPage sourcePage = allPages.get(Integer.parseInt(values[0]));
    			WebPage destinationPage = allPages.get(Integer.parseInt(values[1]));
    			
    			sourcePage.addLinkTo(destinationPage);
    		}
        	
        	
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return allPages;
	}
	
	/**
	 * Prints the input matrix
	 * @param matrix
	 */
	private static void printMatrix(double[][] matrix)
	{
		for(double[] array : matrix)
		{
			System.out.print("[");
			for(double number : array)
			{
				System.out.print(number + ", ");
			}
			System.out.println("]");
		}
	}


	
}

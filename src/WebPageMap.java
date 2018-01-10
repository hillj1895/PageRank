import java.util.HashMap;

public class WebPageMap extends HashMap<Integer, WebPage>
{
	public WebPageMap()
	{
		super();
	}
	
	/**
	 * Builds transition matrix based off of the contents of the map
	 * @return
	 */
	public double[][] constructMatrix()
	{
		double[][] matrix = new double[this.size()][this.size()];
		double value;
		for(WebPage wSource : this.values())
		{
			int sourceMatrixIndex = wSource.getIndex() - 1;
			
			if(wSource.getLinksFromList().size()==0)
			{
				value = 0;
			}
			else
			{
				value = 1 / (double) wSource.getLinksFromList().size();
			}
			
			for(WebPage wDest : wSource.getLinksFromList())
			{
				int destMatrixIndex = wDest.getIndex() - 1;
				matrix[sourceMatrixIndex][destMatrixIndex] = value;
			}
			
		}
		
		return matrix;
	}
	
	
	
}

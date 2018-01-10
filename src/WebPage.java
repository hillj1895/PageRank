import java.util.ArrayList;

public class WebPage
{
	private String _address;
	private int _index;
	private int _location;
	private ArrayList<WebPage> _linksFrom;
	private double _pageRank;
	
	public WebPage(int location, int index, String address)
	{
		_location = location;
		_index = index;
		_address = address;
		_linksFrom = new ArrayList<WebPage>();
	}
	
	public void addLinkTo(WebPage w)
	{
		_linksFrom.add(w);
	}
	
	public String getAddress()
	{
		return _address;
	}
	
	public int getIndex()
	{
		return _index;
	}
	
	public int getLocation()
	{
		return _location;
	}
	
	@Override
	public int hashCode()
	{
		return _index;
	}

	public String printLinksFrom()
	{
		String s = "";
		for(WebPage w : _linksFrom)
		{
			s += w.getIndex();
			s += " | ";
		}
		return s;
	}

	public ArrayList<WebPage> getLinksFromList()
	{
		return _linksFrom;
	}

	public double getPageRank()
	{
		return _pageRank;
	}

	public void setPageRank(double pageRank)
	{
		_pageRank = pageRank;
	}

	
}

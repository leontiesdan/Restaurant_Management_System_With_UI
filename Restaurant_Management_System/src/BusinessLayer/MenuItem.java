package BusinessLayer;

public abstract class MenuItem implements java.io.Serializable {

	protected String name;
	protected int price;
	
	public abstract int computePrice();

	public void setName(String name) 
	{
		this.name = name;
	}
	
	
	public void setPrice(int price) 
	{
		this.price = price;
	}
	
	public String getName() 
	{
		return name;
	}

	public int getPrice() 
	{
		return price;
	}

	
	
	@Override
	public String toString()
	{
		return this.name + " "+ this.price;
		
	}
}

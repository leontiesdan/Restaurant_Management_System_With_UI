package BusinessLayer;

public class BaseProduct extends MenuItem {

	public BaseProduct(String name,int price) 
	{
		this.name=name;
		this.price=price;
	}
	
	@Override
	public int computePrice() 
	{
		return getPrice();
	}

}

package BusinessLayer;

import java.util.ArrayList;
import java.util.Iterator;

public class CompositeProduct extends MenuItem {

	private ArrayList<MenuItem> compProds ;
	
	public CompositeProduct(String name,ArrayList<MenuItem> compProds) 
	{
		this.name=name;
		this.compProds=compProds;
	}
	
	@Override
	public int computePrice() {
		
		int price = 0;
		
		Iterator<MenuItem> it = compProds.iterator();
		
		while(it.hasNext())
		{
			MenuItem curentItem = it.next();
			
			price += curentItem.getPrice();
		}
		
		return price;
	}

}

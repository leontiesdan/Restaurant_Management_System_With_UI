package BusinessLayer;


import java.io.BufferedWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

public class Restaurant extends Observable implements IRestaurantProcessing,java.io.Serializable {

	private ArrayList<MenuItem> menu ;
	private ArrayList<MenuItem> compositeItem = new ArrayList<MenuItem>();
	private String compositeName;
	private  Map<Order, ArrayList<MenuItem>> orderMenuMap ;
	private  ArrayList<Order> orderList;

	public Restaurant()
	{
		menu = new ArrayList<MenuItem>();
		orderMenuMap = new HashMap<Order, ArrayList<MenuItem>>();
		orderList= new ArrayList<Order>();
		
	}

	public void setMenu(ArrayList<MenuItem> myMenu) 
	{
		this.menu = myMenu;
	}

	public void setCompositeItem(ArrayList<MenuItem> comp) 
	{
		this.compositeItem = comp;
	}

	public void setCompositeName(String name) 
	{
		this.compositeName = name;
	}
	
	public ArrayList<MenuItem> getMenu() 
	{
		return menu;
	}

	public ArrayList<MenuItem> getCompItem() 
	{
		return this.compositeItem;
	}

	public String getCompositeName()
	{
		return this.compositeName;
	}
	
	public Map<Order, ArrayList<MenuItem>> getOrderMap()
	{
		return this.orderMenuMap;
	}
	
	public void addOrderToList(Order order)
	{
		orderList.add(order);
		
		StringBuilder sBuilder = new StringBuilder();

		sBuilder.append("NEW order created : " + "\n\n");
		sBuilder.append("Order ID : " + order.getOrderID() + "\n");
		sBuilder.append("Date : " + order.getOrderDate().getDay() + "-" + order.getOrderDate().getMonth() + "-" + order.getOrderDate().getYear() + "\n");
		sBuilder.append("Table : " + order.getTable() + "\n");
		sBuilder.append("Ordered Items : " + "\n");

		Map<Order, ArrayList<MenuItem>> myMap = getOrderMap();
		
		ArrayList<MenuItem> list = myMap.get(order);
		
		Iterator<MenuItem> it = list.iterator();
		
		while (it.hasNext()) 
		{
			sBuilder.append(it.next().toString() + "\n");
		}
		
		
		setChanged();
		notifyObservers(sBuilder.toString());
		
	}

	@Override
	public void createNewOrder(Order order, ArrayList<MenuItem> menuItem) 
	{

		assert order != null;
		assert menuItem != null;
		
		Order ord1 = order;
		
		orderMenuMap.put(order, menuItem);
		
		assert ord1.equals(order);
	}

	@Override
	public int computeOrderPrice(Order order) 
	{
		
		assert order != null ;
		
		int price = 0;
		if(this.orderList.contains(order)==true)
		{
			ArrayList<MenuItem> orderedItems = orderMenuMap.get(order);

			Iterator<MenuItem> it = orderedItems.iterator();
			
			while(it.hasNext())
			{
				price += it.next().getPrice();
			}
		
		}
		
		return price;
	}

	@Override
	public void generateTXTBill(String content) 
	{
		
		assert content != null;
		
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("BillOrder.txt")));
			bw.write(content);
			bw.close();
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void createNewMenuItem(MenuItem item) 
	{
		
		assert item != null;
		
		int preSize = menu.size();
		
		menu.add(item);
		
		int postSize = menu.size();
		
		assert preSize == postSize - 1;

	}

	@Override
	public void deleteMenuItem(MenuItem item) 
	{

		assert item != null;
		
		int preSize = menu.size();
		
		menu.remove(item);

		int postSize = menu.size();
		
		assert preSize + 1 == postSize;
		
	}

	@Override
	public void editMenuItem(MenuItem item) 
	{
		assert item != null;
		
		int postPrice = item.getPrice();
		int prePrice = 0;
		
		String itemName = item.getName();
		
		Iterator<MenuItem> it = menu.iterator();

		while (it.hasNext()) 
		{
			
			MenuItem curentItem = it.next();
			
			if (curentItem.getName() == itemName) 
			{
					
				curentItem.setPrice(item.getPrice());		
				
				prePrice = curentItem.getPrice();
				
			}
		
		}

		it = menu.iterator();
		
		while (it.hasNext()) 
		{
			MenuItem curentItem = it.next();
		
			curentItem.setPrice(curentItem.computePrice());
		}
		
		assert prePrice == postPrice;
	}
	
	
}

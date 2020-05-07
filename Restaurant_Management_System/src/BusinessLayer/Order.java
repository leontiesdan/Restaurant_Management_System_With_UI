package BusinessLayer;

import java.util.Date;

public class Order {

	private int OrderID;
	private int table;
	private CustomDate OrderDate;
	
	public Order(int OrderID, CustomDate OrderDate, int table)
	{
		this.OrderID=OrderID;
		this.OrderDate=OrderDate;
		this.table=table;
	}
	
	public void setOrderID(int OrderID)
	{
		this.OrderID = OrderID;
	}
	
	public void setOrderDate(CustomDate OrderDate)
	{
		this.OrderDate = OrderDate;
	}
	
	public void setTable(int table)
	{
		this.table = table;
	}

	public int getOrderID() 
	{
		return OrderID;
	}

	public CustomDate getOrderDate() 
	{
		return OrderDate;
	}

	public int getTable() 
	{
		return table;
	}
	
	@Override
	public int hashCode()
	{
		int hashcode = 11;
		hashcode += hashcode * 8 + 35 * OrderID + 3 * OrderDate.getDay() + 5 * table;
		return hashcode;
		
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this==obj) return true;
		
		if(obj==null) return false;
		
		if(this.getClass() != obj.getClass()) return false;
		
		Order other = (Order) obj;
		
		if(this.OrderID!=other.OrderID) return false;
		
		if(this.OrderDate.getDay() != other.OrderDate.getDay()) return false;
		
		if(this.table != other.table) return false;
		
		return true;
		
	}
	
}

package DataLayer;

import java.io.*;
import BusinessLayer.Restaurant;

public class RestaurantSerializator {

	public static void sz(Restaurant r) 
	{
		try {
			
			FileOutputStream fileOut = new FileOutputStream("Restaurant.ser");
			
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(r);
			
			out.close();
			
			fileOut.close();
			
			System.out.printf("Data saved in Restaurant.ser");
		
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static Restaurant deSZ() 
	{
		
		Restaurant rest = null;
		
		try {
		
			FileInputStream fileIn = new FileInputStream("Restaurant.ser");
			
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			rest = (Restaurant) in.readObject();
			
			in.close();
			
			fileIn.close();
			
			System.out.println(rest);
			
			return rest;
		
		} catch (IOException i) {
			
			System.out.println(i);
			 
			rest = new Restaurant();
			 
			sz(rest);
			 
			return rest;
			
		} catch (ClassNotFoundException c) {

			System.out.println("Restaurant not found");
			
			c.printStackTrace();
			
			return rest = new Restaurant();
		}
	}
}

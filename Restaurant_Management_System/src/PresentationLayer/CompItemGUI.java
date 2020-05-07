package PresentationLayer;

import java.awt.Font;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Restaurant;
import DataLayer.RestaurantSerializator;



public class CompItemGUI extends JFrame {

	private Restaurant restaurant;
	
	private JLabel titleLabel;
	private JComboBox<MenuItem> menuBox;
	
	private ArrayList<MenuItem> compositeItem;
	
	private JButton addItemBtn;
	private JButton finishItemBtn;
	
	private JTextArea addedItems;
	private JLabel addedLabel;
	
	
	
	public CompItemGUI(Restaurant restaurant)
	{
		
		compositeItem = new ArrayList<MenuItem>();
		this.restaurant=restaurant;
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setBounds(500, 150, 900, 700);
		this.getContentPane().setLayout(null);

		titleLabel = new JLabel("Item Interface");
		titleLabel.setBounds(300, 50, 450, 50);
		getContentPane().add(titleLabel);
		
		menuBox= new JComboBox<MenuItem>();
		menuBox.setBounds(100,150,250,50);
		getContentPane().add(menuBox);
		
		addItemBtn = new JButton("Add Item");
		addItemBtn.setBounds(50, 550, 125, 50);
		getContentPane().add(addItemBtn);
		
		addItemBtn.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				MenuItem chosenItem = (MenuItem) menuBox.getSelectedItem();
				compositeItem.add(chosenItem);
				addedItems.append(chosenItem.toString() + "\n");
				
			}
		});
		
		finishItemBtn = new JButton("Finish Item");
		finishItemBtn.setBounds(250, 550, 125, 50);
		getContentPane().add(finishItemBtn);
		
		finishItemBtn.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				restaurant.setCompositeItem(getCompItems());
				ArrayList<MenuItem> comp = restaurant.getCompItem();
				String iname = restaurant.getCompositeName();
				
				CompositeProduct cp = new CompositeProduct(iname, comp);
				int finalPrice = cp.computePrice();
				cp.setPrice(finalPrice);
				
				restaurant.createNewMenuItem(cp);
				RestaurantSerializator.sz(restaurant);
				
				addedItems.setText("");
				JOptionPane.showMessageDialog(null, "Item Created");
			}
		});
		
		addedLabel = new JLabel("Added Items");
		addedLabel.setBounds(400,100,100,50);
		getContentPane().add(addedLabel);
		
		addedItems = new JTextArea();
		addedItems.setBounds(400,150,400,300);
		addedItems.setEditable(false);
		getContentPane().add(addedItems);
		
		
	}
	
	public void fill()
	{
		ArrayList<MenuItem> list = restaurant.getMenu();
		Iterator<MenuItem> it = list.iterator();

		while(it.hasNext())
		{
			MenuItem curentItem = it.next();
			menuBox.addItem(curentItem);
			
			System.out.println(curentItem.getName());
		}	
	}
	
	public ArrayList<MenuItem> getCompItems()
	{
		return compositeItem;
	}
}

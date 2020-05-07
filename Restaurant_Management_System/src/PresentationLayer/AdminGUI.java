package PresentationLayer;

import java.awt.Font;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import BusinessLayer.BaseProduct;
import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.MenuItem;
import BusinessLayer.Order;
import BusinessLayer.Restaurant;
import DataLayer.RestaurantSerializator;



public class AdminGUI extends JFrame implements IRestaurantProcessing {

	private JLabel titleLabel;
	private Restaurant restaurant;
	private CompItemGUI compositeItemGUI;

	private JButton backBtn;
	private JButton createItemBtn;
	private JButton deleteItemBtn;
	private JButton editItemBtn;
	private JButton showItemsBtn;

	private JLabel itemNameLabel;
	private JTextField itemNameField;

	private JLabel itemPriceLabel;
	private JTextField itemPriceField;

	public AdminGUI(MainGUI userInterface, Restaurant restaurant,CompItemGUI compositeItemGUI) 
	{

		this.restaurant = restaurant;
		this.compositeItemGUI = compositeItemGUI;

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setBounds(500, 150, 900, 700);
		this.getContentPane().setLayout(null);

		titleLabel = new JLabel("Administrator Interface");
		titleLabel.setBounds(300, 50, 450, 50);
		getContentPane().add(titleLabel);

		backBtn = new JButton("Go Back");
		backBtn.setBounds(750, 550, 100, 50);
		
		backBtn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{

				setVisible(false);
				userInterface.setVisible(true);

			}
		});
		getContentPane().add(backBtn);

		createItemBtn = new JButton("Create New item");
		createItemBtn.setBounds(50, 550, 125, 50);
		getContentPane().add(createItemBtn);
		
		createItemBtn.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = getNameField();
				String price = getPriceField();
				
				if (price.compareTo("") == 0) 
				{
						System.out.println("Composite Item");
				
						compositeItemGUI.setVisible(true);
						
						compositeItemGUI.fill();
						
						restaurant.setCompositeName(name);
						
				} else {

					int iPrice = 0;
					try {
						iPrice = Integer.parseInt(price);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Invalid Price");
					}

					BaseProduct newBaseProduct = new BaseProduct(name, iPrice);
					
					createNewMenuItem(newBaseProduct);
					
					RestaurantSerializator.sz(restaurant);
					
					JOptionPane.showMessageDialog(null, "Item has been added");
				}
				
			}
		});

		deleteItemBtn = new JButton("Delete item");
		deleteItemBtn.setBounds(200, 550, 125, 50);
		getContentPane().add(deleteItemBtn);

		deleteItemBtn.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String iname = getNameField();
				restaurant.deleteMenuItem(findByName(iname));
				RestaurantSerializator.sz(restaurant);

				JOptionPane.showMessageDialog(null, "Item has been deleted");
				
			}
	
		});

		editItemBtn = new JButton("Edit item");
		editItemBtn.setBounds(350, 550, 125, 50);
		getContentPane().add(editItemBtn);
		
		editItemBtn.addActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String iname = getNameField();
				String iprice = getPriceField();
				int price = 0;
				
				try {
					price = Integer.parseInt(iprice);
					
					BaseProduct newProduct = new BaseProduct(iname, price);
					MenuItem oldProduct = findByName(iname);
				
					if(oldProduct instanceof BaseProduct)
					{
						oldProduct.setPrice(price);
						JOptionPane.showMessageDialog(null, "Item has been changed");
						editMenuItem(oldProduct);
						RestaurantSerializator.sz(restaurant);

					}
					else JOptionPane.showMessageDialog(null, "Composite Products cannot be edited");
					
				}catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Invalid Price");
				}
				
			}
		});

		showItemsBtn = new JButton("Show items");
		showItemsBtn.setBounds(550, 550, 125, 50);
		getContentPane().add(showItemsBtn);
		
		showItemsBtn.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String columns[] = { "Item Name", "Item Price" };
			
				ArrayList<MenuItem> itemList = restaurant.getMenu();
				
				DefaultTableModel model = new DefaultTableModel();
				
				model.setColumnIdentifiers(columns);
				
				Object[] obj = new Object[2];
				
				Iterator<MenuItem> it = itemList.iterator();
				
				while (it.hasNext()) {
				
					MenuItem curentItem = it.next();
					System.out.println(curentItem.getName() + " " + curentItem.getPrice());

					obj[0] = curentItem.getName();
					obj[1] = curentItem.getPrice();
					
					model.addRow(obj);
				}

				JTable myTable = new JTable(model);
				JScrollPane myScrollPane = new JScrollPane();
				myScrollPane.setBounds(250, 100, 600, 400);
				myScrollPane.setViewportView(myTable);
				getContentPane().add(myScrollPane);

			}
		});

		itemNameLabel = new JLabel("Name");
		itemNameLabel.setBounds(50, 150, 50, 40);
		getContentPane().add(itemNameLabel);

		itemNameField = new JTextField();
		itemNameField.setBounds(100, 150, 100, 30);
		getContentPane().add(itemNameField);

		itemPriceLabel = new JLabel("Price");
		itemPriceLabel.setBounds(50, 200, 50, 40);
		getContentPane().add(itemPriceLabel);

		itemPriceField = new JTextField();
		itemPriceField.setBounds(100, 200, 100, 30);
		getContentPane().add(itemPriceField);

	}

	public String getPriceField() 
	{
		return this.itemPriceField.getText();
	}

	public String getNameField() 
	{
		return this.itemNameField.getText();
	}
	
	public MenuItem findByName(String name)
	{
		ArrayList<MenuItem> items = restaurant.getMenu();
		Iterator<MenuItem> it = items.iterator();
		
		while(it.hasNext())
		{
			MenuItem crtItem = it.next();
			if(crtItem.getName().compareTo(name) == 0) 
			{
				return crtItem;
			}
		}
		
		return null;
	}

	@Override
	public void createNewOrder(Order order, ArrayList<MenuItem> menuItem) 
	{
		System.out.println("Only waiter can create orders");

	}

	@Override
	public int computeOrderPrice(Order order)
	{
		System.out.println("Only waiter can compute the price for an order");
		return 0;

	}

	@Override
	public void generateTXTBill(String whatToPrint)
	{
		System.out.println("Only waiter can generate a bill");

	}

	@Override
	public void createNewMenuItem(MenuItem item) 
	{
		restaurant.createNewMenuItem(item);

	}

	@Override
	public void deleteMenuItem(MenuItem item) 
	{
		restaurant.deleteMenuItem(item);

	}

	@Override
	public void editMenuItem(MenuItem item)
	{
		restaurant.editMenuItem(item);

	}
	
	
}

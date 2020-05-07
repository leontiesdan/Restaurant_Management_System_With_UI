package PresentationLayer;

import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;

import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.GenID;
import BusinessLayer.MenuItem;
import BusinessLayer.CustomDate;
import BusinessLayer.Order;
import BusinessLayer.Restaurant;


public class WaiterGUI extends JFrame implements IRestaurantProcessing {

	private JLabel titleLabel;

	private JButton backButton;
	private JButton createOrderBtn;
	private JButton addMenuItemBtn;
	private JButton fillMenuBtn;

	private JButton showOrdersBtn;
	private JButton generateBillBtn;

	ArrayList<MenuItem> orderedItems = new ArrayList<MenuItem>();
	ArrayList<Order> orderList = new ArrayList<Order>();

	private JLabel orderIDLabel;
	private JTextField orderIDField;
	private JLabel orderTableLabel;
	private JTextField orderTableField;
	private JLabel orderDateLabel;
	private JTextField orderDateField;

	private JLabel chosenItemsLabel;
	private JTextArea chosenItems;

	private JComboBox<MenuItem> menu;

	private Restaurant restaurant;

	public WaiterGUI(MainGUI userInterface, Restaurant restaurant) {
		
		this.restaurant = restaurant;

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(500, 150, 1200, 700);
		this.getContentPane().setLayout(null);

		titleLabel = new JLabel("Waiter Interface");
		titleLabel.setBounds(300, 50, 450, 50);
		getContentPane().add(titleLabel);

		orderIDLabel = new JLabel("OrderID:");
		orderIDLabel.setBounds(50, 110, 80, 30);
		getContentPane().add(orderIDLabel);

		orderIDField = new JTextField();
		orderIDField.setBounds(120, 110, 50, 30);
		getContentPane().add(orderIDField);

		orderTableLabel = new JLabel("Table:");
		orderTableLabel.setBounds(50, 150, 50, 30);
		getContentPane().add(orderTableLabel);

		orderTableField = new JTextField();
		orderTableField.setBounds(100, 150, 50, 30);
		getContentPane().add(orderTableField);

		orderDateLabel = new JLabel("Date:");
		orderDateLabel.setBounds(50, 200, 50, 30);
		getContentPane().add(orderDateLabel);

		orderDateField = new JTextField();
		orderDateField.setBounds(100, 200, 100, 30);
		getContentPane().add(orderDateField);

		menu = new JComboBox<MenuItem>();
		menu.setBounds(50, 250, 150, 50);
		getContentPane().add(menu);

		chosenItemsLabel = new JLabel("Chosen items");
		chosenItemsLabel.setBounds(230, 130, 100, 20);
		getContentPane().add(chosenItemsLabel);

		chosenItems = new JTextArea();
		chosenItems.setBounds(230, 150, 100, 200);
		getContentPane().add(chosenItems);

		backButton = new JButton("Go Back");
		backButton.setBounds(750, 550, 100, 50);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				userInterface.setVisible(true);

			}
		});
		getContentPane().add(backButton);

		fillMenuBtn = new JButton("Show Menu");
		fillMenuBtn.setBounds(20, 320, 110, 30);
		getContentPane().add(fillMenuBtn);
		fillMenuBtn.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				fillMenu();
			}
		});

		addMenuItemBtn = new JButton("Add");
		addMenuItemBtn.setBounds(140, 320, 80, 30);
		getContentPane().add(addMenuItemBtn);
		addMenuItemBtn.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {

				MenuItem myItem = (MenuItem) menu.getSelectedItem();
				chosenItems.append(myItem.toString() + "\n");
				
				orderedItems.add(myItem);
				System.out.println(myItem.getName());

			}
		});

		createOrderBtn = new JButton("Create Order");
		createOrderBtn.setBounds(50, 550, 150, 50);
		getContentPane().add(createOrderBtn);
		createOrderBtn.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {

				ArrayList<MenuItem> copyList =null;

				try {
					int ordID = GenID.getNextId();
					int table = Integer.parseInt(orderTableField.getText());
					String date = orderDateField.getText();
					if(date.matches("[0-9][0-9]-[0-9][0-9]-[0-9][0-9][0-9][0-9]") == false) 
					{
							System.out.println("Wrong Date Format");
							return;
					}
					
					System.out.println(ordID + " " + table + " " + date);
					
					
					StringTokenizer st = new StringTokenizer(date, "-");
					
					int day = Integer.parseInt(st.nextToken());
					
					int month = Integer.parseInt(st.nextToken());
					
					int year = Integer.parseInt(st.nextToken());

					CustomDate cDate = new CustomDate(day, month, year);
					Order myOrder = new Order(ordID, cDate, table);
					
					
					copyList = new ArrayList<MenuItem>();
					
					Iterator<MenuItem> it = orderedItems.iterator();
					
					int i = 0;
					
					while (it.hasNext()) 
					{
						i++;
						copyList.add(it.next());
					}
					
					if(i > 0)
					{
					
					
						createNewOrder(myOrder, copyList);
						
						orderedItems.removeAll(orderedItems);
						chosenItems.setText("");
					
						
						
						
						
						orderList.add(myOrder);
						restaurant.addOrderToList(myOrder);
						
						
					}
					else
						JOptionPane.showMessageDialog(null, "Nothing for chef to prepare");
					
				}catch (Exception e1) {
					System.out.println(e1);
					JOptionPane.showMessageDialog(null, "Wrong input");
				}
				

			}
		});

		showOrdersBtn = new JButton("Show Orders");
		showOrdersBtn.setBounds(550, 550, 150, 50);
		getContentPane().add(showOrdersBtn);
		
		showOrdersBtn.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
			
				Map<Order, ArrayList<MenuItem>> myMap = restaurant.getOrderMap();

				String columns[] = { "OrderID", "Date", "Table", "OrderedItems" };

				DefaultTableModel model = new DefaultTableModel();
				model.setColumnIdentifiers(columns);

				Object[] obj = new Object[4];
				Iterator<Order> it = orderList.iterator();

				while (it.hasNext()) 
				{
				
					Order curentOrder = it.next();
					
					obj[0] = curentOrder.getOrderID();
					
					String date = curentOrder.getOrderDate().getDay() + "-" + curentOrder.getOrderDate().getMonth() + "-" + curentOrder.getOrderDate().getYear();
					
					obj[1] = date;
					obj[2] = curentOrder.getTable();
					
					ArrayList<MenuItem> listM = myMap.get(curentOrder);
					StringBuilder sb = new StringBuilder();
					Iterator<MenuItem> it1 = listM.iterator();
					
					while (it1.hasNext()) 
					{

						sb.append(it1.next().toString());
						if (it1.hasNext())	sb.append(" , ");
					}

					obj[3] = sb.toString();
					model.addRow(obj);
				}

				JTable table = new JTable(model);
				JScrollPane myScrollPane = new JScrollPane();
				
				myScrollPane.setBounds(350, 100, 800, 400);
				myScrollPane.setViewportView(table);
				myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

				getContentPane().add(myScrollPane);

			}
		});

		generateBillBtn = new JButton("Generate Bill in TXT format");
		generateBillBtn.setBounds(300, 550, 200, 50);
		getContentPane().add(generateBillBtn);
		
		generateBillBtn.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				int ordID = Integer.parseInt(orderIDField.getText());
				Order myOrder = findOrderByID(ordID);
			
				if (myOrder == null)
					JOptionPane.showMessageDialog(null, "Order not found");
				else {

					StringBuilder sBuilder = new StringBuilder();

					sBuilder.append("Order ID : " + ordID + "\n");
					sBuilder.append("Date : " + myOrder.getOrderDate().getDay() + "-" + myOrder.getOrderDate().getMonth() + "-" + myOrder.getOrderDate().getYear() + "\n");
					sBuilder.append("Table : " + myOrder.getTable() + "\n");
					sBuilder.append("Ordered Items : " + "\n");

					Map<Order, ArrayList<MenuItem>> myMap = restaurant.getOrderMap();
					ArrayList<MenuItem> list = myMap.get(myOrder);
					Iterator<MenuItem> it = list.iterator();
					
					while (it.hasNext()) 
					{
						sBuilder.append(it.next().toString() + "\n");
					}
					
					sBuilder.append("Final price : " + computeOrderPrice(myOrder));

					generateTXTBill(sBuilder.toString());
					
					JOptionPane.showMessageDialog(null, "Bill has been generated");

				}
			}
		});
	}

	@Override
	public void createNewMenuItem(MenuItem item)
	{
		System.out.println("Only admin can create new menu items");

	}

	@Override
	public void deleteMenuItem(MenuItem item) 
	{
		System.out.println("Only admin can delete menu items");

	}

	@Override
	public void editMenuItem(MenuItem item) 
	{
		System.out.println("Only admin can edit menu items");

	}

	@Override
	public void createNewOrder(Order order, ArrayList<MenuItem> menuItem) 
	{

		restaurant.createNewOrder(order, menuItem);

	}

	
	@Override
	public int computeOrderPrice(Order order) 
	{

		return restaurant.computeOrderPrice(order);
	}

	@Override
	public void generateTXTBill(String content) 
	{
		restaurant.generateTXTBill(content);

	}

	public void fillMenu() 
	{
		menu.removeAllItems();
		
		ArrayList<MenuItem> list = restaurant.getMenu();
		Iterator<MenuItem> it = list.iterator();

		
		while (it.hasNext()) 
		{
			MenuItem curentItem = it.next();
			menu.addItem(curentItem);
		}
	}

	public Order findOrderByID(int id) 
	{
		Order myOrder = null;
		Iterator<Order> it = orderList.iterator();
		while (it.hasNext()) 
		{
			Order curOrder = it.next();
			if (curOrder.getOrderID() == id)
			{
				myOrder = curOrder;
			}
		}
		return myOrder;
	}
}

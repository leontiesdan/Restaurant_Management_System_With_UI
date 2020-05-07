package PresentationLayer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainGUI extends JFrame {

	private JLabel titleLabel;
	private JButton AdminBtn;
	private JButton ChefBtn;
	private JButton WaiterBtn;
	
	public MainGUI() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(500, 150, 900, 700);
		this.getContentPane().setLayout(null);
		
		titleLabel = new JLabel("Select a user");
		titleLabel.setBounds(300,50,450,50);
		getContentPane().add(titleLabel);
		
		AdminBtn = new JButton("Admin");
		AdminBtn.setBounds(100,350,150,50);
		getContentPane().add(AdminBtn);
		
		ChefBtn = new JButton("Chef");
		ChefBtn.setBounds(300,350,200,50);
		getContentPane().add(ChefBtn);
		
		WaiterBtn = new JButton("Waiter");
		WaiterBtn.setBounds(550,350,150,50);
		getContentPane().add(WaiterBtn);
		
		
	}
	
	public void addToWaiterButtonActionListener(ActionListener actionListener)
	{
		WaiterBtn.addActionListener(actionListener);
	}
	
	public void addToAdminButtonActionListener(ActionListener actionListener)
	{
		AdminBtn.addActionListener(actionListener);
	}
	
	public void addToChefButtonActionListener(ActionListener actionListener)
	{
		ChefBtn.addActionListener(actionListener);
	}
}

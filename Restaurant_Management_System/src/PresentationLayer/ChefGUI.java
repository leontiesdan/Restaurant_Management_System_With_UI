package PresentationLayer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import BusinessLayer.Restaurant;



public class ChefGUI extends JFrame implements Observer {

	private JLabel titleLabel;

	private JButton backBtn;
	private Restaurant restaurant;

	public ChefGUI(MainGUI MGUI, Restaurant restaurant) {

		this.restaurant = restaurant;

		restaurant.addObserver(this);

		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setBounds(500, 150, 900, 700);
		this.getContentPane().setLayout(null);


		titleLabel = new JLabel("Chef Interface");
		titleLabel.setBounds(300, 50, 450, 50);
		getContentPane().add(titleLabel);

		backBtn = new JButton("Back");
		backBtn.setBounds(750, 550, 100, 50);
		backBtn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				MGUI.setVisible(true);

			}
		});
		getContentPane().add(backBtn);
	}

	@Override
	public void update(Observable o, Object arg)
	{

		this.setVisible(true);
	
		System.out.println(arg.toString());
		
		int x = JOptionPane.showConfirmDialog(null, arg, "Chef Notification", 2);
		
		if (x == 0) 
		{
			System.out.println("Chef will prepare the order");
			this.setVisible(false);
		} else {
			System.out.println("Chef is currently cooking another order, this order will be cooked later");
			this.setVisible(false);
		}

	}
}
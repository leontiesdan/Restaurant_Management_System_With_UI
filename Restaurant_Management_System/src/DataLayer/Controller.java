package DataLayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.crypto.spec.DESedeKeySpec;
//import javax.jws.soap.SOAPBinding.Use;

import org.w3c.dom.UserDataHandler;

import BusinessLayer.Restaurant;
import PresentationLayer.AdminGUI;
import PresentationLayer.ChefGUI;
import PresentationLayer.CompItemGUI;
import PresentationLayer.MainGUI;
import PresentationLayer.WaiterGUI;


public class Controller {

	private AdminGUI aGUI;
	private ChefGUI chGUI;
	private CompItemGUI ciGUI;
	private MainGUI mGUI;
	private WaiterGUI wGUI;

	private Restaurant restaurant;
	
	
	public void start()
	{

		restaurant=RestaurantSerializator.deSZ();
		mGUI = new MainGUI();
		wGUI = new WaiterGUI(mGUI,restaurant);
		chGUI = new ChefGUI(mGUI,restaurant);
		ciGUI = new CompItemGUI(restaurant);
		aGUI = new AdminGUI(mGUI,restaurant,ciGUI);
		mGUI.setVisible(true);
		
		init();
	}
	
	public void init()
	{
		mGUI.addToWaiterButtonActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				mGUI.setVisible(false);
				wGUI.setVisible(true);			
			}
	
		
		});
		
		mGUI.addToAdminButtonActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				mGUI.setVisible(false);
				aGUI.setVisible(true);
			}
		
		
		});
		
		mGUI.addToChefButtonActionListener(new ActionListener() 
		{
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				mGUI.setVisible(false);
				chGUI.setVisible(true);	
			}
		
		
		});
	}
}

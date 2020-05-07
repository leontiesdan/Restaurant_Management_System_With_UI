package BusinessLayer;

public class GenID {

	public static int counter = 1;

	private GenID() 
	{

	}

	public static int getNextId() 
	{
		return counter++;
	}
}
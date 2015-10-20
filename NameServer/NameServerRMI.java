package NameServer;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class NameServerRMI {
	
	private static final String HOST = "localhost";
	
	/**
	 * Constructor of NameServerRMI
	 */
	public NameServerRMI(){}
	
	/**
	 * Main 
	 * @param args
	 */
	public static void main(String args[])
	{
		try
		{
			NameServer myNameServer = new NameServer();
			
			String rmiObjectName = "rmi://" + HOST + "/myNameServer";
			
			// Creates and exports a Registry instance on the 
			// local host that accepts requests on the specified port
        	Registry registry = LocateRegistry.createRegistry(1099);
        	registry.bind("NameServer", myNameServer);
        	
        	// 'Bind' the object reference to the name...
        	Naming.rebind(rmiObjectName, myNameServer);
        	
        	
    		// Display a message so that we know the process
    		// has been completed...
    		System.out.println("Binding complete...\n");
		}
		catch(Exception e)
		{
			System.out.println("RMI server exception" + e.getMessage());
			e.printStackTrace();
		}
	}
}
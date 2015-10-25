package NameServer;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class RMIService {
	
	private static final String HOST = "localhost";
	NameServer aNameServer;
	/**
	 * Constructor of NameServerRMI
	 */
	public RMIService(){}
	
	
	public void Start(NameServer aNameServer)
	{
		try
		{	
			String rmiObjectName = "rmi://" + HOST + "/aNameServer";
			
			// Creates and exports a Registry instance on the 
			// local host that accepts requests on the specified port
        	Registry registry = LocateRegistry.createRegistry(1099);
        	registry.bind("NameServer", aNameServer);
        	
        	// 'Bind' the object reference to the name...
        	Naming.rebind(rmiObjectName, aNameServer);
        	
        	
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
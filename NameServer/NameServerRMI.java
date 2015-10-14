package NameServer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class NameServerRMI {
	public NameServerRMI(){}
	public static void main(String args[])
	{
		try
		{
			NameServer myNameServer = new NameServer();
        	Registry registry = LocateRegistry.createRegistry(1099);
        	registry.bind("NameServer", myNameServer);
		}
		catch(Exception e)
		{
			System.out.println("RMI server exception" + e.getMessage());
			e.printStackTrace();
		}
	}
}

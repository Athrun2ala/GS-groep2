package NameServer;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server {
	public Server(){}
	private static RMIService ServerRMI = new RMIService();
	private static NameServer myNameServer;
	private static MulticastService MulticastServer;
	
	//start alle services
	public static void main(String args[])
	{
		createNameServer();		//TODO maak nameserver
		InetAddress multiAddress = getMultiAddress();
		ServerRMI.Start(myNameServer);	//start RMI service
		MulticastServer = new MulticastService(1555,multiAddress,myNameServer);
		MulticastServer.run();
	}
	
	//functie om nieuwe nameServer aan te maken
	public static void createNameServer()
	{
		try
		{
			myNameServer = new NameServer();
		}
		catch(Exception e)
		{
			System.out.println("Could not create Nameserver");
		}
	}
	
	public static InetAddress getMultiAddress()
	{
		try {
			InetAddress anAdress = InetAddress.getByName("224.129.1.1");
			return anAdress;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}

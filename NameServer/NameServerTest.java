package NameServer;

import java.rmi.Naming;

public class NameServerTest {
	public static void main(String args[])
	{
		try
		{
			aNameServer myNameServer = (aNameServer)Naming.lookup("//"+"127.0.0.1" + "/NameServer");
			myNameServer.addNode("192.168.1.1");
			myNameServer.addNode("192.168.1.1");
			String anAddress=myNameServer.getLocation("test");
			System.out.println(anAddress);
			myNameServer.removeNode(10);
		}
		catch(Exception e)
		{
			System.out.println("failed");
		}
	}

}

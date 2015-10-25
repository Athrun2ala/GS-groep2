package NameServer;

import java.net.InetAddress;
import java.net.MulticastSocket;

public class NodeMulticast {
	private InetAddress MCAddress;
	private MulticastSocket mcSocket;
	private int mcPort;
	
	
	public NodeMulticast(String MulticastAddress,int aPort)
	{
		try
		{
			MCAddress = InetAddress.getByName(MulticastAddress);
		}
		catch (Exception e)
		{
			System.out.println("given String not an IP address!!");
		}
		mcPort = aPort;
	}
	
	public Boolean SetupMulticast(InetAddress MulticastAddress)
	{
		try
		{
			mcSocket = new MulticastSocket(mcPort);
			if(MulticastAddress.isMulticastAddress())
			{
				mcSocket.joinGroup(MCAddress);
				return true;
			}
			else
			{
				System.out.println("Given address is not a valid MC address!");
				return false;
			}
		}
		catch (Exception e)
		{
			return false;
		}
	}
}

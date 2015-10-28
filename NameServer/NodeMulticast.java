package NameServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class NodeMulticast implements Runnable {
	private InetAddress MCAddress;
	private MulticastSocket mcSocket;
	private boolean running;
	private int mcPort;
	private Node myNode;


	public NodeMulticast(String MulticastAddress,int aPort,Node aNode)
	{
		try
		{
			MCAddress = InetAddress.getByName(MulticastAddress);
		}
		catch (Exception e)
		{
			System.out.println("given String not an IP address!!");
		}
		myNode = aNode;
		mcPort = aPort;
		running = true;
	}

	public Boolean SetupMulticast()
	{
		try
		{
			mcSocket = new MulticastSocket(mcPort);
			if(MCAddress.isMulticastAddress())
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

	public void discovery()
	{
		if(SetupMulticast());
		{
			byte[] buf = new byte[1024];
			try 
			{
				buf = InetAddress.getLocalHost().getHostName().getBytes();
				DatagramPacket message = new DatagramPacket(buf,buf.length);
				mcSocket.send(message);
				// TODO roep functie aan om up te daten
			} catch (UnknownHostException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void run()
	{
		while(running)
		{
			byte[] buf = new byte[1024];
			DatagramPacket message = new DatagramPacket(buf,buf.length);
			try
			{
				mcSocket.receive(message);
				myNode.Update(buf.toString());
			}
			catch(IOException e){System.out.println("an error occured receiving messages");}
		}
	}
}

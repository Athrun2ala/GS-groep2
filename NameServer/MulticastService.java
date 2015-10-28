//Service die de werking van de multicasts verzorgt voor de NameServer
package NameServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class MulticastService implements Runnable {
	//nodige variables
	private int port;
	private InetAddress multicastIP;
	private MulticastSocket mcsocket;
	private boolean running;	
	private int bufferSize = 1024; //misschien naar constructor verhuizen
	private byte[] inputbuffer = new byte[bufferSize]; //ook naar constructor
	private DatagramPacket request = new DatagramPacket(inputbuffer,inputbuffer.length); //ook naar constructor
	private NameServer myNameServer;
	
	
	//constructor voor multicastservice, moet nog uitgebreid worden
	public MulticastService(int aPort,InetAddress anAddress,NameServer aNameServer)
	{
		port = aPort;
		multicastIP = anAddress;
		myNameServer = aNameServer;
	}
	
	
	//start de multicastserver
	public void run() 
	{
		if(setUpMulticast())
		{
			while(running)
			{
				try 
				{
					mcsocket.receive(request);	//discovery request
					myNameServer.addNode(request.getAddress(), inputbuffer.toString());	//voeg node toe aan lijst
					aNode client = (aNode) Naming.lookup("//"+request.getAddress().getHostAddress()+"/Node");	//maak node RMI object
					client.initialise(InetAddress.getLocalHost().getHostAddress(),myNameServer.getSize());	//zorg dat node aantal vraagt aan server
				}
				catch (IOException e) 
				{
					System.out.println("IO Exception: " +e.getMessage());
				}
				catch (NotBoundException e)
				{
					
				}

			}
			mcsocket.close();
		}
	}
	
	//probeer multicast op te zetten, indien niet gelukt return false
	public boolean setUpMulticast()
	{
		try
		{
			mcsocket = new MulticastSocket(port);
			mcsocket.joinGroup(multicastIP);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("error setting up multicast service ");
			return false;
		}
	}
	
	//beëindig multicast service
	public void terminate()
	{
		running = false;
	}
}

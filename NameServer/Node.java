package NameServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Node extends UnicastRemoteObject implements aNode {
	private int ringsize;
	private InetAddress multicastAddress;
	private MulticastSocket mcSocket;
	private int port;
	private static final long serialVersionUID = 1L;
	
	public Node(String aMCAddress,int aPort) throws RemoteException
	{
		try
		{
			multicastAddress = InetAddress.getByName(aMCAddress);
		}
		catch(Exception e)
		{
			System.out.println("Not an Ipaddress!");
		}
		port = aPort;
	}

	@Override
	public void getAantal(String ServerAddress) 
	{
		try 
		{	
			aNameServer myNameServer = (aNameServer)Naming.lookup("//"+ServerAddress+"/aNameServer");
			ringsize=myNameServer.getSize();
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error connecting to the NameServer!");
		}
	}
	
	public boolean setupMulticast(InetAddress multicastAddress,int port)
	{
		try
		{
			mcSocket = new MulticastSocket(port);
			if(multicastAddress.isMulticastAddress())
			{
			mcSocket.joinGroup(multicastAddress);
			return true;
			}
			else
			{
				System.out.println("Not a multicastAddress!");
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Failed to setup multicast \n");
			e.printStackTrace();
			return false;
		}
	}
	
	public void discovery()
	{
		if(setupMulticast(multicastAddress,port));
		{
			byte[] buf = new byte[1024];
			try {
				buf = InetAddress.getLocalHost().getHostName().getBytes();
				DatagramPacket message = new DatagramPacket(buf,buf.length);
				mcSocket.send(message);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

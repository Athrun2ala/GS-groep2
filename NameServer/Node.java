package NameServer;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Node extends UnicastRemoteObject implements aNode {
	private int ringsize;
	private aNameServer myNameServer;
	private int next,previous,myID;
	private static final long serialVersionUID = 1L;

	public Node() throws RemoteException
	{
		try
		{
			myID = calculateHash(InetAddress.getLocalHost().getHostName());
		}
		catch(Exception e){}
	}

	@Override
	public void initialise(String myAddress,int mySize) 
	{
		ringsize = mySize;
		try
		{
			myNameServer = (aNameServer)Naming.lookup("rmi://"+myAddress+"/aNameServer");
		}catch(Exception e){}
		if(ringsize ==1)
		{
			next = myID;
			previous = myID;
		}
	}

	public int calculateHash(String aName)
	{
		Hasher myHash = new Hasher();
		return(myHash.Hash(aName));
	}

	public void Update(String aName)
	{
		int newID = calculateHash(aName);
		if(myID<newID && newID < next) //ik ben vorige, mijn volgende is zijn volgende
		{
			try
			{
				aNode newNode = (aNode)Naming.lookup("rmi://"+myNameServer.getNodeAddress(newID).getHostAddress()+"/Node");
				newNode.setPrevious(myID);//ik ben de vorige node
				newNode.setNext(next);//mijn volgende is de volgende van de nieuwe node
				next = newID; //mijn nieuwe volgende is de nieuwe node
			}catch(Exception e){System.out.println("Error connecting to node RMI");}
		}
		if(previous<newID&&newID<myID) //ik ben de volgende node
		{
			//Updateprevious(newID)
			try
			{
				previous = newID;
			}catch(Exception e){}
		}
	}

	public void setNext(int anID)
	{
		next = anID;
	}

	public void setPrevious(int anID)
	{
		previous = anID;
	}
}

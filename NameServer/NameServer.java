package NameServer;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.TreeMap;
public class NameServer extends UnicastRemoteObject implements aNameServer { 
	private static final long serialVersionUID = 1L;
	private TreeMap<Integer, InetAddress> NodeList = new TreeMap<Integer,InetAddress>();
	public NameServer() throws RemoteException{};

	/**
	 * Add new node to NodeList(treemap)
	 * @param address	String ipaddress
	 * @throws Exception	Exception if allready in list
	 */
	public void addNode(InetAddress address,String Naam) 
	{
		Hasher myHasher = new Hasher();
		int nodeID = myHasher.Hash(Naam);
		if(!NodeList.containsKey(nodeID)&&!NodeList.containsValue(address))
		{
			NodeList.put(nodeID, address);
		}
	}

	/**
	 * Delete node with specified ID
	 * @param anID	The nodeID
	 */
	public void removeNode(int anID)
	{
		if(NodeList.containsKey(anID))
		{
			NodeList.remove(anID);
		}
		else
		{
			//do nothing
		}
	}

	/**
	 * Find the IP address of the node that has the specified file
	 * @param FileName name of the file that has to be found
	 * @return null if no nodes in the system, else return IP address of the node.
	 */
	public InetAddress getLocation(String FileName)
	{
		if (NodeList.isEmpty())
		{
			return null;
		}
		Hasher myHasher = new Hasher();
		int fileID = myHasher.Hash(FileName);
		Integer NodeID = NodeList.ceilingKey(fileID);
		if (NodeID == null)
		{
			NodeID = NodeList.firstKey();
		}
		return NodeList.get(NodeID);
	}
	
	public InetAddress getNodeAddress(int anID)
	{
		return NodeList.get(anID);
	}

	public Integer getSize()
	{
		return NodeList.size();
	}

}

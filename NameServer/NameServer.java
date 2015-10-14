package NameServer;

import java.util.TreeMap;
public class NameServer implements aNameServer { 
	private TreeMap<Integer, String> NodeList = new TreeMap<Integer,String>();
	public NameServer(){};
	
	/**
	 * Add new node to NodeList(treemap)
	 * @param address	String ipaddress
	 * @throws Exception	Exception if allready in list
	 */
	public void addNode(String address) 
	{
		Hasher myHasher = new Hasher();
		int nodeID = myHasher.Hash(address);
		if(!NodeList.containsValue(address))
		{
			NodeList.put(nodeID, address);
		}
		else
		{
			System.out.println("Node allready in list!");
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
			
		}
	}
	
	/**
	 * Find the IP address of the node that has the specified file
	 * @param FileName name of the file that has to be found
	 * @return null if no nodes in the system, else return IP address of the node.
	 */
	public String getLocation(String FileName)
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

}

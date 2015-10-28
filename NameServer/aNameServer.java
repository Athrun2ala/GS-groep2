package NameServer;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface aNameServer extends Remote {
	void addNode(InetAddress anAddress, String aName)throws RemoteException;
	void removeNode(int anID)throws RemoteException;
	InetAddress getLocation(String aFileName)throws RemoteException;
	InetAddress getNodeAddress(int anID) throws RemoteException;
	Integer getSize() throws RemoteException;
}

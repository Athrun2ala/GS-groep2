package NameServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface aNameServer extends Remote {
	void addNode(String anAddress)throws RemoteException;
	void removeNode(int anID)throws RemoteException;
	String getLocation(String aFileName)throws RemoteException;

}

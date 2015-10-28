//Node interface om RMI communicatie tussen nodes onderling en nodes met server mogelijk te maken.
package NameServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface aNode extends Remote {
	void initialise(String ServerAddress,int ringsize) throws RemoteException;
	void setNext(int anID) throws RemoteException;
	void setPrevious(int anID) throws RemoteException;

}

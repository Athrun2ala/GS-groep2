//Node interface om RMI communicatie tussen nodes onderling en nodes met server mogelijk te maken.
package NameServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface aNode extends Remote {
	void getAantal(String ServerAddress) throws RemoteException;

}

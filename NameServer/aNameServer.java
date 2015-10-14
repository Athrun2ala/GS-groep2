package NameServer;

import java.rmi.Remote;

public interface aNameServer extends Remote {
	void addNode(String anAddress);
	void removeNode(int anID);
	String getLocation(String aFileName);

}

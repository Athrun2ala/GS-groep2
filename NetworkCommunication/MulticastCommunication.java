package NetworkCommunication;

import java.net.*;
import java.io.*;

public class MulticastCommunication implements Runnable
{
	/**
	 * Class that enables multicasts in the network.
	 */
	private int port;
	private String multicastIP;
	private String nodename;
	private MulticastSocket mcsocket;
	private MulticastObserver mcobserver;
	private boolean running;				// parameter to check if communication is working or not
	private int bufferSize = 1024;

	/**
	 * Creates the MulticastCommunication Object.
	 * @param multicastIP	The ip of the multicast.
	 * @param port			The port on which the multicast needs to be send.
	 */
	public MulticastCommunication(String multicastIP, String nodename, int port)
	{
		this.multicastIP = multicastIP;
		this.nodename = nodename;
		this.port = port;
		this.mcobserver = new MulticastObserver();
		this.running = false;
	}

	/**
	 * gets the MulticastObserver
	 * @return	MulticastObserver
	 */
	public MulticastObserver getObserver()
	{
		return this.getObserver();
	}

	/**
	 * Setup the Multicast Communication.
	 * @return	boolean type: true if communication has started, otherwise false
	 */
	public boolean setupMulticastCommunication()
	{
		try
		{
			InetAddress ipAddress = InetAddress.getByName(multicastIP);
			mcsocket = new MulticastSocket(port);
			mcsocket.setInterface(InetAddress.getByName(this.nodename));
			mcsocket.joinGroup(ipAddress);
		}
		catch(SocketException e)
		{
			System.out.println("Socket: " + e.getMessage());
			return false;
		}
		catch(IOException e)
		{
			System.out.println("IO: " + e.getMessage());
			return false;
		}
		return true;
	}


	/**
	 * Send a multicast message.
	 * @param message	The message you want to send.
	 * @return True if successful, false otherwise.
	 */
	public boolean sendMulticast(byte[] message)
	{
		boolean status = false;
		
		// multicastCommunication is nog niet geinitialiseerd
		if(mcsocket == null)
		{
			if(!setupMulticastCommunication())
			{
				System.err.println("MulticastCommunication not initialised.");
			}
		}
		// boodschap is grooter dan buffersize
		else if(message.length > bufferSize)
		{
			System.err.println("Message exceeds length of multicastbuffer.");
		}

		else {
			try
			{
				InetAddress ipAddress = InetAddress.getByName(multicastIP);
				DatagramPacket messageOut = new DatagramPacket(message, message.length, ipAddress, port);
				mcsocket.send(messageOut);
			}
			catch(IOException e)
			{
				System.out.println("IO: " + e.getMessage());
				return false;
			}
			status = true;
		}
		return status;
	}
	
	/**
	 * Terminate the Multicast
	 * @return	true if termination is completed, otherwise false
	 */
	public boolean terminateMulticast()
	{
		this.running = false;	//disable running parameter
		
		if(mcsocket != null)
		{
			mcsocket.close();
			while(!mcsocket.isClosed())
			{
				// wait till socket closes
			}
			mcsocket = null;
			return true;
		}
		return false;
	}
	
	
	/**
	 * Run the MulticastCommunication
	 */
	@Override
	public void run() {
		if(mcsocket == null)
		{
			if(!setupMulticastCommunication())
			{
				System.err.println("MulticastCommunication not initialised.");
			}
		}
		// multicastCommunication is now set to run
		this.running = true;
		try
		{	
			while (this.running)
			{
				byte[] buffer = new byte[bufferSize];
				DatagramPacket request =new DatagramPacket(buffer, buffer.length);
				mcsocket.receive(request);
				this.mcobserver.hasChanged();				// verify if observer has changed
				this.mcobserver.notifyObservers(request);	// notify all oberservers of the new message
			}
		}
		catch (SocketException e)
		{
			System.out.println("Socket: " + e.getMessage());
		}
		catch (IOException e)
		{
			System.out.println("IO: " + e.getMessage());
		}
	}

}



//ToDo		nagaan of node bestaat. zoniet null retourneren (voor test enzo)

public class node {
	
	int ID;								// unieke ID in de lijst
	String adress;						// Overeenkomende IP adress
	
	// basic constructor. initialiseert waarde ID op -1 omdat we geen negative waardes mogen hebben
	public node() {
		this.ID = -1;
		this.adress = null;
	}
	
	/*
	 * extended constructor
	 */
	
	public node(int ID, String adress){
		this.ID = ID;
		this.adress = adress;
	}
	
	/**
	 * get the ID from a node
	 * @return ID van de node
	 */
	int getID (){
		return this.ID;
	}

	/**
	 * get the IP adress from a node
	 * @return adress van de node
	 */
	String getAdress () {
		return this.adress;
	}
	
	/**
	 * set the ID from a node
	 * @param ID	ID of the node.
	 */
	void setID (int ID) {
		this.ID = ID;
	}
	
	/**
	 * set the adress from a node
	 * @param adress	IP adress of the node.
	 */
	void setAdress (String adress) {
		this.adress = adress;
	}
	

}

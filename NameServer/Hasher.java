package NameServer;
public class Hasher {
	public Hasher(){}
	public int Hash(String aString)
	{
		int result = aString.hashCode();
		result = Math.abs(result % 32768);
		return result;
	}

}

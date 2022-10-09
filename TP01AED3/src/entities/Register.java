package entities;

import java.io.IOException;

public interface Register {
	
	public int getId();
	public void setId(int id);
	
	public byte[] toByteArray () throws IOException;
	public void fromByteArray(byte[] b) throws IOException;
	
}

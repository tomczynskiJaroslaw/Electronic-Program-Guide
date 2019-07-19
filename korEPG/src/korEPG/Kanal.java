package korEPG;

import java.util.ArrayList;
import java.util.HashMap;

public class Kanal {
	public static final HashMap<String, Kanal> kanaly = new HashMap<String, Kanal>();
	
	private String id;
	private String ikona;
	public ArrayList<Program> listaProgramow = new ArrayList<Program>();
	
	public Kanal(String id, String ikona) {
		this.id = id;
		this.ikona = ikona;
		kanaly.put(id, this);
	}
	
	static Kanal getKanal(String id) {
		return kanaly.get(id);
	}
	
	public void addProgram(Program program){
		listaProgramow.add(program);
	}
	
	public String getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return " listaProgramow=\n" + listaProgramow + "]\n\n";
	}
}

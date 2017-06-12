package wsiiz.projekt.mpkrzeszow.others;

public class Bus {
	
	private String id, linia, kurs;
	
	public Bus(String id, String linia, String kurs) {
		this.id = id;
		this.linia = linia;
		this.kurs = kurs;
	}
	
	public String toString() {
		if(id.equalsIgnoreCase("init"))
			return linia;
		else
			return linia + ": " + kurs;		
	}

	public String getId() {
		return id;
	}

}

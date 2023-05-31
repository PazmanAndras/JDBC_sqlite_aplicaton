package jdbcmodule;

public class Tanfolyam {
	
	String kezdesDate;
	int idotartam;
	String tanfTema;
	int tanfAr;
	
	
	public Tanfolyam(String kezdesDate, int idotartam, String tanfTema, int tanfAr) {
		super();
		this.kezdesDate = kezdesDate;
		this.idotartam = idotartam;
		this.tanfTema = tanfTema;
		this.tanfAr = tanfAr;
	}


	public String getKezdesDate() {
		return kezdesDate;
	}


	public void setKezdesDate(String kezdesDate) {
		this.kezdesDate = kezdesDate;
	}


	public int getIdotartam() {
		return idotartam;
	}


	public void setIdotartam(int idotartam) {
		this.idotartam = idotartam;
	}


	public String getTanfTema() {
		return tanfTema;
	}


	public void setTanfTema(String tanfTema) {
		this.tanfTema = tanfTema;
	}


	public int getTanfAr() {
		return tanfAr;
	}


	public void setTanfAr(int tanfAr) {
		this.tanfAr = tanfAr;
	}
	
	
	

}

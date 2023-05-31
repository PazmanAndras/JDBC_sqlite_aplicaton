package jdbcmodule;

public class Oktato {

	String szakterulet;
	String oktEmail;
	String OktNev;

	public Oktato(String szakterulet, String oktEmail, String oktNev) {
		super();
		this.szakterulet = szakterulet;
		this.oktEmail = oktEmail;
		OktNev = oktNev;
	}

	public String getSzakterulet() {
		return szakterulet;
	}

	public void setSzakterulet(String szakterulet) {
		this.szakterulet = szakterulet;
	}

	public String getOktEmail() {
		return oktEmail;
	}

	public void setOktEmail(String oktEmail) {
		this.oktEmail = oktEmail;
	}

	public String getOktNev() {
		return OktNev;
	}

	public void setOktNev(String oktNev) {
		OktNev = oktNev;
	}

}

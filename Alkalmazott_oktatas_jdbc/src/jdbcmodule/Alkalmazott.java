package jdbcmodule;

public class Alkalmazott {
	
		String Vnev;
		String Knev;
		String szulev;
		String Beosztas;
		int Fizetes;
		String AlkTel;
		
		
		public Alkalmazott(String vnev, String knev, String szulev, String beosztas, int fizetes, String alkTel) {
			super();
			this.Vnev = vnev;
			this.Knev = knev;
			this.szulev = szulev;
			this.Beosztas = beosztas;
			this.Fizetes = fizetes;
			this.AlkTel = alkTel;
		}




		public String getVnev() {
			return Vnev;
		}


		public void setVnev(String vnev) {
			Vnev = vnev;
		}


		public String getKnev() {
			return Knev;
		}


		public void setKnev(String knev) {
			Knev = knev;
		}


		public String getSzulev() {
			return szulev;
		}


		public void setSzulev(String szulev) {
			this.szulev = szulev;
		}


		public String getBeosztas() {
			return Beosztas;
		}


		public void setBeosztas(String beosztas) {
			Beosztas = beosztas;
		}


		public int getFizetes() {
			return Fizetes;
		}


		public void setFizetes(int fizetes) {
			Fizetes = fizetes;
		}


		public String getAlkTel() {
			return AlkTel;
		}


		public void setAlkTel(String alkTel) {
			AlkTel = alkTel;
		}
		
		
				
		

}

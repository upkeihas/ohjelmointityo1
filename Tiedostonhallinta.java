import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;						//BigInteger ja BigDecimal suuria lukuja varten

/*T�m� tiedosto sis�lt�� toiminnot tiedostoihin kirjoittamista ja niist�
*lukemista varten. Osittain p��llekk�inen Tilinhallinta-luokan kanssa.
*/

public class Tiedostonhallinta {

	public static BigInteger tilinumero = BigInteger.valueOf(0);	//Tilin tiedot luetaan n�ihin muuttujiin. Ohjelma ei suoraan
	public static double pinkoodi = 0;				//muokkaa tilitiedostoa, siihen kirjoitetaan ainoastaan
	public static BigDecimal tilinsaldo = BigDecimal.valueOf(0);	//tilin saldon muuttuessa.
		
	
	static BigInteger getTilinro() {				//Palauttaa tilinumeron (toimintoa k�ytet��n kirjautumisvaiheessa
		return tilinumero;					//varmentamaan, ett� k�ytt�j�n sy�tt�m� tilitiedosto on oikeasti
	}//getTilinro							//olemassa.)
	
	static double getPin() {					//Palauttaa tilitiedostosta luetun pin-koodin
		return pinkoodi;
	}//getPin
	
	static BigDecimal getSaldo() {					//Lukee tilin saldon tiedostosta ja palauttaa sen 
		try {
		lueTiedot(tilinumero);
		} catch (IOException ioe) {System.out.println("Tietojen lukeminen tilitiedostosta epaonnistui");}
		return tilinsaldo;
	}//getSaldo

	static void setSaldo (BigDecimal saldo) {			//Asettaa tilin saldon (Kirjoittaa tilitiedoston)
		String pin = Double.toString(pinkoodi);			//Muutetaan tilin tiedot stringiksi kirjoittamista varten
		String sld = saldo.toString();				//Pin-koodia ei voi vaihtaa t�ll� ohjelmalla, mutta se kirjoitetaan
		String tiedostonimi = tilinumero.toString();		//silti uudestaan aina tilitiedostoa muutettaessa. T�m� on er�s
									//toiminnallisuus johon en ole tyytyv�inen ohjelmassa.
		try { 
		FileWriter fw = new FileWriter(tiedostonimi + ".tili");
		BufferedWriter out = new BufferedWriter(fw); 
		out.write(pin + "\n");					//T�ss� kirjoitetaan pin-koodi ja rivinvaihtomerkki
		out.write(sld); 					//T�ss� kirjoitetaan saldo
		out.close(); 
		} catch (IOException ioe) { System.out.println("Tilitiedoston kirjoitus ei onnistunut!"); } 		
	}//setSaldo

	static void lueTiedot (BigInteger tnro) throws IOException{	//Lukee tiedot tilitiedostosta ajonaikaisiin muuttujiin.
									//Tilitiedostoon kirjoitetaan ainoastaan saldoa asetettaessa.
		tilinumero = tnro;					//lueTiedot-metodi saa tilinumeron p��ohjelmalta BigInteger-tyyppisen�
		String tiedostonimi = tnro.toString();			//Asetetaan muuttuja tilinumero samaksi kuin k�ytt�j�n sy�tt�m�
									//tilinumero
		try {
		FileReader fr = new FileReader(tiedostonimi + ".tili");
		BufferedReader br = new BufferedReader(fr);

		pinkoodi = Double.parseDouble(br.readLine());		//Luetaan pin-koodi ja saldo paikallisiin muuttujiin
		String tmp = br.readLine();
		tilinsaldo = new BigDecimal(tmp);
		fr.close();
		} catch(FileNotFoundException fnfe) { System.out.println("Tilitiedostoa ei loydy!"); tilinumero = BigInteger.valueOf(0);}
									//Jos k�ytt�j�n sy�tt�m�ll� tilinumerolla ei l�ydy tilitiedostoa,
									//asetetaan muuttuja tilinumero nollaksi, jolloin p��ohjelma kysyy
									//tilinumeroa kunnes l�ytyy t�sm��v� tiedosto.
	}//lueTiedot
		

}//Tiedostonhallinta
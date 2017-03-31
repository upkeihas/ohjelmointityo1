import java.math.*;
/* T�m� tiedosto sis�lt�� kaikki tilink�ytt��n liittyv�t toimenpiteet,
* eli saldojen laskemisen ja summien py�rist�misen.
*/

public class Tilinkaytto {
	
	static double getPin() {			//Pyyt�� tiedostonhallinnalta pin-koodin ja palauttaa sen doublena
		return Tiedostonhallinta.getPin();
	}//getPin
	
	static BigInteger getTilinro() {		//Pyyt�� tiedostonhallinnalta tilinumeron ja palauttaa sen BigIntegerin�
		return Tiedostonhallinta.getTilinro();
	}//getTilinro

	static BigDecimal getSaldo() {			//Pyyt�� tiedostonhallinnalta saldon ja palauttaa sen BigDecimalina.
		return Tiedostonhallinta.getSaldo();
	}//getSaldo

	static void nostaRahaa (BigDecimal saldo) {	//V�hent�� tililt� summan saldo. Saldomuutos v�litet��n BigDecimalina
	  BigDecimal uusisaldo = BigDecimal.valueOf(0);	//ja arvot py�ristet��n kahteen desimaaliin.
	  uusisaldo = Tiedostonhallinta.getSaldo();
	  saldo = saldo.setScale(2,6);
	  uusisaldo = uusisaldo.subtract(saldo);
	  uusisaldo = uusisaldo.setScale(2,6);
	  Tiedostonhallinta.setSaldo(uusisaldo);	//Funktio setSaldo kirjoittaa tilitiedostoon nyt py�ristetyn arvon joka
	} //nostaRahaa					//v�litet��n BigDecimalina.
	
	static void talletaRahaa (BigDecimal saldo) {	//Lis�� tilille summan saldo
	  BigDecimal uusisaldo = BigDecimal.valueOf(0);
	  uusisaldo = Tiedostonhallinta.getSaldo();
	  saldo = saldo.setScale(2,6);			//Py�rist�� k�ytt�j�n sy�tt�m�n summan, joka "nostetaan" tililt�
	  uusisaldo = uusisaldo.add(saldo);
	  uusisaldo = uusisaldo.setScale(2,6);
	  Tiedostonhallinta.setSaldo(uusisaldo);	//Asettaa tilin uuden saldon.
	} //talletaRahaa

}//Tilinkaytto
import java.math.*;
/* Tämä tiedosto sisältää kaikki tilinkäyttöön liittyvät toimenpiteet,
* eli saldojen laskemisen ja summien pyöristämisen.
*/

public class Tilinkaytto {
	
	static double getPin() {			//Pyytää tiedostonhallinnalta pin-koodin ja palauttaa sen doublena
		return Tiedostonhallinta.getPin();
	}//getPin
	
	static BigInteger getTilinro() {		//Pyytää tiedostonhallinnalta tilinumeron ja palauttaa sen BigIntegerinä
		return Tiedostonhallinta.getTilinro();
	}//getTilinro

	static BigDecimal getSaldo() {			//Pyytää tiedostonhallinnalta saldon ja palauttaa sen BigDecimalina.
		return Tiedostonhallinta.getSaldo();
	}//getSaldo

	static void nostaRahaa (BigDecimal saldo) {	//Vähentää tililtä summan saldo. Saldomuutos välitetään BigDecimalina
	  BigDecimal uusisaldo = BigDecimal.valueOf(0);	//ja arvot pyöristetään kahteen desimaaliin.
	  uusisaldo = Tiedostonhallinta.getSaldo();
	  saldo = saldo.setScale(2,6);
	  uusisaldo = uusisaldo.subtract(saldo);
	  uusisaldo = uusisaldo.setScale(2,6);
	  Tiedostonhallinta.setSaldo(uusisaldo);	//Funktio setSaldo kirjoittaa tilitiedostoon nyt pyöristetyn arvon joka
	} //nostaRahaa					//välitetään BigDecimalina.
	
	static void talletaRahaa (BigDecimal saldo) {	//Lisää tilille summan saldo
	  BigDecimal uusisaldo = BigDecimal.valueOf(0);
	  uusisaldo = Tiedostonhallinta.getSaldo();
	  saldo = saldo.setScale(2,6);			//Pyöristää käyttäjän syöttämän summan, joka "nostetaan" tililtä
	  uusisaldo = uusisaldo.add(saldo);
	  uusisaldo = uusisaldo.setScale(2,6);
	  Tiedostonhallinta.setSaldo(uusisaldo);	//Asettaa tilin uuden saldon.
	} //talletaRahaa

}//Tilinkaytto
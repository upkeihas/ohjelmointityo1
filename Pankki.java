import java.io.*;
import java.math.BigDecimal;					  //BigDecimal järjettömän suuria lukuja varten (tilin saldo ja saldomuutokset)
import java.math.BigInteger;					  //BigInteger tilinumeron välittämistä varten.

/*Tämä ohjelma on yksinkertainen pankkiautomaatti, jolla pystyy nostamaan ja tallettamaan "rahaa"
* tilitiedostoihin. Pankkitoiminnot on suojattu pin-koodilla.
*/

public class Pankki {

	public static void main (String [] args)throws IOException 
	{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		int valinta; 					   //Kokonaislukumuuttuja alkuvalikkoa varten
		BigInteger tilinro = BigInteger.valueOf(0);	   //Käyttäjältä kysyttävä käsiteltävän tilin numero.
		BigDecimal saldomuutos = BigDecimal.valueOf(0);	   //Väliaikainen muuttuja jota käytetään saldomuutoksiin
		boolean pinok = false;				   //Boolean-muuttuja jota käytetään pin-koodin tarkistuksen apuna.
								   //Muuttuja saa arvon true käyttäjän syötettyä tilitiedostoon täsmäävän
								   //pin-koodin.
		

		System.out.println("\nAutomaaginen pankkiautomaatti v 1.0:\n"); //Tervetuloviesti

		do {
		System.out.print("Anna tilinumero > ");
		tilinro = lueBigInteger();
		Tiedostonhallinta.lueTiedot(tilinro);
		} while (tilinro != Tilinkaytto.getTilinro());
		

		do {
			valikko();                                //Metodi valikko() joka tulostaa alkuvalikon
			System.out.print("\nSyota valinta > ");
			valinta = lueKokonaisluku();		  //Kysytään käyttäjältä valikkovalinta
			switch ( valinta ){
			 case 1: //Nosta rahaa

			   while (!pinok) {			  //Tarkistetaan onko käyttäjä syöttänyt PIN-koodinsa.
				tarkistapin();			  //Ellei ole, se kysytään ja samalla muuttuja pinok saa arvon true,
				pinok = true;			  //jolla varmistetaan ettei PIN-koodia kysytä enää uudestaan. metodi tarkistapin()
			   }					  //huolehtii siitä että pin-koodi on oikea.

			   System.out.print("\nSyota summa jonka haluat nostaa > ");
			   saldomuutos = lueisoKokonaisluku();    //Kysytään käyttäjältä nostettava summa
			   Tilinkaytto.nostaRahaa(saldomuutos);   //Kutsutaan Tilinkaytto-aliohjelmaa joka hoitelee siististi
			   System.out.println("\n");		  //monimutkaiset tilinkäyttöoperaatiot.
			   break;

			 case 2: //Talleta rahaa

			   while (!pinok) { 
				tarkistapin();
				pinok = true; 
			   }

			   System.out.print("\nSyota summa jonka haluat tallettaa > ");
			   saldomuutos = lueisoKokonaisluku();	  //Kysytään käyttäjältä talletettava summa
			   Tilinkaytto.talletaRahaa(saldomuutos); //Kutsutaan aliohjelmaa jolle välitetään talletettava summa
			   System.out.println("\n");		  //muuttujassa saldomuutos (tyyppi BigDecimal)
			  break;

			 case 3: //Saldokysely

			   while (!pinok) { 
				tarkistapin();
				pinok = true; 
			   }

			  System.out.print("\nTilisi saldo on ");
			  System.out.print(Tilinkaytto.getSaldo()); //Tilin saldon tulostus
			  System.out.println(" rahayksikkoa.\n");
			  break;

			 case 4: //Lopetus

			  System.out.println("\nKiitos kaynnista ja tervetuloa uudelleen!!1\n"); //Kohtelias loppuviesti
			  break;

			 default:
			  System.out.println("\nVirheellinen valinta. (paina 1, 2, 3 tai 4)\n"); //Oletusviesti jos ohjelma saa virheellisen syötteen
		   } //switch									 //päävalikossa.

		} while (valinta != 4);				   //Koko ohjelman selkäranka-looppi: ohjelman suoritus loppuu ehdon täyttyessä
	
	}//main

public static void valikko(){					   //Tulostaa valikon. Tämä valikko tukee vain 5-numeroisia tilinumeroita,
	System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");	   //muuten muotoilu pettää rivillä jolla tilinumero näytetään käyttäjälle.
	System.out.print("$ Kaytettava tili: "); 
	System.out.print(Tilinkaytto.getTilinro());
	System.out.println("     $");
	System.out.println("$      1. Nosta rahaa        $");
	System.out.println("$      2. Talleta rahaa      $");
	System.out.println("$      3. Saldokysely        $");
	System.out.println("$      4. Lopeta             $");
	System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
	}//valikko

public static void tarkistapin(){				   //tarkistapin-metodi joka pyytää käyttäjää syöttämään PIN-koodiansa
	double pinkoodi = 0;					   //niin kauan että se on syötetty oikein.
	while (pinkoodi != Tilinkaytto.getPin()) {		   //Tilinkaytto.getPin() noutaa tiedostosta oikean PIN-koodin vertailtavaksi.
	System.out.print("\nSyota PIN-koodi > ");
	pinkoodi = lueReaaliluku();
	}
	}//tarkistapin

public static double lueReaaliluku(){				   //lueReaaliluku- ja lueKokonaisluku -metodit, lainattu JO-kurssin harjoitustöistä

	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
       		
	double luku = 0.0;
	boolean ok = false; // ilmaisee onko lukeminen onnistunut vai ei

	do {
		try {
			luku = Double.parseDouble(stdin.readLine());
			ok = true;
           
		}catch(NumberFormatException nfe){
			System.out.print("\nSyotteen on oltava reaaliluku > ");

		}catch(IOException ioe){
          
			System.out.print("\nSyotteen lukemisessa tapahtui virhe > ");
		} //try-catch
        
	}while(!ok);

	return luku;
      
	}//lueReaaliluku

public static int lueKokonaisluku(){

	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	int luku = 0;
	boolean ok = false;

	do {
		try {
			luku = Integer.parseInt(stdin.readLine());
			ok = true;

		}
		catch(NumberFormatException nfe){
			System.out.print("\nSyotteen on oltava kokonaisluku > ");

		}catch(IOException ioe){
			System.out.print("\nSyotteen lukemisessa tapahtui virhe > ");
		} //try-catch

	}while(!ok);
	
	return luku;
	}//lueKokonaisluku

public static BigDecimal lueisoKokonaisluku() throws IOException{	//lueisoKokonaisluku, tehty käyttäen mallina kurssitöistä lainattuja	
									//syötteentarkistusmetodeja.
	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	String mjono = null;
	boolean ok = false;
	int pistelkm = 0;
	boolean lukuok = true;

        while (!ok) {
		try {
			mjono = stdin.readLine();		//Käytäjä syöttää lukuja string-tyyppisinä, jonka jälkeen
			int i;					//metodi tarkistaa ettei syöte sisällä numeroiden ja korkeintaan
			Character x = new Character (' ');	//yhden pisteen lisäksi muita merkkejä.
			pistelkm = 0;
			lukuok = true;
					
			for (i=0; i < mjono.length(); i++){
			x = mjono.charAt(i);
				if (x.charValue() == 46) {	//x.charvalue() == 46 tarkoittaa pistettä
				  pistelkm++;}
				else if (Character.isDigit(x) == false && x.charValue() != 46){ //Tarkistetaan syötteen muut merkit
				  lukuok = false;}
			}
		}catch( IOException ioe){
			System.out.println("\nVirhe lukemisessa > ");

		}

		if (pistelkm <= 1 && lukuok)
			ok = true;
		else
			System.out.print("\nLuvun tulee sisaltaa vain numeroita ja korkeintaan yksi piste > ");
		
	}
	
	BigDecimal luku = new BigDecimal(mjono);
	return luku;						//Palautetaan tarkistettu syöte tyyppinä BigDecimal
	}//lueisoKokonaisluku

public static BigInteger lueBigInteger() throws IOException{	//lueBigInteger tarkistaa, että käyttäjä syöttää ainoastaan numeroita.

	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	String mjono = null;
	boolean ok = true;

        do {
		
		try {
			mjono = stdin.readLine();		//Tämä kaunis läjä koodia hoitaa syötteen tarkistuksen
			int i;
			Character x = new Character (' ');
			ok = true;
					
			for (i=0; i < mjono.length(); i++){
			x = mjono.charAt(i);			//Tarkistetaan syöte merkki kerrallaan. Vain numerot kelpaavat.
				if (Character.isDigit(x) == false) {
				  ok = false; }
			}
		}catch( IOException ioe){
			System.out.println("\nVirhe lukemisessa > ");

		}

		if (!ok)
			System.out.print("\nLuvun tulee sisaltaa vain numeroita > ");
		
	} while(!ok);
	
	BigInteger luku = new BigInteger(mjono);
	return luku;						//Palautetaan tarkistettu syöte BigIntegerina.
	}//lueBigInteger

}//Pankki
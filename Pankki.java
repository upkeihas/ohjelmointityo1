import java.io.*;
import java.math.BigDecimal;					  //BigDecimal j�rjett�m�n suuria lukuja varten (tilin saldo ja saldomuutokset)
import java.math.BigInteger;					  //BigInteger tilinumeron v�litt�mist� varten.

/*T�m� ohjelma on yksinkertainen pankkiautomaatti, jolla pystyy nostamaan ja tallettamaan "rahaa"
* tilitiedostoihin. Pankkitoiminnot on suojattu pin-koodilla.
*/

public class Pankki {

	public static void main (String [] args)throws IOException 
	{
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		int valinta; 					   //Kokonaislukumuuttuja alkuvalikkoa varten
		BigInteger tilinro = BigInteger.valueOf(0);	   //K�ytt�j�lt� kysytt�v� k�sitelt�v�n tilin numero.
		BigDecimal saldomuutos = BigDecimal.valueOf(0);	   //V�liaikainen muuttuja jota k�ytet��n saldomuutoksiin
		boolean pinok = false;				   //Boolean-muuttuja jota k�ytet��n pin-koodin tarkistuksen apuna.
								   //Muuttuja saa arvon true k�ytt�j�n sy�tetty� tilitiedostoon t�sm��v�n
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
			valinta = lueKokonaisluku();		  //Kysyt��n k�ytt�j�lt� valikkovalinta
			switch ( valinta ){
			 case 1: //Nosta rahaa

			   while (!pinok) {			  //Tarkistetaan onko k�ytt�j� sy�tt�nyt PIN-koodinsa.
				tarkistapin();			  //Ellei ole, se kysyt��n ja samalla muuttuja pinok saa arvon true,
				pinok = true;			  //jolla varmistetaan ettei PIN-koodia kysyt� en�� uudestaan. metodi tarkistapin()
			   }					  //huolehtii siit� ett� pin-koodi on oikea.

			   System.out.print("\nSyota summa jonka haluat nostaa > ");
			   saldomuutos = lueisoKokonaisluku();    //Kysyt��n k�ytt�j�lt� nostettava summa
			   Tilinkaytto.nostaRahaa(saldomuutos);   //Kutsutaan Tilinkaytto-aliohjelmaa joka hoitelee siististi
			   System.out.println("\n");		  //monimutkaiset tilink�ytt�operaatiot.
			   break;

			 case 2: //Talleta rahaa

			   while (!pinok) { 
				tarkistapin();
				pinok = true; 
			   }

			   System.out.print("\nSyota summa jonka haluat tallettaa > ");
			   saldomuutos = lueisoKokonaisluku();	  //Kysyt��n k�ytt�j�lt� talletettava summa
			   Tilinkaytto.talletaRahaa(saldomuutos); //Kutsutaan aliohjelmaa jolle v�litet��n talletettava summa
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
			  System.out.println("\nVirheellinen valinta. (paina 1, 2, 3 tai 4)\n"); //Oletusviesti jos ohjelma saa virheellisen sy�tteen
		   } //switch									 //p��valikossa.

		} while (valinta != 4);				   //Koko ohjelman selk�ranka-looppi: ohjelman suoritus loppuu ehdon t�yttyess�
	
	}//main

public static void valikko(){					   //Tulostaa valikon. T�m� valikko tukee vain 5-numeroisia tilinumeroita,
	System.out.println("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");	   //muuten muotoilu pett�� rivill� jolla tilinumero n�ytet��n k�ytt�j�lle.
	System.out.print("$ Kaytettava tili: "); 
	System.out.print(Tilinkaytto.getTilinro());
	System.out.println("     $");
	System.out.println("$      1. Nosta rahaa        $");
	System.out.println("$      2. Talleta rahaa      $");
	System.out.println("$      3. Saldokysely        $");
	System.out.println("$      4. Lopeta             $");
	System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
	}//valikko

public static void tarkistapin(){				   //tarkistapin-metodi joka pyyt�� k�ytt�j�� sy�tt�m��n PIN-koodiansa
	double pinkoodi = 0;					   //niin kauan ett� se on sy�tetty oikein.
	while (pinkoodi != Tilinkaytto.getPin()) {		   //Tilinkaytto.getPin() noutaa tiedostosta oikean PIN-koodin vertailtavaksi.
	System.out.print("\nSyota PIN-koodi > ");
	pinkoodi = lueReaaliluku();
	}
	}//tarkistapin

public static double lueReaaliluku(){				   //lueReaaliluku- ja lueKokonaisluku -metodit, lainattu JO-kurssin harjoitust�ist�

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

public static BigDecimal lueisoKokonaisluku() throws IOException{	//lueisoKokonaisluku, tehty k�ytt�en mallina kurssit�ist� lainattuja	
									//sy�tteentarkistusmetodeja.
	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	String mjono = null;
	boolean ok = false;
	int pistelkm = 0;
	boolean lukuok = true;

        while (!ok) {
		try {
			mjono = stdin.readLine();		//K�yt�j� sy�tt�� lukuja string-tyyppisin�, jonka j�lkeen
			int i;					//metodi tarkistaa ettei sy�te sis�ll� numeroiden ja korkeintaan
			Character x = new Character (' ');	//yhden pisteen lis�ksi muita merkkej�.
			pistelkm = 0;
			lukuok = true;
					
			for (i=0; i < mjono.length(); i++){
			x = mjono.charAt(i);
				if (x.charValue() == 46) {	//x.charvalue() == 46 tarkoittaa pistett�
				  pistelkm++;}
				else if (Character.isDigit(x) == false && x.charValue() != 46){ //Tarkistetaan sy�tteen muut merkit
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
	return luku;						//Palautetaan tarkistettu sy�te tyyppin� BigDecimal
	}//lueisoKokonaisluku

public static BigInteger lueBigInteger() throws IOException{	//lueBigInteger tarkistaa, ett� k�ytt�j� sy�tt�� ainoastaan numeroita.

	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	String mjono = null;
	boolean ok = true;

        do {
		
		try {
			mjono = stdin.readLine();		//T�m� kaunis l�j� koodia hoitaa sy�tteen tarkistuksen
			int i;
			Character x = new Character (' ');
			ok = true;
					
			for (i=0; i < mjono.length(); i++){
			x = mjono.charAt(i);			//Tarkistetaan sy�te merkki kerrallaan. Vain numerot kelpaavat.
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
	return luku;						//Palautetaan tarkistettu sy�te BigIntegerina.
	}//lueBigInteger

}//Pankki
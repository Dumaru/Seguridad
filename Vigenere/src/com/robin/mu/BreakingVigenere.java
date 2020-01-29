package com.robin.mu;
import java.util.ArrayList;
import java.util.Collections.*;
import java.util.HashMap;
import java.util.List;
public class BreakingVigenere {
	public static final String[] SPANISH_ALPHABET = new String[] {
			"A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z"
	};
	
	public static final String SPANISH_ALPHABET_STR = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
	
	public static String encrypt(String text, String key){
		System.out.println("Alfabeto "+ SPANISH_ALPHABET_STR);
		String encrypted = "";
		int L_ALPHABET = SPANISH_ALPHABET_STR.length();
		int xi;
		int ki;
		
		HashMap<Integer, Integer> textKeyPairsIndex = new HashMap<Integer, Integer>();
		for(int i=0,j=0; i < text.length(); ++i) {
			textKeyPairsIndex.put(i, j);
			j = (j==key.length()-1? 0: ++j);
		}
		
		for(int i=0; i < text.length(); ++i) {
			/* E(xi) = (Xi+Ki) mod L
			 * En términos matemáticos puede expresarse la función de cifrado como: E ( X i ) = ( X i + K i ) mod   L 
			Donde 
			Xi es la letra en la posición i del texto a cifrar, 
			Ki es el carácter de la clave correspondiente a Xi, pues se encuentran en la misma posición, y 
			L es el tamaño del alfabeto. En este caso L = 27  
			* */
			xi = SPANISH_ALPHABET_STR.indexOf(text.charAt(i));
			ki = SPANISH_ALPHABET_STR.indexOf(key.charAt(textKeyPairsIndex.get(i)));
			encrypted += SPANISH_ALPHABET[(xi+ki)%L_ALPHABET];
		}
		return encrypted;
	}
	
	public static String[] crack(String text, int keylen) {
		List<String> keys = new ArrayList<String>();
		
		
		
		return (String[]) keys.toArray();
	}
	
	public static boolean verify(String plain, String encruypted, String key) {
		boolean match = false;
		
		
		return match;
	}
}

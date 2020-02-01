package com.robin.mu;
import java.util.ArrayList;
import java.util.Collections.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class BreakingVigenere {	
	// INFO: CHECK THE � ENE
	public static final String ALPHABET_STR = "ABCDEFGHIJKLMN�OPQRSTUVWXYZ";
	
	public static final HashMap<Character, Float> ALPHABET_FRECUENCY_MODEL= new HashMap<Character, Float>(){
		{
			put('E', 0.1365F);
			put('A', 0.11797F);
			put('O', 0.09195F);
			put('S', 0.07983F);
			put('R', 0.06696F);
			put('N', 0.0669F);
			put('I', 0.0686F);
			put('L', 0.0527F);
			put('D', 0.0519F);
			put('C', 0.04919F);
			put('T', 0.04802F);
			put('P', 0.03445F);
			put('U', 0.03996F);
			put('M', 0.02925F);
			put('B', 0.01F);
			put('F', 0.00953F);
			put('V', 0.00693F);
			put('Q', 0.00875F);
			put('G', 0.00943F);
			put('H', 0.00585F);
			put('J', 0.00272F);
			put('�', 0.00074F);
			put('K', 0.00022F);
			put('W', 0.00019F);
			put('X', 0.0183F);
			put('Y', 0.0523F);
			put('Z', 0.00291F);
		}
	}; 
	
	/** Encrypts the @text parameter using the @key
	 * @param text
	 * @param key
	 * @return an encrypted String
	 */
	public static String encrypt(String text, String key){
		String textUpper = text.toUpperCase();
		String keyUpper = key.toUpperCase();
		//System.out.println("Alfabeto "+ ALPHABET_STR);
		String encrypted = "";
		int L_ALPHABET = ALPHABET_STR.length();
		int xi;
		int ki;
		
		HashMap<Integer, Integer> textKeyPairsIndex = new HashMap<Integer, Integer>();
		String expanded = "";
		for(int i=0,j=0; i < textUpper.length(); ++i) {
			expanded += keyUpper.charAt(j);
			textKeyPairsIndex.put(i, j);
			j = (j==keyUpper.length()-1? 0: ++j);
		}
		
		for(int i=0; i < textUpper.length(); ++i) {
			/* E(xi) = (Xi+Ki) mod L
			 * En t�rminos matem�ticos puede expresarse la funci�n de cifrado como: E ( X i ) = ( X i + K i ) mod   L 
			Donde 
			Xi es la letra en la posici�n i del texto a cifrar, 
			Ki es el car�cter de la clave correspondiente a Xi, pues se encuentran en la misma posici�n, y 
			L es el tama�o del alfabeto. En este caso L = 27  
			* */
			xi = ALPHABET_STR.indexOf(textUpper.charAt(i));
			ki = ALPHABET_STR.indexOf(keyUpper.charAt(textKeyPairsIndex.get(i)));
			encrypted += ALPHABET_STR.charAt((xi+ki)%L_ALPHABET);
		}
		return encrypted;
	}
	
	
	/**Decrypts a text using the key parameter
	 * @param encryptedText
	 * @param key
	 * @return
	 */
	public static String decrypt(String encryptedText, String key) {
		String encryptedUpper = encryptedText.toUpperCase();
		String keyUpper = key.toUpperCase();
		String plainText = "";

		// Cuando (Ci - Ki) < 0
		int L_ALPHABET = ALPHABET_STR.length();
		int ci;
		int ki;
		
		HashMap<Integer, Integer> textKeyPairsIndex = new HashMap<Integer, Integer>();
		for(int i=0,j=0; i < encryptedUpper.length(); ++i) {
			textKeyPairsIndex.put(i, j);
			j = (j==keyUpper.length()-1? 0: ++j);
		}
		
		for(int i=0; i < encryptedUpper.length(); ++i) {
			ci = ALPHABET_STR.indexOf(encryptedUpper.charAt(i));
			ki = ALPHABET_STR.indexOf(keyUpper.charAt(textKeyPairsIndex.get(i)));
			// Cuando (Ci - Ki) >= 0 
			if(ci-ki>=0) {
				plainText += ALPHABET_STR.charAt((ci-ki)%L_ALPHABET);
			}else {
				plainText += ALPHABET_STR.charAt((ci-ki+L_ALPHABET)%L_ALPHABET);				
			}
		}
		
		return plainText;
	}

	
	/**Cracks the encrypted text and returns the possible keys
	 * @param text
	 * @param keylen
	 * @return the possible keys
	 */
	public static String[] crack(String text, int keylen) {
		String textUpper = text.toUpperCase();
		List<String> keys = new ArrayList<String>();
		// Create bigrams with those that are repeated in the text
		List<String> blocks = new ArrayList<String>();
		for(int i=0; i < textUpper.length(); i += keylen) {
			int upper = (i+keylen > textUpper.length() ? textUpper.length(): i+keylen);
			blocks.add(textUpper.substring(i, upper));
		}
		
		// Each column is encyphered with the same key so it's a one-alphabet subtitution 
		// thus it can be attacked with frecuency analysis
		// Extract all the columns to analyse with the frecuency
		List<TextoMonoalfabetico> messages = new ArrayList<TextoMonoalfabetico>();
		for(int i=0; i < keylen; ++i) {
			String message = "";
			for(int j=0; j < blocks.size(); ++j) {
				try {
					message += blocks.get(j).charAt(i);
				} catch (IndexOutOfBoundsException e) {
					
				}
			}
			messages.add(new TextoMonoalfabetico(message));
		}
		
		// Setear claves para los mensajes de la misma columna con los n mejores, (El ultimo tiene mayor correlacion xd)
		int n = 10;  
		for(int i=0; i < messages.size(); ++i) {
			// The correlation comes ordered from lest to greater
			HashMap<Integer, Float> corrs =  messages.get(i).getCorrelationTableWithAlphabet(ALPHABET_STR, ALPHABET_FRECUENCY_MODEL);
			int j=0;
			for(Entry<Integer, Float> entry: corrs.entrySet()) {
				if(j>=corrs.size()-n) {
					messages.get(i).getKeys().add(entry.getKey());
				}
				j++;
			}
		}
		// Sets the keys with the lengt of the messages that is the lenght of the key because they are the columns 
		for(int i=0; i < n; ++i) {
			String tempKey = "";
			for(int j=0; j < messages.size(); ++j) {
				int keyIndex = messages.get(j).getKeys().get(i);
				tempKey += ALPHABET_STR.charAt(keyIndex);
			}
			keys.add(tempKey);
		}
		System.out.println(messages);
		
		return keys.toArray(new String[keys.size()]);
	}
	
	public List<String> getKeysCombined() {
		List<String> combinados = new ArrayList<String>();
		
		
		return combinados;
	}
	
	public static int getKeyLen(String encryptedText) {
		int keylen = 0;
		List<String> bigrams = new ArrayList<String>();
		for(int i=0,j=2; i < encryptedText.length(); j+=2,++i) {
			
		}
		
		return keylen;
	}
	
	/**Verifies the if the encryption process is correct, comparing the plain text with the encrypted 
	 * message after it was decrypted.
	 * @param plain
	 * @param encruypted
	 * @param key
	 * @return
	 */
	public static boolean verify(String plain, String encruypted, String key) {
		String plainUpper = plain.toUpperCase();
		String encryptedUpper = encruypted.toUpperCase();
		String keyUpper = key.toUpperCase();
		boolean match = false;
		
		
		return match;
	}
}

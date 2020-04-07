package com.robin.mu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Collections.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

public class BreakingVigenere {	
	// INFO: CHECK THE Ñ ENE
	//public static final String ALPHABET_STR = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
	public static final String ALPHABET_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
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
			//put('Ñ', 0.00074F);
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
			 * En tï¿½rminos matemï¿½ticos puede expresarse la funciï¿½n de cifrado como: E ( X i ) = ( X i + K i ) mod   L 
			Donde 
			Xi es la letra en la posiciï¿½n i del texto a cifrar, 
			Ki es el carï¿½cter de la clave correspondiente a Xi, pues se encuentran en la misma posiciï¿½n, y 
			L es el tamaï¿½o del alfabeto. En este caso L = 27  
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
		int n = 5;  
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
		
		// Keys combined
		
		keys = getKeysCombined(keys);
		
		// Search for the better keys within all the keys with their respective decrypted messages
		HashMap<String, String> decryptedMessages = new HashMap<String, String>();
		for(int i=0; i < keys.size(); ++i) {
			decryptedMessages.put(keys.get(i), decrypt(textUpper, keys.get(i)));
		}
		keys = ordenarKeys(decryptedMessages);
		return keys.toArray(new String[keys.size()]);
	}
	
	public static List<String> ordenarKeys(HashMap<String, String> keys_plan){
		HashMap<String, Integer> clavesMejor = new HashMap<String, Integer>();
		CorpusHandler corpusHandler = new CorpusHandler("500_formas_Spanish.txt");
		corpusHandler.setCommonWords();
		
		// Key-Plain text
		for(Entry<String, String> entryK_E : keys_plan.entrySet()) {
			int wordCounter = 0;
			for(String word: corpusHandler.getCommonWords()) {
				wordCounter += (entryK_E.getValue().contains(word)?1:0);
			}
			clavesMejor.put(entryK_E.getKey(), wordCounter);
		}
		
		// Ordenamiento
		Comparator<Entry<String, Integer>> valueComparator = new Comparator<Entry<String, Integer>>() {
		            
		            @Override
		            public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
		                Integer v1 = e1.getValue();
		                Integer v2 = e2.getValue();
		                return v1.compareTo(v2);
		            }
		};
		// Llenar lista de entries del map
		List<Entry<String, Integer>> listOfEntries = new ArrayList<Entry<String, Integer>>();
		for(Entry<String, Integer> entry: clavesMejor.entrySet()) {
			listOfEntries.add(entry);
		}
		// Uses the comparator to sort the all the entries
		Collections.sort(listOfEntries, valueComparator);
		LinkedHashMap<String, Integer> sortedByValue = new LinkedHashMap<String, Integer>(listOfEntries.size());
        
        // copying entries from List to Map
        for(Entry<String, Integer> entry : listOfEntries){
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        
		//return Arrays.asList(clavesMejor.keySet().toArray(new String[clavesMejor.size()]));
        List<String> lista = Arrays.asList(sortedByValue.keySet().toArray(new String[clavesMejor.size()]));
        Collections.reverse(lista);
        int keysAmount = 125;
        
        return lista.subList(0, keysAmount);
	}
	public static List<String> getKeysCombined(List<String> keysLineByLine) {
		List<String> combinados = new ArrayList<String>();
		// Combine taking the first letter of each key with all the others
		List<String> columnas = new ArrayList<String>();
		for(int j=0; j < keysLineByLine.get(0).length(); ++j) {
			String columna = "";
			for(int i=0; i < keysLineByLine.size();  ++i) {
				columna += keysLineByLine.get(i).charAt(j);
			}
			columnas.add(columna);
		}
		combinados.addAll(Arrays.asList(columnas.get(0).split("")));
		columnas.remove(0);
		boolean expandido = false;
		int colIndex = 0;
		List<String> strExpanded = new ArrayList<String>();
		while(!expandido) {
			for(String expandedStr: combinados) {
					String key = "";
					for(int i=0; i < columnas.get(colIndex).length(); ++i) {
						key += expandedStr+""+columnas.get(colIndex).charAt(i);
						strExpanded.add(key);
						key="";
					}
			}
			colIndex++;
			combinados = (List<String>) ((ArrayList<String>)strExpanded).clone();
			strExpanded.clear();
			
			expandido = (colIndex > columnas.size()-1? true: false);
		}
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
	 * @return true if there is a match between the plan and decrypted messages with certain key
	 */
	public static boolean verify(String plain, String encruypted, String key) {
		String plainUpper = plain.toUpperCase();
		String encryptedUpper = encruypted.toUpperCase();
		String keyUpper = key.toUpperCase();		
		String decrypted = decrypt(encryptedUpper, keyUpper);
		boolean iguales = plainUpper.strip().equals(decrypted.strip());
		System.out.printf("Key:%s%nPlano:%s%nDesencriptado:%s",keyUpper, plain.toUpperCase(), decrypted.toUpperCase());
		return iguales;
	}
}

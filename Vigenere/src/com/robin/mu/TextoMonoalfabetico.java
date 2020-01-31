package com.robin.mu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextoMonoalfabetico {
	private String text;
	private HashMap<Character, Float> frecuencias = new HashMap<Character, Float>();
	
	public TextoMonoalfabetico() {
		
	}
	
	public TextoMonoalfabetico(String text) {
		this.text = text;
		this.setFrecuencias();
	}
	public void setFrecuencias() {
		HashMap<Character, Integer> repeticiones = new HashMap<Character, Integer>();
		for(int i=0; i < this.text.length(); ++i) {
			if(repeticiones.get(text.charAt(i)) != null) {
				repeticiones.replace(text.charAt(i), repeticiones.get(text.charAt(i))+1);
			}else {
				repeticiones.put(text.charAt(i), 1);
			}
		}
		
		for(Character c : repeticiones.keySet()) {
			this.frecuencias.put(c, (float)repeticiones.get(c)/this.text.length());
		}
	}
	
	public HashMap<Integer, Float> getCorrelationTableWithAlphabet(String alphabet, HashMap<Character, Float> alphabetFrecuencyModel){
		System.out.println("Longitud alphabeto "+alphabet.length()+" longitud corpus modelo "+alphabetFrecuencyModel.size());
		HashMap<Integer, Float> correlations = new HashMap<Integer, Float>();
		char[] lettersSet = this.getLettersSet();
		for(int i=0; i < alphabet.length(); ++i) {
			float phi_i = 0f;
			for(int j=0; j < this.frecuencias.size(); ++j) {
				float phi_c = this.frecuencias.get(lettersSet[j]); 
				int c = alphabet.indexOf(lettersSet[j]);
				int index = 0;
				if((c-i)>0) {
					index = (c-i)%alphabet.length();
				}else {
					index = (c-i+alphabet.length())%alphabet.length();
				}
				float proba_c_i = alphabetFrecuencyModel.get(alphabet.charAt(index));
				float temp_phi = phi_c * proba_c_i;
				phi_i += temp_phi;
			}
			correlations.put(i, phi_i);
			
		}
		return correlations;
	}

	public char[] getLettersSet() {
		char[] keysSet = new char[this.frecuencias.size()];
		int i=0;
		for(char c: this.frecuencias.keySet()) {
			keysSet[i] = c;
			i++;
		}
		return keysSet;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public HashMap<Character, Float> getFrecuencias() {
		return frecuencias;
	}

	public void setFrecuencias(HashMap<Character, Float> frecuencias) {
		this.frecuencias = frecuencias;
	}

	@Override
	public String toString() {
		return "TextoMonoalfabetico [text=" + text + ", frecuencias=" + frecuencias + "]";
	}
	
	
	public static void main(String[] args) {
		TextoMonoalfabetico textoMonoalfabetico = new TextoMonoalfabetico("KHOORZRUOG");
		String englishAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		HashMap<Character, Float> englishModel = new HashMap<Character, Float>();
		englishModel.put('A', 0.07984f);
		englishModel.put('B', 0.01511f);
		englishModel.put('C', 0.02504f);
		englishModel.put('D', 0.04262f);
		englishModel.put('E', 0.12452f);
		englishModel.put('F', 0.02262f);
		englishModel.put('G', 0.02013f);
		englishModel.put('H', 0.06384f);
		englishModel.put('I', 0.07000f);
		englishModel.put('J', 0.00131f);
		englishModel.put('K', 0.00741f);
		englishModel.put('L', 0.03961f);
		englishModel.put('M', 0.02629f);
		englishModel.put('N', 0.06876f);
		englishModel.put('O', 0.07691f);
		englishModel.put('P', 0.01741f);
		englishModel.put('Q', 0.00107f);
		englishModel.put('R', 0.05912f);
		englishModel.put('S', 0.06333f);
		englishModel.put('T', 0.09058f);
		englishModel.put('U', 0.02844f);
		englishModel.put('V', 0.01056f);
		englishModel.put('W', 0.02304f);
		englishModel.put('X', 0.00159f);
		englishModel.put('Y', 0.02028f);
		englishModel.put('Z', 0.00057f);
		textoMonoalfabetico.getCorrelationTableWithAlphabet(englishAlphabet, englishModel);

	}

}

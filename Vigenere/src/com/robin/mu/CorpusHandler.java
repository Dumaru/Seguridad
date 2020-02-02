package com.robin.mu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CorpusHandler {
	private String corpusPath="500_formas_Spanish.txt";
	private List<String> commonWords = new ArrayList<String>();
	public CorpusHandler() {
		
	}
	public CorpusHandler(String corpusPath) {
		this.corpusPath = corpusPath;
	}
	
	public void setCommonWords() {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(this.corpusPath));
			List<String> words = new ArrayList<String>();
			String word = "";
			while((word = reader.readLine()) != null) {
				words.add(word.strip().toUpperCase());
			}
			commonWords = words;
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	public String getCorpusPath() {
		return corpusPath;
	}
	public void setCorpusPath(String corpusPath) {
		this.corpusPath = corpusPath;
	}
	public List<String> getCommonWords() {
		return commonWords;
	}
	public void setCommonWords(List<String> commonWords) {
		this.commonWords = commonWords;
	}
	
	
//	@Override
//	public String toString() {
//		return "CorpusHandler [corpusPath=" + corpusPath + ", commonWords=" + commonWords + "]";
//	}
//	public static void main(String[] args) {
//		//String path = "C:\\Users\\robin\\Google Drive\\2020-1\\Seguridad\\SeguridadTrabajos\\Vigenere\\500_formas_Spanish.txt";
//		//String path = "500_formas_Spanish.txt";
//		
//		
//		CorpusHandler corpusHandler = new CorpusHandler();
//		corpusHandler.setCommonWords();
//		System.out.println(corpusHandler);
//	}
}

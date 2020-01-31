package com.robin.mu;

public class NgramInfo {
	private String ngram;
	private int location;
	private int distace;
	private int[] factors;
	
	
	public NgramInfo() {
		
	}
	public NgramInfo(String ngram, int location, int distace, int[] factors) {
		super();
		this.ngram = ngram;
		this.location = location;
		this.distace = distace;
		this.factors = factors;
	}
	public String getNgram() {
		return ngram;
	}
	public void setNgram(String ngram) {
		this.ngram = ngram;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getDistace() {
		return distace;
	}
	public void setDistace(int distace) {
		this.distace = distace;
	}
	public int[] getFactors() {
		return factors;
	}
	public void setFactors(int[] factors) {
		this.factors = factors;
	}
	
	

}

package com.robin.mu;

public class ValoremeElEsfuerzo {

	public static void main(String[] args) {
		
		//String encryptedMessage = BreakingVigenere.encrypt("THEBOYHASTHEBAG", "VIG");
		//String encryptedMessage = BreakingVigenere.encrypt("HOLAMUNDO", "AN");
		String encryptedMessage = BreakingVigenere.encrypt("PARISVAUTBIENUNEMESSE", "LOUP");
		String decryptedMessage = BreakingVigenere.decrypt(encryptedMessage, "LOUP");
		System.out.printf("\nMain:\nEncrypted %s%nPlain %s%n", encryptedMessage, decryptedMessage);
		
		// Crack stuff
		String[] keys = BreakingVigenere.crack(encryptedMessage, 4);
		System.out.printf("\nMain:\nEncrypted %s%nPlain %s%n", encryptedMessage, decryptedMessage);
		
	}

}

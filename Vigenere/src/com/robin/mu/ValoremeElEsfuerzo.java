package com.robin.mu;

import java.util.Arrays;

public class ValoremeElEsfuerzo {

	public static void main(String[] args) {
		String encryptedMessage = BreakingVigenere.encrypt("ESTEESUNMENSAJEMUYSECRETOPORFAVORNOMEMIRES", "LOUP");
		String decryptedMessage = BreakingVigenere.decrypt(encryptedMessage, "LOUP");
		System.out.printf("\nMain:\nEncrypted %s%nPlain decrypted %s%n", encryptedMessage, decryptedMessage);
		
		// Crack stuff
		String[] keys = BreakingVigenere.crack(encryptedMessage, 4);
		System.out.printf("\nMain:\nEncrypted %s%nPlain %s%n", encryptedMessage, decryptedMessage);
		System.out.println("Keys "+Arrays.deepToString(keys));
	}

}

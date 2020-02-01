package com.robin.mu;

import java.util.Arrays;

public class ValoremeElEsfuerzo {

	public static void main(String[] args) {
		//Testing
		/*
		String encryptedMessage = BreakingVigenere.encrypt("ESTEESUNMENSAJEMUYSECRETOPORFAVORNOMEMIRES", "LOUP");
		String decryptedMessage = BreakingVigenere.decrypt(encryptedMessage, "LOUP");
		System.out.printf("\nMain:\nEncrypted %s%nPlain decrypted %s%n", encryptedMessage, decryptedMessage);
		
		// Crack stuff
		System.out.printf("\nMain:\nEncrypted %s%nPlain %s%n", encrypted, decryptedMessage);
		System.out.println("Keys "+Arrays.deepToString(keys));
		*/
		String plain = "sehaencontradoafaceapplaaplicacindetransformacindefotografasbasadaenlainteligenciaartificialquerecientementesevolviviralporsufiltrodeedadperollegalostitularesporsupolmicapolticadeprivacidadquerecopilalalistadetusamigosdefacebooksinningunaraznelfaceappdefabricacinrusahaexistidodesdelaprimaveradeperosehavistoafectadoporlasredessocialesenlasltimassemanasyaquemillonesdepersonasdescargaronlaaplicacinparavercmoseverancuandoseanmayoresomenoresointercambiengneroslaaplicacintambincontieneunafuncinquepermitealosusuariosdescargaryeditarfotosdesuscuentasdefacebookquesolofuncionacuandounusuariopermitequefaceappaccedaalacuentaderedessocialesatravsdelaopcininiciarsesinconfacebook";
		String encrypted = "emusitowalvgpwnxeiqichpgmxyaggoqavizdiakjudunumtpmsgxusznxeynifshgqvysmtfmyakkzkvsexfqsagomtdmixqkvwrzqurfxkemigpbudvjerbwekylutgjsjqmqshvqzbdpksiygwzubhdexqacgvygxbdqooicgpzuknvivdqisgopiqiykdmpgtoxiyspoebnvizganemmaaqwjgomogsqeqafmtscasvglvrdjgomnhtjqnntvooiparxganzekjqflmjalrkhkxicjmsmdrjejqxrjsyqpnnmyfwnxiifiqgtudtnkvkpmfksiuiywwkztnkpzuunkwkyiaswemyhwqoxtbfiypmcwvyavnkhkeknjkgdwadegbtvueiuvcsvghmeuquemiwvgzkhsrjaarsrsmgbjiyaurfsxqabarzqzpsqhumayrkdwfdegbtvueiuvgsqhuvpgrzumawytmnhfgozyhwtkduvligxwfmwamzvgwjqapsvmmzlwhofiexszaaqwwaekhwrzmaqwjgomogsqccrksranhfgoavnuygzlbmraecnjmubmeemzqyhwjgomnhtgokrvegxipmitfiqwvkpmfksiuiywwgfznnwjqtngtiuvvfmiuiekiyuvpgrlmkrtsuw";
		
		String encryptedMessage = BreakingVigenere.encrypt(plain, "MINSEG");
		String decryptedMessage = BreakingVigenere.decrypt(encryptedMessage, "MINSEG");
		System.out.printf("\nMain:\nEncrypted:%s %nDecrypted:%s%n", encrypted, decryptedMessage);
		
		String[] keys = BreakingVigenere.crack(encrypted, 6);
		String solucionado = BreakingVigenere.decrypt(encrypted, keys[keys.length-1]);
		System.out.println(java.util.Arrays.deepToString(keys));
		System.out.println("ROTO, LO ROMPI");
		System.out.println(solucionado);
	}

}

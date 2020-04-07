package model;

/**
 * collection of utility routines for converting and displaying binary saved in
 * byte/short/int arrays, and loaded/displayed using hex.
 *
 * @author Lawrie Brown, Oct 2001
 */
public class Util {

	// ......................................................................
	// utility conversions between byte, short and int arrays

	public static byte[] short2byte(short[] sa) {
		int length = sa.length;
		byte[] ba = new byte[length * 2];
		for (int i = 0, j = 0, k; i < length;) {
			k = sa[i++];
			ba[j++] = (byte) ((k >>> 8) & 0xFF);
			ba[j++] = (byte) (k & 0xFF);
		}
		return (ba);
	}

	/**
	 * Utility method to count the amount of differences between to binary strings.
	 * 
	 * @param bin1 a string with binary like values
	 * @param bin2 a string with binary like values
	 * @return
	 */
	public static int bitsChangedAmount(String bin1, String bin2) {
		int amount = 0;
		//int start = (bin1.length() < bin2.length() ? bin1.length() : bin2.length());
		String strBin1 = "";
		String strBin2 = "";
		if(bin1.length() < bin2.length()) {
			// Completar b1
			for(int i=0; i < (bin2.length() - bin1.length()); i++) {
				strBin1 += "0";
			}
			// Completa con ceros y asigna el otro que estaba con mas bits a la nueva variable
			strBin1  += bin1;
			strBin2 = bin2;
			
		}else if(bin1.length() > bin2.length()) {
			// Completar b2
			for(int i=0; i < (bin1.length() - bin2.length()); i++) {
				strBin2 += "0";
			}
			strBin2  += bin2;
			strBin1 = bin1;
		}else {
			strBin1 = bin1;
			strBin2 = bin2;
		}
		
		System.out.println("Bin 1 "+ bin1);
		System.out.println("Bin 2 "+ bin2);
		System.out.println("Bin 1 C "+ strBin1);
		System.out.println("Bin 2 C "+ strBin2);
		// Counts all the places where the char is not the same
		for (int i = 0; i < strBin1.length(); ++i) {
			if (strBin1.charAt(i) != strBin2.charAt(i)) {
				amount++;
			}
		}
		return amount;
	}

	// ......................................................................
	public static short[] byte2short(byte[] ba) {
		int length = ba.length;
		short[] sa = new short[length / 2];
		for (int i = 0, j = 0; j < length / 2;) {
			sa[j++] = (short) (((ba[i++] & 0xFF) << 8) | ((ba[i++] & 0xFF)));
		}
		return (sa);
	}

	// ......................................................................
	public static byte[] int2byte(int[] ia) {
		int length = ia.length;
		byte[] ba = new byte[length * 4];
		for (int i = 0, j = 0, k; i < length;) {
			k = ia[i++];
			ba[j++] = (byte) ((k >>> 24) & 0xFF);
			ba[j++] = (byte) ((k >>> 16) & 0xFF);
			ba[j++] = (byte) ((k >>> 8) & 0xFF);
			ba[j++] = (byte) (k & 0xFF);
		}
		return (ba);
	}

	// ......................................................................
	public static int[] byte2int(byte[] ba) {
		int length = ba.length;
		int[] ia = new int[length / 4];
		for (int i = 0, j = 0; j < length / 4;) {
			ia[j++] = (((ba[i++] & 0xFF) << 24) | ((ba[i++] & 0xFF) << 16) | ((ba[i++] & 0xFF) << 8)
					| ((ba[i++] & 0xFF)));
		}
		return (ia);
	}

	// ......................................................................
	// utility methods (adapted from cryptix.util.core.Hex class)

	/** array mapping hex value (0-15) to corresponding hex digit (0-9a-f). */
	public static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	/**
	 * utility method to convert a byte array to a hexadecimal string.
	 * <p>
	 * Each byte of the input array is converted to 2 hex symbols, using the
	 * HEX_DIGITS array for the mapping, with spaces after each pair.
	 * 
	 * @param ba array of bytes to be converted into hex
	 * @return hex representation of byte array
	 */
	public static String toHEX(byte[] ba) {
		int length = ba.length;
		char[] buf = new char[length * 3];
		for (int i = 0, j = 0, k; i < length;) {
			k = ba[i++];
			buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
			buf[j++] = HEX_DIGITS[k & 0x0F];
			buf[j++] = ' ';
		}
		return new String(buf);
	}

	/**
	 * utility method to convert a short array to a hexadecimal string.
	 * <p>
	 * Each word of the input array is converted to 4 hex symbols, using the
	 * HEX_DIGITS array for the mapping, with spaces after every 4.
	 * 
	 * @param ia array of shorts to be converted into hex
	 * @return hex representation of short array
	 */
	public static String toHEX(short[] ia) {
		int length = ia.length;
		char[] buf = new char[length * 5];
		for (int i = 0, j = 0, k; i < length;) {
			k = ia[i++];
			buf[j++] = HEX_DIGITS[(k >>> 12) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 8) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
			buf[j++] = HEX_DIGITS[k & 0x0F];
			buf[j++] = ' ';
		}
		return new String(buf);
	}

	/**
	 * utility method to convert an int array to a hexadecimal string.
	 * <p>
	 * Each word of the input array is converted to 8 hex symbols, using the
	 * HEX_DIGITS array for the mapping, with spaces after every 4.
	 * 
	 * @param ia array of ints to be converted into hex
	 * @return hex representation of int array
	 */
	public static String toHEX(int[] ia) {
		int length = ia.length;
		char[] buf = new char[length * 10];
		for (int i = 0, j = 0, k; i < length;) {
			k = ia[i++];
			buf[j++] = HEX_DIGITS[(k >>> 28) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 24) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 20) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 16) & 0x0F];
			buf[j++] = ' ';
			buf[j++] = HEX_DIGITS[(k >>> 12) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 8) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
			buf[j++] = HEX_DIGITS[k & 0x0F];
			buf[j++] = ' ';
		}
		return new String(buf);
	}

	/**
	 * utility method to convert a byte to a hexadecimal string.
	 * <p>
	 * the byte is converted to 2 hex symbols, using the HEX_DIGITS array for the
	 * mapping.
	 * 
	 * @param b byte to be converted into hex
	 * @return hex representation of byte
	 */
	public static String toHEX1(byte b) {
		char[] buf = new char[2];
		int j = 0;
		buf[j++] = HEX_DIGITS[(b >>> 4) & 0x0F];
		buf[j++] = HEX_DIGITS[b & 0x0F];
		return new String(buf);
	}

	/**
	 * utility method to convert a byte array to a hexadecimal string.
	 * <p>
	 * Each byte of the input array is converted to 2 hex symbols, using the
	 * HEX_DIGITS array for the mapping.
	 * 
	 * @param ba array of bytes to be converted into hex
	 * @return hex representation of byte array
	 */
	public static String toHEX1(byte[] ba) {
		int length = ba.length;
		char[] buf = new char[length * 2];
		for (int i = 0, j = 0, k; i < length;) {
			k = ba[i++];
			buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
			buf[j++] = HEX_DIGITS[k & 0x0F];
		}
		return new String(buf);
	}

	/**
	 * utility method to convert a short array to a hexadecimal string.
	 * <p>
	 * Each word of the input array is converted to 4 hex symbols, using the
	 * HEX_DIGITS array for the mapping.
	 * 
	 * @param ia array of shorts to be converted into hex
	 * @return hex representation of short array
	 */
	public static String toHEX1(short[] ia) {
		int length = ia.length;
		char[] buf = new char[length * 4];
		for (int i = 0, j = 0, k; i < length;) {
			k = ia[i++];
			buf[j++] = HEX_DIGITS[(k >>> 12) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 8) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
			buf[j++] = HEX_DIGITS[k & 0x0F];
		}
		return new String(buf);
	}

	/**
	 * utility method to convert an int to a hexadecimal string.
	 * <p>
	 * the int is converted to 8 hex symbols, using the HEX_DIGITS array for the
	 * mapping.
	 * 
	 * @param i int to be converted into hex
	 * @return hex representation of int
	 */
	public static String toHEX1(int i) {
		char[] buf = new char[8];
		int j = 0;
		buf[j++] = HEX_DIGITS[(i >>> 28) & 0x0F];
		buf[j++] = HEX_DIGITS[(i >>> 24) & 0x0F];
		buf[j++] = HEX_DIGITS[(i >>> 20) & 0x0F];
		buf[j++] = HEX_DIGITS[(i >>> 16) & 0x0F];
		buf[j++] = HEX_DIGITS[(i >>> 12) & 0x0F];
		buf[j++] = HEX_DIGITS[(i >>> 8) & 0x0F];
		buf[j++] = HEX_DIGITS[(i >>> 4) & 0x0F];
		buf[j++] = HEX_DIGITS[i & 0x0F];
		return new String(buf);
	}

	/**
	 * utility method to convert an int array to a hexadecimal string.
	 * <p>
	 * Each word of the input array is converted to 8 hex symbols, using the
	 * HEX_DIGITS array for the mapping.
	 * 
	 * @param ia array of ints to be converted into hex
	 * @return hex representation of int array
	 */
	public static String toHEX1(int[] ia) {
		int length = ia.length;
		char[] buf = new char[length * 8];
		for (int i = 0, j = 0, k; i < length;) {
			k = ia[i++];
			buf[j++] = HEX_DIGITS[(k >>> 28) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 24) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 20) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 16) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 12) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 8) & 0x0F];
			buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
			buf[j++] = HEX_DIGITS[k & 0x0F];
		}
		return new String(buf);
	}

	// ......................................................................
	/**
	 * Returns a byte array from a string of hexadecimal digits.
	 *
	 * @param hex string of hex characters
	 * @return byte array of binary data corresponding to hex string input
	 */
	public static byte[] hex2byte(String hex) {
		int len = hex.length();
		byte[] buf = new byte[((len + 1) / 2)];

		int i = 0, j = 0;
		if ((len % 2) == 1)
			buf[j++] = (byte) hexDigit(hex.charAt(i++));

		while (i < len) {
			buf[j++] = (byte) ((hexDigit(hex.charAt(i++)) << 4) | hexDigit(hex.charAt(i++)));
		}
		return buf;
	}

	// ......................................................................
	/**
	 * Returns true if the string consists ONLY of valid hex characters
	 *
	 * @param hex string of hex characters
	 * @return true if a valid hex string
	 */
	public static boolean isHex(String hex) {
		int len = hex.length();
		int i = 0;
		char ch;

		while (i < len) {
			ch = hex.charAt(i++);
			if (!((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f')))
				return false;
		}
		return true;
	}

	// ......................................................................
	/**
	 * Returns the number from 0 to 15 corresponding to the hex digit <i>ch</i>.
	 *
	 * @param ch hex digit character (must be 0-9A-Fa-f)
	 * @return numeric equivalent of hex digit (0-15)
	 */
	public static int hexDigit(char ch) {
		if (ch >= '0' && ch <= '9')
			return ch - '0';
		if (ch >= 'A' && ch <= 'F')
			return ch - 'A' + 10;
		if (ch >= 'a' && ch <= 'f')
			return ch - 'a' + 10;

		return (0); // any other char is treated as 0
	}

}

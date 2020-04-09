/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.practicapki.controlador;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.security.*;
import java.security.spec.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author robin:
 * https://docs.oracle.com/javase/9/docs/api/java/security/package-summary.html
 */
public class FileShare {

    public static final int BIT_LENGTH = 4;

    public FileShare() {

    }

    public KeyPair crearClaves(String pathBaseClaves) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // Genera las claves privada y publica

        KeyFactory fabricaClaves = KeyFactory.getInstance("RSA");
        Random randomGenerador = new Random();

        // Generar numeros grandes y aleatorios p y q 
        BigInteger p = BigInteger.probablePrime(BIT_LENGTH, randomGenerador);
        BigInteger q = BigInteger.probablePrime(BIT_LENGTH, randomGenerador);
        while (p.equals(q)) {
            p = BigInteger.probablePrime(BIT_LENGTH, randomGenerador);
            q = BigInteger.probablePrime(BIT_LENGTH, randomGenerador);
        }
        // Calculo de n=pxq y 𝜙(n)
        BigInteger n = p.multiply(q);
        BigInteger 𝜙 = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Obtencion de la parte privada e con metodo extendido de euclides
        // sacando el gcd que debe de ser 1 si son primos relativos
        BigInteger e = BigInteger.ONE;
        boolean eEncontrado = false;
        while (!eEncontrado) {
            //e = nextRandomBigInteger(𝜙);
            e = e.add(BigInteger.ONE);
            System.out.println("E "+e);
            if (gcd(e, 𝜙).compareTo(BigInteger.ONE) == 0 && e.compareTo(𝜙) < 0) {
                eEncontrado = true;
            }
        }
        RSAPrivateKeySpec especPriv = new RSAPrivateKeySpec(𝜙, e);

        PrivateKey privateKey = fabricaClaves.generatePrivate(especPriv);

        PublicKey publicKey = fabricaClaves.generatePublic(especPriv);
        KeyPair parClaves = new KeyPair(publicKey, privateKey);
        System.out.println(parClaves);
        // Llamar metodo guardar claves
        return parClaves;
    }

    /**
     * Retorna un BigInteger menor que el valor n fuente:
     * https://stackoverflow.com/questions/2290057/how-to-generate-a-random-biginteger-value-in-java
     *
     * @param n
     * @return a BigInteger value
     */
    public BigInteger nextRandomBigInteger(BigInteger n) {
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while (result.compareTo(n) >= 0) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result;
    }

    /**
     * Calcula el maximo comun divisor de dos valores BigInteger
     *
     * @param a
     * @param b
     * @return a BigInteger value
     */
    private static BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger t;
        while (b.compareTo(BigInteger.ZERO) != 0) {
            t = a;
            a = b;
            b = t.mod(b);
        }
        return a;
    }

    public String cifrarArchivo(String pathArchivoCifrar, String pathClavePublica) {
        // Cifra el archivo y retorna el direccion donde esta el archivo
        String pathCifrado = "";

        return pathCifrado;
    }

    public String descifrarArchivo(String pathSalida, String pathArchivoCifrado, String pathClavePrivada) {
        // Descifra el archivo con la clave privada y retorna la direccion
        String pathDescifrado = pathSalida;

        return pathDescifrado;
    }

    public static void main(String[] args) {
        FileShare fileShare = new FileShare();
        try {
            System.out.println(FileShare.gcd(BigInteger.valueOf(7), BigInteger.valueOf(120)));
            KeyPair parClaves = fileShare.crearClaves("Path");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FileShare.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(FileShare.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

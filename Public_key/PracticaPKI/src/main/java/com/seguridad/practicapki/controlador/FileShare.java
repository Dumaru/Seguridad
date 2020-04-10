/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.practicapki.controlador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.spec.*;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.PKCS8Generator;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPKCS8Generator;
import org.bouncycastle.openssl.jcajce.JceOpenSSLPKCS8EncryptorBuilder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

/**
 *
 * @author robin:
 * https://docs.oracle.com/javase/9/docs/api/java/security/package-summary.html
 */
public class FileShare {

    public static final int BIT_LENGTH = 1024;

    public FileShare() {
        java.security.Security.addProvider(
                new org.bouncycastle.jce.provider.BouncyCastleProvider()
        );
    }

    /**
     * Crea las dos claves privada y publica utilizando RSA de 1024 bits
     *
     * @param pathBaseClaves
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws KeyStoreException
     */
    public String[] crearClaves(String pathBaseClaves, String nombrePriv, String nombrePub) throws NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException, IOException, OperatorCreationException {
        // Genera las claves privada y publica
        KeyPairGenerator generadorClaves = KeyPairGenerator.getInstance("RSA");
        generadorClaves.initialize(BIT_LENGTH);

        // Genera par de claves y los guarda
        KeyPair parClaves = generadorClaves.genKeyPair();
        PrivateKey clavePriv = parClaves.getPrivate();
        PublicKey clavePub = parClaves.getPublic();

        // Crear archivos
        // Llamar metodo guardar claves
        String[] pathClaves = new String[]{
            Path.of(pathBaseClaves, nombrePriv).toString(),
            Path.of(pathBaseClaves, nombrePub).toString()
        };
        guardarClaves(parClaves, pathClaves[0], pathClaves[1]);

        return pathClaves;
    }

    /**
     * Guarda los archivos de clave privada y publica usando la libreria de
     * BouncyCastle
     * https://www.bouncycastle.org/docs/pkixdocs1.5on/org/bouncycastle/openssl/jcajce/JcaPEMWriter.html
     *
     * @param parClaves
     * @param pathBase
     * @throws IOException
     */
    private void guardarClaves(KeyPair parClaves, String nombrePriv, String nombrePub) throws IOException, OperatorCreationException {
        // Crea writter de clave privada 
        // TODO: CODIFICAR CLAVES:
        FileWriter archivoClavePriv = new FileWriter(Path.of(nombrePriv + "_privada.pem").toFile());
        JcaPEMWriter pemWriter = new JcaPEMWriter(archivoClavePriv);
        // Private key in PKCS8 
        //JcaMiscPEMGenerator generator = new JcaMiscPEMGenerator(parClaves.getPrivate());
        //pemWriter.writeObject(generator.generate());

        pemWriter.writeObject(parClaves.getPrivate());

        pemWriter.close();
        // Crea writter de clave publica
        FileWriter archivoClavePub = new FileWriter(Path.of(nombrePub + "_publica.pem").toFile());
        pemWriter = new JcaPEMWriter(archivoClavePub);
        pemWriter.writeObject(parClaves.getPublic());
        pemWriter.close();
    }

    /**
     * Lee un archivo y devuelve el arreglo de bytes de la clave codificada
     *
     * @param pathClave
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Key leerClave(String pathClave) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Key clave = null;
        FileInputStream archivo = new FileInputStream(pathClave);
        PemReader pemReader = new PemReader(new InputStreamReader(archivo));
        PemObject objetoPem = pemReader.readPemObject();
        byte[] pemContent = objetoPem.getContent();
        pemReader.close();
        System.out.println("Objeto pem " + objetoPem.getType() + " " + "\nContenido objeto pem " + Arrays.toString(pemContent));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        if (objetoPem.getType().contains("PRIVATE KEY")) {
            PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(pemContent);
            clave = factory.generatePrivate(encodedKeySpec);
            System.out.println("Clave privada " + Arrays.toString(clave.getEncoded()));
        } else if (objetoPem.getType().contains("PUBLIC KEY")) {
            EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(pemContent);
            clave = factory.generatePublic(encodedKeySpec);
            System.out.println("Clave publica " + Arrays.toString(clave.getEncoded()));
        }
        return clave;
    }

    /**
     * Lee un archivo y retorna la secuencia de bytes de este
     *
     * @param pathArchivo
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    private byte[] leerArchivo(String pathArchivo) throws FileNotFoundException, IOException {
        byte[] archivo = null;
        FileInputStream file = new FileInputStream(pathArchivo);
        archivo = file.readAllBytes();
        return archivo;
    }

    public String cifrarArchivo(String pathCifrado, String pathArchivoCifrar, String pathClavePublica) throws NoSuchAlgorithmException, NoSuchPaddingException {
        // Cifra el archivo y retorna el direccion donde esta el archivo
        Cipher cifradorRsa = Cipher.getInstance("RSA");
        return pathCifrado;
    }

    public String descifrarArchivo(String pathSalida, String pathArchivoCifrado, String pathClavePrivada) {
        // Descifra el archivo con la clave privada y retorna la direccion
        String pathDescifrado = pathSalida;

        return pathDescifrado;
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

    public static void main(String[] args) {

        FileShare fileShare = new FileShare();
        System.out.println(FileShare.gcd(BigInteger.valueOf(7), BigInteger.valueOf(120)));

        try {
            String[] parClaves = fileShare.crearClaves("C:\\claves", "miclave", "miclave");

            try {
                //Key claveRsa = fileShare.leerClave("C:\\claves\\publica.pub");
                Key claveRsa = fileShare.leerClave("C:\\claves\\miclave_publica.pem");
                if (claveRsa instanceof PrivateKey) {
                    PrivateKey claveRsaPrivada = (PrivateKey) claveRsa;
                    System.out.println("Clave rsa privada: " + claveRsaPrivada);
                }else{
                    PublicKey claveRsaPublica = (PublicKey) claveRsa;
                    System.out.println("Clave rsa publica: " + claveRsaPublica);
                }
            } catch (IOException ex) {
                Logger.getLogger(FileShare.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FileShare.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(FileShare.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException ex) {
            Logger.getLogger(FileShare.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileShare.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperatorCreationException ex) {
            Logger.getLogger(FileShare.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

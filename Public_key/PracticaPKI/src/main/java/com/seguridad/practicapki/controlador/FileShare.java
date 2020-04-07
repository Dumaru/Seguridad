/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.practicapki.controlador;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author robin
 */

public class FileShare {
    
    public FileShare() {
        
    }
    
    public List<String> crearClaves(String pathBaseClaves){
        // Genera las claves privada y publica
        String pathClavePrivada = pathBaseClaves;
        String pathClavePublica = pathBaseClaves;
        
        
        List<String> claves = new ArrayList<>();
        claves.add(pathClavePrivada);
        claves.add(pathClavePublica);
        return claves;
    }
    
    public String cifrarArchivo(String pathArchivoCifrar, String pathClavePublica){
        // Cifra el archivo y retorna el direccion donde esta el archivo
        String pathCifrado = "";
        
        return pathCifrado;
    }
    
    public String descifrarArchivo(String pathSalida, String pathArchivoCifrado, String pathClavePrivada){
        // Descifra el archivo con la clave privada y retorna la direccion
        String pathDescifrado = pathSalida;
    
        return pathDescifrado;
    }
    
    
    public static void main(String[] args){
        
    }
    
}

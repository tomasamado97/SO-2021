/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obligatorio1.obligatorio1.Dominio;

import java.util.ArrayList;

public class Carpeta {
    public String nombreDirecto;
    public Permiso permiso;
    public ArrayList<Carpeta> carpetas;
    public ArrayList<Archivo> archivos;
    
    public Carpeta(String nombreDirectorio){
        nombreDirecto = nombreDirectorio;
    }
    
    public void borrarCarpeta(String nombDir){
    }
}
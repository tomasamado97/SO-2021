package com.obligatorio1.obligatorio1.Dominio;

import java.util.ArrayList;

public class Carpeta {
    public String nombreDirecto;
    public Permiso permiso;
    public ArrayList<Carpeta> carpetas;
    public ArrayList<Archivo> archivos;
    public Carpeta carpetaPadre;
    
    public Carpeta(String nombreDirectorio, Carpeta cPadre){
        nombreDirecto = nombreDirectorio;
        carpetaPadre = cPadre;
    }
    
    public void borrarCarpeta(String nombDir){
        for (Carpeta directorio: carpetas){
            if (directorio.nombreDirecto.equals(nombDir)){
                carpetas.remove(directorio);
            }
        } 
    }
}
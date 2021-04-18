/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obligatorio1.obligatorio1.Dominio;

import java.util.ArrayList;

/**
 *
 * @author tomasamado
 */
public class ControladorCarpeta {
    public ArrayList<Carpeta> carpetasRuta;
    public Carpeta directorioActual;
    
    public String pwd(){
        String ruta = "/";
        for (Carpeta carpeta : carpetasRuta){
            ruta = ruta + "/" + carpeta.nombreDirecto;
        };
        return ruta;
    }
    
    public String mkdir(String nombreDir){
        ArrayList<Carpeta> directoriosExistentes = directorioActual.carpetas;
        for (Carpeta directorio: directoriosExistentes){
            if (directorio.nombreDirecto.equals(nombreDir)){
                return "Ya existe un directorio con ese nombre";
            }
        }
        Carpeta nuevoDir = new Carpeta(nombreDir);
        return "Se creo" + nuevoDir.nombreDirecto + "en " + directorioActual.nombreDirecto;
    }
    
    public String rmdir(String nombreDir){
        if (nombreDir.equals("/")){
            return "No se puede borrar la carpeta inicial.";
        }else{
            ArrayList<Carpeta> directoriosExistentes = directorioActual.carpetas;
            for (Carpeta directorio: directoriosExistentes){
                if (directorio.nombreDirecto.equals(nombreDir)){
//                    directorio.borrarCarpeta();
                }
            }
            return "Se borro el directorio";
        }
    }
    
     public String touch(String nombreArchivo) {
       
        if(nombreArchivo.isEmpty()){
            return "No se puede crear un archivo sin nombre";
        }else {
            boolean archivoRepetido = false;
            for(Archivo arch: directorioActual.archivos){
                if (arch.nombreArch.equals(nombreArchivo)){
                    archivoRepetido = true;
                }
            }
            if (archivoRepetido) {
                return "Ya existe un archivo con este nombre, utilice otro.";
            } else {
                Archivo nuevoArchivo = new Archivo();
                directorioActual.archivos.add(nuevoArchivo);
                return "El archivo se agregó con exito";
            }
        }
    }
    
//     public String echo(String texto, String nombreArchivo){
//         return "string ";
//     }
}

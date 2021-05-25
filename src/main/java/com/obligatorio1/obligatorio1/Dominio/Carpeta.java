package com.obligatorio1.obligatorio1.Dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Carpeta {

    public String nombreDirectorio;
    public Permiso permiso;
    public ArrayList<Carpeta> carpetas;
    public ArrayList<Archivo> archivos;
    public Carpeta carpetaPadre;
    public Usuario dueño;
    public String fechaHora;

    public String getNombreDirectorio() {
        return nombreDirectorio;
    }

    public void setNombreDirectorio(String nombreDirectorio) {
        this.nombreDirectorio = nombreDirectorio;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public ArrayList<Carpeta> getCarpetas() {
        return carpetas;
    }

    public void setCarpetas(ArrayList<Carpeta> carpetas) {
        this.carpetas = carpetas;
    }

    public ArrayList<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(ArrayList<Archivo> archivos) {
        this.archivos = archivos;
    }

    public Carpeta getCarpetaPadre() {
        return carpetaPadre;
    }

    public void setCarpetaPadre(Carpeta carpetaPadre) {
        this.carpetaPadre = carpetaPadre;
    }

    public Usuario getDueño() {
        return dueño;
    }

    public void setDueño(Usuario dueño) {
        this.dueño = dueño;
    }

    
    
    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Carpeta(String nombreDirectorio, int permisoDueño, int permisoGrupo, int permisoResto, Carpeta carpetaPadre, Usuario dueño, String fechaHora) {
        this.nombreDirectorio = nombreDirectorio;
        this.permiso = new Permiso(permisoDueño,permisoGrupo,permisoResto);
        this.carpetas = null;
        this.archivos = null;
        this.carpetaPadre = carpetaPadre;
        this.dueño = dueño;
        this.fechaHora = fechaHora;
    }

    public void borrarCarpeta(String nombDir) {
        for (Carpeta directorio : carpetas) {
            if (directorio.nombreDirectorio.equals(nombDir)) {
                carpetas.remove(directorio);
            }
        }
    }
}

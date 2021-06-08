package Dominio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Carpeta {

    public String nombreDirectorio;
    public Permiso permiso;
    public ArrayList<Carpeta> carpetas;
    public ArrayList<Archivo> archivos;
    public Carpeta carpetaPadre;
    public Usuario dueño;
    public String fechaHora;

    public void setDueño(Usuario dueño) {
        this.dueño = dueño;
    }

    public Carpeta(String nombreDirectorio, int permisoDueño, int permisoGrupo, int permisoResto, Carpeta carpetaPadre) {
        this.nombreDirectorio = nombreDirectorio;
        this.permiso = new Permiso(permisoDueño,permisoGrupo,permisoResto);
        this.carpetas = new ArrayList<Carpeta>();
        this.archivos = new ArrayList<Archivo>();
        this.carpetaPadre = carpetaPadre;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuu/M/d h:m:s");
        String fechaHora = now.format(format);
        this.fechaHora = fechaHora;
    }
}

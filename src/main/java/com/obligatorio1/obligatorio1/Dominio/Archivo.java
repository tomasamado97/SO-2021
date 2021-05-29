package com.obligatorio1.obligatorio1.Dominio;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Archivo {
    public ArrayList<String> linea;
    public Permiso permiso;
    public String nombreArch;
    public Usuario dueño;
    public String fechaHora;

    public void setDueño(Usuario dueño) {
        this.dueño = dueño;
    }

    public Archivo(int permisoDueño, int permisoGrupo, int permisoResto, String nombreArch, Usuario dueño) {
        this.linea = null;
        this.permiso = new Permiso(permisoDueño,permisoGrupo,permisoResto);
        this.nombreArch = nombreArch;
        this.dueño = dueño;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss");
        String fechaHora = now.format(format);
        this.fechaHora = fechaHora;
    }
    
}

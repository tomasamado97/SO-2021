package com.obligatorio1.obligatorio1.Dominio;

import java.util.ArrayList;

public class Usuario {
    public String nombreUsuario;
    public String password;
    public boolean esAdmin;
    public ArrayList<String> comandos;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public void agregarComando(String comando) {
        this.comandos.add(comando);
    }
    
    public Usuario (String unNombreUsuario, String unaPassword, boolean unEsAdmin) {
        this.setNombreUsuario(unNombreUsuario);
        this.setPassword(unaPassword);
        this.setEsAdmin(unEsAdmin);
        this.comandos = new ArrayList<String>();
    }
    
    

}


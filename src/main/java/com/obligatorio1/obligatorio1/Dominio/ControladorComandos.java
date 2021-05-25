package com.obligatorio1.obligatorio1.Dominio;

import java.util.ArrayList;

public class ControladorComandos {
    public ArrayList<String> comandos;
    
    public ControladorComandos(){
        comandos = new ArrayList<String>();
    }
    
    public void agregarComando(String comando) {
        comandos.add(comando);
    }
    
    public void imprimirComandos() {
        for (String command : comandos) {
            System.out.println(command);
        }
    }
    
}

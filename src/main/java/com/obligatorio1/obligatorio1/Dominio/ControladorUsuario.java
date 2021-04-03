/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obligatorio1.obligatorio1.Dominio;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author tomasamado
 */
public class ControladorUsuario {
    public Usuario[] usuarios;


    public String userdel(String nombreUser){
        if (usuarios.length == 0){
            return "El usuario a borrar no existe";
        }else{
            usuarios = Arrays.stream(usuarios).filter(user -> user.nombreUsuario != nombreUser).toArray(Usuario[]::new);
            return "El usuario se borro con exito";
        }
    }
}

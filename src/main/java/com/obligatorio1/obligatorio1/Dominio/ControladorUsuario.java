/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obligatorio1.obligatorio1.Dominio;
import java.util.ArrayList;

public class ControladorUsuario {
    public ArrayList<Usuario> usuarios;
    public Usuario usuarioActual;


    public String userdel(String nombreUser){
        boolean seBorroUsuario = usuarios.removeIf(user -> (user.nombreUsuario == nombreUser));
        if (seBorroUsuario) {
            return "El usuario se borro con exito";
        }else{
            return "El usuario a borrar no existe";
        }
    }
    
    public String useradd(String nombreUser){
        if (nombreUser.isEmpty()){
            return "No se puede crear un usuario sin nombre";
        }else{
            Usuario nuevoUser = new Usuario();
            usuarios.add(nuevoUser);
            return "El usuario se agrego con exito";
        }
    }
}

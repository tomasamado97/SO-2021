/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.obligatorio1.obligatorio1.Dominio;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ControladorUsuario {
    public ArrayList<Usuario> usuarios;
    public Usuario usuarioActual;


    public String userdel(String nombreUser){
        boolean seBorroUsuario = usuarios.removeIf(user -> (user.nombreUsuario.equals(nombreUser)));
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
    
    public String su(String nombreUser, String password){
        if (nombreUser.isEmpty()){
            return "El nombre del usuario no puede ser vacio.";
        }else{
            boolean isAuthenticated = false;
            Usuario userToAuth = null;
            for (Usuario user: usuarios){
                if (user.nombreUsuario.equals(nombreUser) & user.password.equals(password)){
                    isAuthenticated = true;
                    userToAuth = user;
                }
            }
            
            if (isAuthenticated){
                usuarioActual = userToAuth;
                return "Te has autenticado con exito";
            }else{
                return "El username y la password no son correctos";
            }   
        }
    }
    
       public String passwd(String password1, String password2){ //si no funciona: crear un nuevo usuario(mismo nombre dif passwrd) y reemplazamos con set.
            if (password1.isEmpty() || password2.isEmpty()){
                return "No se puede crear una contraseña ";
            } else {
                if (password1.equals(password2)){
                    Usuario aCambiar = null;
                    for (Usuario user : usuarios){
                       if (user.nombreUsuario.equals(usuarioActual.nombreUsuario) && user.password.equals(usuarioActual.password)) {
                           aCambiar = user;
                           user.setPassword(password1);
                       }
                    }
                    aCambiar.setPassword(password1);
                    return "Se cambió la contraseña con éxito";
                } else {
                    return "Las contraseñas no coinciden";
                }
            }
       }
       
       public String whoami(String nombreUser){
        return nombreUser;
    }
   
}

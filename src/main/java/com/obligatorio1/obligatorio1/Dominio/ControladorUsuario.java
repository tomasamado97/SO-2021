package com.obligatorio1.obligatorio1.Dominio;
import java.util.ArrayList;
import java.util.Scanner;

public class ControladorUsuario {
    public ArrayList<Usuario> usuarios;
    public Usuario usuarioActual;

     public ControladorUsuario(){
         usuarios = null;
         usuarioActual = null;
     }

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
    
    public String su(String nombreUser){
        if (nombreUser.isEmpty()){
            return "El nombre del usuario no puede ser vacio.";
        }else{
            Scanner in = new Scanner(System.in);
            System.out.println("Ingrese la contraseña");
            String password = in.nextLine();
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
    
       public String passwd(String nombreUser){
           System.out.println("Ingrese la contraseña");
           Scanner in = new Scanner(System.in);
           String password1 = in.nextLine();
           System.out.println("Ingrese la contraseña nuevamente");
           String password2 = in.nextLine();
           if (password1.isEmpty() || password2.isEmpty()){
               return "No se puede crear una contraseña";
           }else {
               if (password1.equals(password2)){
                   Usuario aCambiar = null;
                   for (Usuario user: usuarios){
                       if (user.nombreUsuario.equals(nombreUser)){
                           aCambiar = user;
                       }
                   }
                   if (aCambiar != null){
                       aCambiar.setPassword(password1);
                   }
                   return "Se cambio la contraseña correctamente";
               }else{
                   return "Las contraseñas no coinciden";
               }
           }
       }
       
       public String whoami(){
        return usuarioActual.nombreUsuario;
    }
   
}

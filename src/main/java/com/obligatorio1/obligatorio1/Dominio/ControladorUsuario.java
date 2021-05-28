package com.obligatorio1.obligatorio1.Dominio;

import java.util.ArrayList;
import java.util.Scanner;

public class ControladorUsuario {

    public ArrayList<Usuario> usuarios;
    public Usuario usuarioActual;

    public ControladorUsuario() {
        usuarios = new ArrayList<Usuario>();
        usuarioActual = new Usuario("visita", "1234", false);
        usuarios.add(usuarioActual);
        Usuario root = new Usuario("root", "root", true);
        usuarios.add(root);
    }

    public String userdel(String nombreUser) {
        if (usuarioActual.esAdmin) {
            boolean seBorroUsuario = usuarios.removeIf(user -> (user.nombreUsuario.equals(nombreUser)));
            if (seBorroUsuario) {
                return "El usuario se borro con exito";
            } else {
                return "El usuario a borrar no existe";
            }
        } else {
            return "El usuario actual no cuenta con los privilegios para realizar esta acción";
        }
    }

    public String useradd(String nombreUser) {
        if (usuarioActual.esAdmin) {
            if (nombreUser.isEmpty()) {
                return "No se puede crear un usuario sin nombre";
            } else {
                Scanner in = new Scanner(System.in);
                System.out.println("Ingrese la contraseña:");
                String password = in.nextLine();
                Usuario nuevoUser = new Usuario(nombreUser, password, false);
                usuarios.add(nuevoUser);
                return "El usuario se agrego con exito";
            }
        } else {
            return "El usuario actual no cuenta con los privilegios para realizar esta acción";
        }
    }

    public String su(String nombreUser) {
        if (nombreUser.isEmpty()) {
            return "El nombre del usuario no puede ser vacio.";
        } else {
            Scanner in = new Scanner(System.in);
            System.out.println("Ingrese la contraseña");
            String password = in.nextLine();
            boolean isAuthenticated = false;
            for (Usuario user : usuarios) {
                if (user.nombreUsuario.equals(nombreUser)) {
                    isAuthenticated = true;
                    if (user.password.equals(password)){
                        return "Te has autenticado con exito";
                    }else{
                        return "El username y la password no son correctos";
                    }
                }
            }
            return "No se encontro el usuario con nombre" + nombreUser;
        }
    }

    public String passwd(String nombreUser) {
        if (usuarioActual.esAdmin || usuarioActual.nombreUsuario.equals(nombreUser)) {
            System.out.println("Ingrese la contraseña");
            Scanner in = new Scanner(System.in);
            String password1 = in.nextLine();
            System.out.println("Ingrese la contraseña nuevamente");
            String password2 = in.nextLine();
            if (password1.isEmpty() || password2.isEmpty()) {
                return "No se puede crear una contraseña";
            } else {
                if (password1.equals(password2)) {
                    Usuario aCambiar = null;
                    for (Usuario user : usuarios) {
                        if (user.nombreUsuario.equals(nombreUser)) {
                            aCambiar = user;
                        }
                    }
                    if (aCambiar != null) {
                        aCambiar.setPassword(password1);
                    }
                    return "Se cambio la contraseña correctamente";
                } else {
                    return "Las contraseñas no coinciden";
                }
            }
        } else {
            return "El usuario actual no cuenta con los privilegios para realizar esta acción";
        }
    }

    public String whoami() {
        return usuarioActual.nombreUsuario;
    }
    
    public String history() {
        String history = "";
        for (String comando : usuarioActual.comandos) {
            history = comando + "\n";
        }
        return history;
    } 

}

package com.obligatorio1.obligatorio1.Dominio;

import java.util.ArrayList;
import java.util.Scanner;

public class ControladorUsuario {

    public ArrayList<Usuario> usuarios;
    public Usuario usuarioActual;

    public ControladorUsuario() {
        usuarios = null;
        usuarioActual = new Usuario("visita", "1234", false);
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
                System.out.println("¿Este usuario será admin (responda sólo con si o no)?");
                boolean esAdmin;
                switch (in.nextLine()) {
                    case "si":
                        boolean existeAdmin = false;
                        for (Usuario user : usuarios) {
                            if (user.esAdmin == true) {
                                existeAdmin = true;
                            }
                        }
                        if (!existeAdmin) {
                            esAdmin = true;
                        } else {
                            return "Ya existe un usuario admin";
                        }
                        break;
                    case "no":
                        esAdmin = false;
                        break;
                    default:
                        return "Respuesta no válida";
                }
                Usuario nuevoUser = new Usuario(nombreUser, password, esAdmin);
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
            Usuario userToAuth = null;
            for (Usuario user : usuarios) {
                if (user.nombreUsuario.equals(nombreUser) & user.password.equals(password)) {
                    isAuthenticated = true;
                    userToAuth = user;
                }
            }
            if (isAuthenticated) {
                usuarioActual = userToAuth;
                return "Te has autenticado con exito";
            } else {
                return "El username y la password no son correctos";
            }
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

}

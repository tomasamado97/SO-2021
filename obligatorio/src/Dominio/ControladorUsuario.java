package Dominio;

import java.util.ArrayList;
import java.util.Scanner;

public class ControladorUsuario {

    public ArrayList<Usuario> usuarios;
    public Usuario usuarioActual;

    public ControladorUsuario() {
        usuarios = new ArrayList<Usuario>();
        usuarioActual = new Usuario("visita", "1234", false); // usuario inicial autenticado, no privilegiado
        usuarios.add(usuarioActual);
        Usuario root = new Usuario("root", "root", true); // usuario inicial no autenticado, privilegiado
        usuarios.add(root);
    }

    public Usuario getUserByName(String nombreUser) {
        if (usuarios != null) {
            for (Usuario user : usuarios) {
                if (user.nombreUsuario.equals(nombreUser)) {
                    return user;
                }
            }
            return null;
        }
        return null;
    }

    public String userdel(String nombreUsuario) {
        if (usuarioActual.esAdmin) {
            if (!nombreUsuario.equals(usuarioActual.nombreUsuario)) {
                boolean seBorroUsuario = usuarios.removeIf(user -> (user.nombreUsuario.equals(nombreUsuario)));
                if (seBorroUsuario) {
                    return "El usuario se borró con éxito";
                } else {
                    return "error: El usuario a borrar no existe";
                }
            }
            return "error: No se puede borrar el usuario admin";
        }
        return "error: El usuario actual no cuenta con los privilegios para realizar esta acción";
    }

    public String useradd(String nombreUsuario) {
        if (usuarioActual.esAdmin) {
            for (Usuario user : usuarios) {
                if (nombreUsuario.equals(user.nombreUsuario)) {
                    return "error: Ya existe un usuario con ese nombre";
                }
            }
            Scanner in = new Scanner(System.in);
            System.out.println("Ingrese la contraseña:");
            String password = in.nextLine();
            Usuario nuevoUser = new Usuario(nombreUsuario, password, false);
            usuarios.add(nuevoUser);
            return "El usuario " + nombreUsuario + " se agregó con éxito";
        }
        return "error: El usuario actual no cuenta con los privilegios para realizar esta acción";
    }

    public String su(String nombreUsuario) {
        for (Usuario user : usuarios) {
            if (user.nombreUsuario.equals(nombreUsuario)) {
                Scanner in = new Scanner(System.in);
                System.out.println("Ingrese la contraseña");
                String password = in.nextLine();
                if (user.password.equals(password)) {
                    usuarioActual = user;
                    return nombreUsuario + " se autenticó con éxito";
                }
                return "error: El usuario y la contraseña no son correctos";
            }
        }
        return "error: No se encontró el usuario";
    }

    public String passwd(String nombreUsuario) {
        if (usuarioActual.esAdmin || usuarioActual.nombreUsuario.equals(nombreUsuario)) {
            System.out.println("Ingrese la contraseña");
            Scanner in = new Scanner(System.in);
            String password1 = in.nextLine();
            System.out.println("Ingrese la contraseña nuevamente");
            String password2 = in.nextLine();
            if (password1.isEmpty() || password2.isEmpty()) {
                return "error: No se puede crear una contraseña vacía";
            }
            if (password1.equals(password2)) {
                for (Usuario user : usuarios) {
                    if (user.nombreUsuario.equals(nombreUsuario)) {
                        user.setPassword(password1);
                        return "Se cambió la contraseña de " + nombreUsuario + " correctamente";
                    }
                }
                return "error: El usuario indicado no existe";
            }
            return "error: Las contraseñas no coinciden";
        }
        return "error: El usuario actual no cuenta con los privilegios para realizar esta acción";
    }

    public String whoami() {
        return usuarioActual.nombreUsuario;
    }

    public String history() {
        String history = "";
        for(int i = 0; i <= usuarioActual.comandos.size()-1; i++) {
            history = history + i + " " + usuarioActual.comandos.get(i) + "\n";
        }
        return history;
    }

    public String historyGrep(String palabraABuscar) {
        for(int i = 0; i <= usuarioActual.comandos.size()-1; i++) {
            if (usuarioActual.comandos.get(i).contains(palabraABuscar)) {
                return i + " " + usuarioActual.comandos.get(i);
            }
        }
        return "error: No se encontró la palabra en ninguno de los comandos ingresados";
    }

}

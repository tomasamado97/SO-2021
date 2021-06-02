package Dominio;

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
    
    public Usuario getUserByName(String nombreUser){
        if (usuarios != null){
            for (Usuario user: usuarios){
                if (user.nombreUsuario.equals(nombreUser)){
                    return user;
                }
            }
            return null;
        }
        return null;
    }

    public String userdel(String nombreUsuario) {
        if (usuarioActual.esAdmin) {
            boolean seBorroUsuario = usuarios.removeIf(user -> (user.nombreUsuario.equals(nombreUsuario)));
            if (seBorroUsuario) {
                return "El usuario se borro con exito";
            } else {
                return "El usuario a borrar no existe";
            }
        }
        return "El usuario actual no cuenta con los privilegios para realizar esta acción";
    }

    public String useradd(String nombreUsuario) {
        if (usuarioActual.esAdmin) {
            for (Usuario user : usuarios) {
                if (nombreUsuario.equals(user.nombreUsuario)) {
                    return "Ya existe un usuario con ese nombre";
                }
            }
            Scanner in = new Scanner(System.in);
            System.out.println("Ingrese la contraseña:");
            String password = in.nextLine();
            Usuario nuevoUser = new Usuario(nombreUsuario, password, false);
            usuarios.add(nuevoUser);
            return "El usuario se agrego con exito";
        }
        return "El usuario actual no cuenta con los privilegios para realizar esta acción";
    }

    public String su(String nombreUsuario) {
        for (Usuario user : usuarios) {
            if (user.nombreUsuario.equals(nombreUsuario)) {
                Scanner in = new Scanner(System.in);
                System.out.println("Ingrese la contraseña");
                String password = in.nextLine();
                if (user.password.equals(password)) {
                    usuarioActual = user;
                    return "Te has autenticado con exito";
                }
                return "El username y la password no son correctos";
            }
        }
        return "No se encontro el usuario con nombre" + nombreUsuario;
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
            }
            if (password1.equals(password2)) {
                for (Usuario user : usuarios) {
                    if (user.nombreUsuario.equals(nombreUser)) {
                        user.setPassword(password1);
                        return "Se cambió la contraseña correctamente";
                    }
                }
                return "El usuario indicado no existe";
            }
            return "Las contraseñas no coinciden";
        }
        return "El usuario actual no cuenta con los privilegios para realizar esta acción";
    }

    public String whoami() {
        return usuarioActual.nombreUsuario;
    }
    
    public String history() {
        String history = "";
        for (String comando : usuarioActual.comandos) {
            history = history + comando + "\n";
        }
        return history;
    } 
    
    public String historyGrep(String palabraABuscar) {
        for (String comando : usuarioActual.comandos) {
            if (comando.contains(palabraABuscar)) {
                return comando;
            }
        }
        return "No se encontró la palabra en ninguno de los comandos ingresados";
    }

}

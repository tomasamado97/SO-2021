package Dominio;

import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        ControladorUsuario CU = new ControladorUsuario();
        ControladorCarpeta CA = new ControladorCarpeta(CU.getUserByName("root"));
        System.out.println("Para salir, escriba el comando 'exit'");
        boolean exit = false;
        while (!exit) {
            System.out.print(CU.usuarioActual.nombreUsuario + "@$_" + CA.pwd() + "$ ");
            Scanner in = new Scanner(System.in);
            String comandoHistory = in.nextLine();
            String[] comando = comandoHistory.split(" ");
            int largo = comando.length;
            CU.usuarioActual.agregarComando(comandoHistory);
            switch (largo) {
                case 0:
                    System.out.println("");
                    break;
                case 1:
                    switch (comando[0]) {
                        case "whoami":
                            System.out.println(CU.whoami());
                            break;
                        case "pwd":
                            System.out.println(CA.pwd());
                            break;
                        case "history":
                            System.out.println(CU.history());
                            break;
                        case "exit":
                            exit = true;
                            System.out.println("Consola cerrada");
                            break;
                        default:
                            System.out.println("Comando no válido");
                            break;
                    }
                    break;
                case 2:
                    switch (comando[0]) {
                        case "useradd":
                            System.out.println(CU.useradd(comando[1]));
                            break;
                        case "passwd":
                            System.out.println(CU.passwd(comando[1]));
                            break;
                        case "su":
                            System.out.println(CU.su(comando[1]));
                            break;
                        case "userdel":
                            System.out.println(CU.userdel(comando[1]));
                            break;
                        case "mkdir":
                            System.out.println(CA.mkdir(comando[1], CU.usuarioActual));
                            break;
                        case "rmdir":
                            System.out.println(CA.rmdir(comando[1], CU.usuarioActual));
                            break;
                        case "touch":
                            System.out.println(CA.touch(comando[1], CU.usuarioActual));
                            break;
                        case "cat":
                            System.out.println(CA.cat(comando[1], CU.usuarioActual));
                            break;
                        case "rm":
                            System.out.println(CA.rm(comando[1], CU.usuarioActual));
                            break;
                        case "cd":
                            System.out.println(CA.cd(comando[1], CU.usuarioActual));
                            break;
                        case "ls":
                            if (comando[1].equals("-l")) {
                                System.out.println(CA.ls());
                            } else {
                                System.out.println("Comando no válido");
                            }
                            break;
                        default:
                            System.out.println("Comando no válido");
                            break;
                    }
                    break;
                case 3:
                    switch (comando[0]) {
                        case "mv":
                            System.out.println(CA.mv(comando[1], comando[2], CU.usuarioActual));
                            break;
                        case "cp":
                            System.out.println(CA.cp(comando[1], comando[2], CU.usuarioActual));
                            break;
                        case "chmod":
                            System.out.println(CA.chmod(comando[1], comando[2], CU.usuarioActual));
                            break;
                        case "chown":
                            System.out.println(CA.chown(comando[1], comando[2], CU.usuarios, CU.usuarioActual));
                            break;
                        default:
                            System.out.println("Comando no válido");
                            break;
                    }
                    break;
                case 4:
                    switch (comando[0]) {
                        case "echo":
                            if (comando[3].equals(">>")) {
                                System.out.println("Comando no válido");
                            } else {
                                System.out.println(CA.echo(comando[1], comando[3], CU.usuarioActual));
                            }
                            break;
                        case "history":
                            System.out.println(CU.historyGrep(comando[3]));
                            break;
                        default:
                            System.out.println("Comando no válido");
                            break;
                    }
                    break;
                case 5:
                    switch (comando[0]) {
                        case "cat":
                            System.out.println(CA.catGrep(comando[1], comando[4], CU.usuarioActual));
                            break;
                        case "ls":
                            System.out.println(CA.lsGrep(comando[4]));
                            break;
                        case "echo":
                            if (comando[4].equals(">>")) {
                                System.out.println("Comando no válido");
                            } else {
                                System.out.println(CA.echo(comando[1] + " " + comando[2], comando[4], CU.usuarioActual));
                            }
                            break;
                        default:
                            System.out.println("Comando no válido");
                            break;
                    }
                    break;
                default:
                    if (comando[0].equals("echo") && !comando[comando.length - 1].equals(">>") && comando[comando.length - 2].equals(">>")) {
                        String texto = "";
                        for (int i = 1; i < comando.length - 2; i++) {
                            texto = texto + " " + comando[i];
                        }
                        System.out.println(CA.echo(texto, comando[comando.length - 1], CU.usuarioActual));
                    } else {
                        System.out.println("Comando no válido");
                    }
                    break;
            }
        }
    }
}

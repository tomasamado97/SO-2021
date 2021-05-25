package com.obligatorio1.obligatorio1.Dominio;

import java.util.Scanner;

public class main {

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        ControladorUsuario CU = new ControladorUsuario();
        ControladorCarpeta CA = new ControladorCarpeta();
        ControladorComandos CC = new ControladorComandos();
        System.out.println("Para salir, escriba el comando 'exit'");
        boolean exit = false;
        while (!exit) {
            System.out.print(CU.usuarioActual.nombreUsuario + "@#_");
            Scanner in = new Scanner(System.in);
            String[] comando = in.nextLine().split(" ");
            int largo = comando.length;
            switch (largo)
            {
                case 0:
                    System.out.println("");
                case 1:
                    switch(comando[0])
                    {
                        case "whoami":
                            System.out.println(CU.whoami());
                        case "pwd":
                            System.out.println(CA.pwd());
                        case "history":
                            /*System.out.println(CC.history())*/;
                        case "exit":
                            exit = true;
                            System.out.println("Consola cerrada");
                        default:
                            System.out.println("Comando no válido");
                    }
                case 2:
                    switch(comando[0])
                    {
                        case "useradd":
                            System.out.println(CU.useradd(comando[1]));
                        case "passwd":
                            System.out.println(CU.passwd(comando[1]));
                        case "su":
                            System.out.println(CU.su(comando[1]));
                        case "userdel":
                            System.out.println(CU.userdel(comando[1]));
                        case "mkdir":
                            System.out.println(CA.mkdir(comando[1], CU.usuarioActual));
                        case "rmdir":
                            System.out.println(CA.rmdir(comando[1]));
                        case "touch":
                            System.out.println(CA.touch(comando[1]));
                        case "cat":
                            System.out.println(CA.cat(comando[1]));
                        case "rm":
                            System.out.println(CA.rm(comando[1]));
                        case "cd":
                            /*System.out.println(CA.cd(comando[1]))*/;
                        case "ls":
                            if (comando[1].equals("-l")) {
                                /*System.out.println(CA.ls())*/;
                            } else {
                                System.out.println("Comando no válido");
                            }
                        default:
                            System.out.println("Comando no válido");      
                    }
                case 3:
                    switch (comando[0])
                    {
                        case "mv":
                            /*System.out.println(CA.mv(comando[1],comando[2]))*/;
                        case "cp":
                            /*System.out.println(CA.cp(comando[1],comando[2]))*/;
                        case "chmod":
                            /*System.out.println(CA.chmod(comando[1],comando[2]))*/;
                        case "chown":
                            /*System.out.println(CA.chown(comando[1],comando[2]))*/;
                        default:
                            System.out.println("Comando no válido");
                    }
                case 4:
                    switch (comando[0])
                    {
                        case "echo":
                            System.out.println(CA.echo(comando[1], comando[3]));
                        case "history":
                            /*System.out.println(CA.grepHistory(comando[3]))*/;
                        default:
                            System.out.println("Comando no válido");
                    }
                case 5:
                    if (comando[0].equals("cat")) {
                        /*System.out.println(CA.grepCat(comando[1],comando[4]))*/;
                    } else {
                        System.out.println("Comando no válido");
                    }       
            }
        }
    }
}

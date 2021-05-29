package com.obligatorio1.obligatorio1.Dominio;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ControladorCarpeta {

    public ArrayList<Carpeta> carpetasRuta;
    public Carpeta directorioActual;

    public ControladorCarpeta() {
        carpetasRuta = new ArrayList<Carpeta>();
        directorioActual = new Carpeta("Inicio", 7, 7, 7, null);
        carpetasRuta.add(directorioActual);
    }

    public String pwd() {
        String ruta = "/";
        for (Carpeta carpeta : carpetasRuta) {
            ruta = ruta + carpeta.nombreDirectorio + "/";
        }
        return ruta;
    }

    public String mkdir(String nombreDir, Usuario usuarioActual) {
        if (directorioActual.carpetas != null) {
            for (Carpeta directorio : directorioActual.carpetas) {
                if (directorio.nombreDirectorio.equals(nombreDir)) {
                    return "Ya existe un directorio con ese nombre";
                }
            }
            if ((usuarioActual.nombreUsuario.equals(directorioActual.dueño.nombreUsuario) && directorioActual.permiso.permisoDueño != 7) || (directorioActual.permiso.permisoResto != 7)) {
                return "No se tiene permiso para realizar esta acción";
            }
            Carpeta nuevoDir = new Carpeta(nombreDir, 7, 7, 5, directorioActual);
            nuevoDir.setDueño(usuarioActual);
            directorioActual.carpetas.add(nuevoDir);
            return "Se creo" + nuevoDir.nombreDirectorio + "en " + directorioActual.nombreDirectorio;
        }
        return "No se pudo crear el directorio";
    }

    public String rmdir(String nombreDir, Usuario usuarioActual) {
        if (nombreDir.equals("/")) {
            return "No se puede borrar la carpeta inicial.";
        } else {
            if (directorioActual.carpetas != null) {
                for (Carpeta directorio : directorioActual.carpetas) {
                    if (directorio.nombreDirectorio.equals(nombreDir)) {
                        if ((usuarioActual.nombreUsuario.equals(directorioActual.dueño.nombreUsuario) && directorioActual.permiso.permisoDueño != 7) || (directorioActual.permiso.permisoResto != 7)) {
                            return "No se tiene permiso para realizar esta acción";
                        }
                        directorio.borrarCarpeta(directorio.nombreDirectorio);
                    }
                }
                return "Se borro el directorio";
            }
            return "No se pudo borrar el directorio";
        }
    }

    public String touch(String nombreArchivo, Usuario usuarioActual) {
        if (nombreArchivo.isEmpty()) {
            return "No se puede crear un archivo sin nombre";
        } else {
            boolean archivoRepetido = false;
            if (directorioActual.archivos != null) {
                for (Archivo arch : directorioActual.archivos) {
                    if (arch.nombreArch.equals(nombreArchivo)) {
                        archivoRepetido = true;
                    }
                }
                if (archivoRepetido) {
                    return "Ya existe un archivo con este nombre, utilice otro.";
                } else {
                    if ((usuarioActual.nombreUsuario.equals(directorioActual.dueño.nombreUsuario) && directorioActual.permiso.permisoDueño != 7) || (directorioActual.permiso.permisoResto != 7)) {
                        return "No se tiene permiso para realizar esta acción";
                    }
                    Archivo nuevoArchivo = new Archivo(7, 7, 5, nombreArchivo, usuarioActual);
                    directorioActual.archivos.add(nuevoArchivo);
                    return "El archivo se agregó con exito";
                }
            }
            return "No se pudo crear el archivo";
        }
    }

    public String echo(String texto, String nombreArchivo, Usuario usuarioActual) {
        if (nombreArchivo.isEmpty()) {
            return "No se puede agregar el texto un archivo sin nombre";
        } else {
            for (Archivo arch : directorioActual.archivos) {
                if (arch.nombreArch.equals(nombreArchivo)) {
                    if ((usuarioActual.nombreUsuario.equals(directorioActual.dueño.nombreUsuario)
                            && directorioActual.permiso.permisoDueño != 2
                            || directorioActual.permiso.permisoDueño != 3
                            || directorioActual.permiso.permisoDueño != 6
                            || directorioActual.permiso.permisoDueño != 7) || (directorioActual.permiso.permisoResto != 2
                            || directorioActual.permiso.permisoResto != 3
                            || directorioActual.permiso.permisoResto != 6
                            || directorioActual.permiso.permisoResto != 7)) {
                        return "No se tiene permiso para realizar esta acción";
                    }
                    arch.linea.add(texto);
                }
            }
            return "Se agrego el texto al archivo";
        }
    }

    public Carpeta findDirectory(String ruta, Carpeta carpPadre) {
        for (Carpeta nodo : carpPadre.carpetas) {
            if (nodo.nombreDirectorio.equals(ruta)) {
                return nodo;
            }
        }
        return null;
    }

    public String mv(String origen, String destino) {
        if (origen.isEmpty() || destino.isEmpty()) {
            return "No es posible mover un archivo sin ruta de origen o destino";
        } else {
            Carpeta carpOrigen = null;
            Carpeta aux = null;
            Carpeta carpDestino = null;
            Carpeta aux2 = null;
            String[] rutasOrigen = origen.split("/");
            String[] rutasDestino = destino.split("/");
            Boolean esRutaInicialDestino = false;
            Boolean esRutaInicialOrigen = false;

            // Encuentra la carpeta/archivo de Destino
            if (carpetasRuta.get(0).nombreDirectorio.equals(rutasDestino[0])) {
                carpDestino = carpetasRuta.get(0);
                esRutaInicialDestino = true;
            } else {
                carpDestino = directorioActual;
            }
            for (String ruta : rutasDestino) {
                if (!esRutaInicialDestino) {
                    aux2 = carpDestino;
                    carpDestino = findDirectory(ruta, carpDestino);
                }
                esRutaInicialDestino = false;
                if (carpDestino == null) {
                    return "La ruta de destino especificada no es correcta";
                }
            }

            // Encuentra la carpeta/archivo de Origen
            if (carpetasRuta.get(0).nombreDirectorio.equals(rutasOrigen[0])) {
                carpOrigen = carpetasRuta.get(0);
                esRutaInicialOrigen = true;
            } else {
                carpOrigen = directorioActual;
            }
            for (String ruta : rutasOrigen) {
                if (!esRutaInicialOrigen) {
                    aux = carpOrigen;
                    carpOrigen = findDirectory(ruta, carpOrigen);
                }
                esRutaInicialOrigen = false;
                if (carpOrigen == null) {
                    return "La ruta de origen especificada no es correcta";
                }
            }

            // Chequea que sea carpeta o archivo y que existan
            if (carpOrigen == null) {
                Boolean esArchivoDestino = false;
                Boolean esArchivoOrigen = false;
                Archivo archvOrigen = null;
                Archivo archvDestino = null;
                for (Archivo arch : aux.archivos) {
                    if (rutasOrigen[rutasOrigen.length - 1].equals(arch.nombreArch)) {
                        esArchivoOrigen = true;
                        archvOrigen = arch;
                    }
                }
                for (Archivo arch : aux2.archivos) {
                    if (rutasDestino[rutasDestino.length - 1].equals(arch.nombreArch)) {
                        esArchivoDestino = true;
                        archvDestino = arch;
                    }
                }
                if (esArchivoOrigen) {
                    if (esArchivoDestino) {
                        // ya existe un archivo con ese nombre en el destino
                        return "No se puede renombrar el archivo de origen debido a que en la ruta de destino ya existe un archivo con ese nombre";
                    } else {
                        if (carpDestino != null) {
                            // muevo el archivo de origen a destino
                            carpDestino.archivos.add(archvOrigen);
                            aux.archivos.remove(archvOrigen);
                            return "Se movio el archivo de origen a destino";
                        } else {
                            archvOrigen.nombreArch = destino;
                            return "Se renombro el archivo de origen por " + destino;
                        }
                    }
                } else {
                    return "La ruta de origen no existe o es incorrecta";
                }
            } else {
                if (carpDestino != null) {
                    // agrego la carpeta al destino
                    carpDestino.carpetas.add(carpOrigen);
                    // Borro la carpeta del origen
                    carpOrigen.carpetaPadre.carpetas.remove(carpOrigen);
                    return "Se movio la carpeta de origen a destino";
                } else {
                    // renombramos la carpeta origen por el nombre de destino
                    carpOrigen.nombreDirectorio = destino;
                    return "Se renombro la ruta de origen por " + destino;
                }
            }
        }
    }

    ;
      
       public String cp(String origen, String destino) {
        if (origen.isEmpty() || destino.isEmpty()) {
            return "No es posible mover un archivo sin ruta de origen o destino";
        } else {
            Carpeta carpOrigen = null;
            Carpeta aux = null;
            Carpeta carpDestino = null;
            Carpeta aux2 = null;
            String[] rutasOrigen = origen.split("/");
            String[] rutasDestino = destino.split("/");
            Boolean esRutaInicialDestino = false;
            Boolean esRutaInicialOrigen = false;

            // Encuentra la carpeta/archivo de Destino
            if (carpetasRuta.get(0).nombreDirectorio.equals(rutasDestino[0])) {
                carpDestino = carpetasRuta.get(0);
                esRutaInicialDestino = true;
            } else {
                carpDestino = directorioActual;
            }
            for (String ruta : rutasDestino) {
                if (!esRutaInicialDestino) {
                    aux2 = carpDestino;
                    carpDestino = findDirectory(ruta, carpDestino);
                }
                esRutaInicialDestino = false;
            }

            // Encuentra la carpeta/archivo de Origen
            if (carpetasRuta.get(0).nombreDirectorio.equals(rutasOrigen[0])) {
                carpOrigen = carpetasRuta.get(0);
                esRutaInicialOrigen = true;
            } else {
                carpOrigen = directorioActual;
            }
            for (String ruta : rutasOrigen) {
                if (!esRutaInicialOrigen) {
                    aux = carpOrigen;
                    carpOrigen = findDirectory(ruta, carpOrigen);
                }
                esRutaInicialOrigen = false;
            }

            // Chequea que sea carpeta o archivo y que existan
            if (carpOrigen == null) {
                Boolean esArchivoDestino = false;
                Boolean esArchivoOrigen = false;
                Archivo archvOrigen = null;
                Archivo archvDestino = null;
                for (Archivo arch : aux.archivos) {
                    if (rutasOrigen[rutasOrigen.length - 1].equals(arch.nombreArch)) {
                        esArchivoOrigen = true;
                        archvOrigen = arch;
                    }
                }
                for (Archivo arch : aux2.archivos) {
                    if (rutasDestino[rutasDestino.length - 1].equals(arch.nombreArch)) {
                        esArchivoDestino = true;
                        archvDestino = arch;
                    }
                }
                if (esArchivoOrigen) {
                    if (esArchivoDestino) {
                        // ya existe un archivo con ese nombre en el destino
                        return "No se puede renombrar el archivo de origen debido a que en la ruta de destino ya existe un archivo con ese nombre";
                    } else {
                        if (carpDestino != null) {
                            // muevo el archivo de origen a destino
                            carpDestino.archivos.add(archvOrigen);
                            return "Se copio el archivo de origen a destino";
                        } else {
                            return "La ruta de destino no es correcta";
                        }
                    }
                } else {
                    return "La ruta de origen no existe o es incorrecta";
                }
            } else {
                if (carpDestino != null) {
                    // agrego la carpeta al destino
                    carpDestino.carpetas.add(carpOrigen);
                    return "Se copio la carpeta de origen a destino";
                } else {
                    return "La ruta de destino no es correcta";
                }
            }
        }
    }

    ;
      
      public String cat(String nombreArchivo, Usuario usuarioActual) {
        if (nombreArchivo.isEmpty()) {
            return "El nombre del archivo ingresado es incorrecto";
        } else {
            String contenido = "";
            if (directorioActual.archivos != null) {
                for (Archivo arch : directorioActual.archivos) {
                    if (arch.nombreArch.equals(nombreArchivo)) {
                        for (String line : arch.linea) {
                            contenido = contenido.concat(" ").concat(line);
                        }
                    }
                }
                if (contenido.isEmpty()) {
                    return "El archivo no se encontro o no tiene contenido";
                } else {
                    if ((usuarioActual.nombreUsuario.equals(directorioActual.dueño.nombreUsuario)
                            && directorioActual.permiso.permisoDueño != 4
                            || directorioActual.permiso.permisoDueño != 5
                            || directorioActual.permiso.permisoDueño != 6
                            || directorioActual.permiso.permisoDueño != 7) || (directorioActual.permiso.permisoResto != 4
                            || directorioActual.permiso.permisoResto != 5
                            || directorioActual.permiso.permisoResto != 6
                            || directorioActual.permiso.permisoResto != 7)) {
                        return "No se tiene permiso para realizar esta acción";
                    }
                    return contenido;
                }
            } else {
                return "No se pudo realizar la operacion";
            }
        }
    }

    public String rm(String nombreArchivo, Usuario usuarioActual) {
        if (nombreArchivo.isEmpty()) {
            return "El nombre del archivo ingresado es incorrecto";
        } else {
            if (directorioActual.archivos != null) {
                for (Archivo arch : directorioActual.archivos) {
                    if (arch.nombreArch.equals(nombreArchivo)) {
                        if ((usuarioActual.nombreUsuario.equals(directorioActual.dueño.nombreUsuario) && directorioActual.permiso.permisoDueño != 7) || (directorioActual.permiso.permisoResto != 7)) {
                            return "No se tiene permiso para realizar esta acción";
                        }
                        directorioActual.archivos.remove(arch);
                        return "El archivo se elimino con exito";
                    }
                }
                return "El archivo no pudo ser borrado, chequee que el mismo exista bajo la ruta";
            } else {
                return "No se pudo eliminar el archivo";
            }
        }
    }

    public String cd(String ruta) {
        if (ruta.isEmpty()) {
            return "La ruta especificada es incorrecta";
        } else {
            Carpeta aux = null;
            ArrayList<Carpeta> nuevaRuta = new ArrayList<Carpeta>();
            Carpeta carpDestino = null;
            String[] rutasDestino = ruta.split("/");
            Boolean esRutaInicialDestino = false;

            // Encuentra la carpeta/archivo de Destino
            if (carpetasRuta.get(0).nombreDirectorio.equals(rutasDestino[0])) {
                carpDestino = carpetasRuta.get(0);
                nuevaRuta.add(carpDestino);
                esRutaInicialDestino = true;
            } else {
                carpDestino = directorioActual;
            }
            for (String r : rutasDestino) {
                if (!esRutaInicialDestino) {
                    aux = carpDestino;
                    carpDestino = findDirectory(r, carpDestino);
                    if (carpDestino != null) {
                        nuevaRuta.add(carpDestino);
                    }
                }
                esRutaInicialDestino = false;
            }
            if (carpDestino == null) {
                return "La ruta es incorrecta";
            } else {
                carpetasRuta = nuevaRuta;
                directorioActual = carpDestino;
                return pwd();
            }
        }
    }

    public String ls() {
        return "";
    }

    public String chmod(String permiso, String nombreArchivo, Usuario usuarioActual) {
        if (!nombreArchivo.isEmpty()) {
            if (directorioActual.archivos != null) {
                for (Archivo arch : directorioActual.archivos) {
                    if (arch.nombreArch.equals(nombreArchivo)) {
                        if (usuarioActual.nombreUsuario.equals(directorioActual.dueño.nombreUsuario) || !usuarioActual.esAdmin) {
                            String[] permisoSeparado = permiso.split("(?!^)");
                            if (permisoSeparado.length == 3) {
                                int[] permisoInt = new int[3];
                                permisoInt[0] = Integer.parseInt(permisoSeparado[0]);
                                permisoInt[1] = Integer.parseInt(permisoSeparado[1]);
                                permisoInt[2] = Integer.parseInt(permisoSeparado[2]);
                                if (permisoInt[0] > 7 || permisoInt[0] < 0
                                        || permisoInt[1] > 7 || permisoInt[1] < 0
                                        || permisoInt[2] > 7 || permisoInt[2] < 0) {
                                    return "El permiso ingresado no es correcto";
                                } else {
                                    arch.permiso = new Permiso(permisoInt[0], permisoInt[1], permisoInt[2]);
                                    return "Se modificó el permiso del archivo con éxito";
                                }
                            }
                            return "El permiso que indicó no es correcto";
                        }
                        return "No se tiene permiso para realizar esta acción";
                    }                   
                }
                return "El archivo no pudo ser borrado, chequee que el mismo exista bajo la ruta";
            }
            return "No se pudo eliminar el archivo";
        }
        return "El nombre del archivo ingresado es incorrecto";
    }

    public String chown(String nombreUsuario, String nombreArchivo, ArrayList<Usuario> usuarios, Usuario usuarioActual) {
        if (!nombreArchivo.isEmpty() && !nombreUsuario.isEmpty()) {
            for (Usuario user : usuarios) {
                if (nombreUsuario.equals(user.nombreUsuario)) {
                    if (directorioActual.archivos != null) {
                        for (Archivo arch : directorioActual.archivos) {
                            if (arch.nombreArch.equals(nombreArchivo)) {
                                if (arch.dueño == usuarioActual || usuarioActual.esAdmin) {
                                    arch.setDueño(user);
                                }
                                return "No se tiene permiso para realizar esta acción";
                            }
                        }
                    }
                    return "El archivo no existe bajo la ruta";
                }
                return "El usuario que ingresó no existe";
            }
        }
        return "El nombre de archivo o usuario ingresado está vacío";
    }
    
    public String catGrep(String nombreArchivo, String palabraABuscar, Usuario usuarioActual) {
        for (Archivo arch : directorioActual.archivos) {
            if (directorioActual.archivos != null) {
                if (cat(nombreArchivo, usuarioActual).contains(palabraABuscar)) {
                    return "La palabra existe dentro del archivo";
                }
                return "La palabra no se encuentra dentro del archivo indicado";
            }
            return "No se pudo encontrar el archivo";
        }
        return "No se pudo encontrar el archivo";
    }
    
}

package com.obligatorio1.obligatorio1.Dominio;

import java.util.ArrayList;

public class ControladorCarpeta {

    public ArrayList<Carpeta> carpetasRuta;
    public Carpeta directorioActual;

    public ControladorCarpeta(Usuario root) {
        carpetasRuta = new ArrayList<>();
        directorioActual = new Carpeta("Inicio", 7, 7, 7, null);
        carpetasRuta.add(directorioActual);
        directorioActual.setDueño(root);
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
        }
        if (permisoTotal(directorioActual.permiso, usuarioActual, directorioActual, null)) {
            Carpeta nuevoDir = new Carpeta(nombreDir, 7, 7, 5, directorioActual);
            nuevoDir.setDueño(usuarioActual);
            directorioActual.carpetas.add(nuevoDir);
            return "Se creó " + nuevoDir.nombreDirectorio + " en " + directorioActual.nombreDirectorio;
        }
        return "No se tiene permiso para realizar esta acción";

    }

    public String rmdir(String nombreDir, Usuario usuarioActual) {
        if (nombreDir.equals("Inicio")) {
            return "No se puede borrar la carpeta inicial.";
        } else {
            if (directorioActual.carpetas != null) {
                for (Carpeta directorio : directorioActual.carpetas) {
                    if (directorio.nombreDirectorio.equals(nombreDir)) {
                        if (permisoTotal(directorioActual.permiso, usuarioActual, directorioActual, null)) {
                            directorioActual.carpetas.remove(directorio);
                            return "Se borro el directorio";
                        }
                    }
                }
                return "No se tiene permiso para realizar esta acción";
            }
            return "No se pudo borrar el directorio";
        }
    }

    public String touch(String nombreArchivo, Usuario usuarioActual) {
        if (directorioActual.archivos != null) {
            for (Archivo arch : directorioActual.archivos) {
                if (arch.nombreArch.equals(nombreArchivo)) {
                    return "Ya existe un archivo con este nombre";
                }
            }
            if (permisoTotal(directorioActual.permiso, usuarioActual, directorioActual, null)) {
                Archivo nuevoArchivo = new Archivo(7, 7, 5, nombreArchivo, usuarioActual);
                directorioActual.archivos.add(nuevoArchivo);
                return "El archivo se agregó con exito";
            }
            return "No se tiene permiso para realizar esta acción";
        }
        return "No se pudo crear el archivo";
    }

    public String echo(String texto, String nombreArchivo, Usuario usuarioActual) {
        String textoSinComillas = texto.substring(0).substring(texto.length() -1);
        for (Archivo arch : directorioActual.archivos) {
            if (arch.nombreArch.equals(nombreArchivo)) {
                if (permisoEscritura(directorioActual.permiso, usuarioActual, directorioActual, arch)) {
                    arch.linea.add(textoSinComillas);
                }
                return "No se tiene permiso para realizar esta acción";
            }
        }
        return "El archivo no existe";
    }

    public boolean permisoLectura(Permiso permiso, Usuario usuarioActual, Carpeta carpeta, Archivo archivo) {
        if (carpeta != null && archivo != null){
            return (((usuarioActual.nombreUsuario.equals(carpeta.dueño.nombreUsuario)
                && (carpeta.permiso.permisoDueño == 4
                || carpeta.permiso.permisoDueño == 5
                || carpeta.permiso.permisoDueño == 6
                || carpeta.permiso.permisoDueño == 7)) || (carpeta.permiso.permisoResto == 4
                || carpeta.permiso.permisoResto == 5
                || carpeta.permiso.permisoResto == 6
                || carpeta.permiso.permisoResto == 7))
                && ((usuarioActual.nombreUsuario.equals(archivo.dueño.nombreUsuario)
                && (archivo.permiso.permisoDueño == 4
                || archivo.permiso.permisoDueño == 5
                || archivo.permiso.permisoDueño == 6
                || archivo.permiso.permisoDueño == 7)) || (archivo.permiso.permisoResto == 4
                || archivo.permiso.permisoResto == 5
                || archivo.permiso.permisoResto == 6
                || archivo.permiso.permisoResto == 7)));
        }
        if (carpeta != null){
            return ((usuarioActual.nombreUsuario.equals(carpeta.dueño.nombreUsuario)
                && (carpeta.permiso.permisoDueño == 4
                || carpeta.permiso.permisoDueño == 5
                || carpeta.permiso.permisoDueño == 6
                || carpeta.permiso.permisoDueño == 7)) || (carpeta.permiso.permisoResto == 4
                || carpeta.permiso.permisoResto == 5
                || carpeta.permiso.permisoResto == 6
                || carpeta.permiso.permisoResto == 7));
        }
        if (archivo != null){
            return ((usuarioActual.nombreUsuario.equals(archivo.dueño.nombreUsuario)
                    && (archivo.permiso.permisoDueño == 4
                    || archivo.permiso.permisoDueño == 5
                    || archivo.permiso.permisoDueño == 6
                    || archivo.permiso.permisoDueño == 7)) || (archivo.permiso.permisoResto == 4
                    || archivo.permiso.permisoResto == 5
                    || archivo.permiso.permisoResto == 6
                    || archivo.permiso.permisoResto == 7));
        }
        return false;

    }

    public boolean permisoEscritura(Permiso permiso, Usuario usuarioActual, Carpeta carpeta, Archivo archivo) {
        if (carpeta != null && archivo != null){
            return (((usuarioActual.nombreUsuario.equals(carpeta.dueño.nombreUsuario)
                    && carpeta.permiso.permisoDueño == 2
                    || carpeta.permiso.permisoDueño == 3
                    || carpeta.permiso.permisoDueño == 6
                    || carpeta.permiso.permisoDueño == 7) || (carpeta.permiso.permisoResto == 2
                    || carpeta.permiso.permisoResto == 3
                    || carpeta.permiso.permisoResto == 6
                    || carpeta.permiso.permisoResto == 7))
                    && ((usuarioActual.nombreUsuario.equals(archivo.dueño.nombreUsuario)
                    && archivo.permiso.permisoDueño == 2
                    || archivo.permiso.permisoDueño == 3
                    || archivo.permiso.permisoDueño == 6
                    || archivo.permiso.permisoDueño == 7) || (archivo.permiso.permisoResto == 2
                    || archivo.permiso.permisoResto == 3
                    || archivo.permiso.permisoResto == 6
                    || archivo.permiso.permisoResto == 7)));
        }
        if (carpeta != null){
            return ((usuarioActual.nombreUsuario.equals(carpeta.dueño.nombreUsuario)
                    && carpeta.permiso.permisoDueño == 2
                    || carpeta.permiso.permisoDueño == 3
                    || carpeta.permiso.permisoDueño == 6
                    || carpeta.permiso.permisoDueño == 7) || (carpeta.permiso.permisoResto == 2
                    || carpeta.permiso.permisoResto == 3
                    || carpeta.permiso.permisoResto == 6
                    || carpeta.permiso.permisoResto == 7));
        }
        if (archivo != null){
            return ((usuarioActual.nombreUsuario.equals(archivo.dueño.nombreUsuario)
                    && archivo.permiso.permisoDueño == 2
                    || archivo.permiso.permisoDueño == 3
                    || archivo.permiso.permisoDueño == 6
                    || archivo.permiso.permisoDueño == 7) || (archivo.permiso.permisoResto == 2
                    || archivo.permiso.permisoResto == 3
                    || archivo.permiso.permisoResto == 6
                    || archivo.permiso.permisoResto == 7));
        }
        return false;
    }

    public boolean permisoTotal(Permiso permiso, Usuario usuarioActual, Carpeta carpeta, Archivo archivo) {
        if (carpeta != null && archivo != null){
            return (((usuarioActual.nombreUsuario.equals(carpeta.dueño.nombreUsuario)
                    && carpeta.permiso.permisoDueño == 7) || (carpeta.permiso.permisoResto == 7))
                    && ((usuarioActual.nombreUsuario.equals(archivo.dueño.nombreUsuario)
                    && archivo.permiso.permisoDueño == 7) || (archivo.permiso.permisoResto == 7)));
        }
        if (carpeta != null){
            return ((usuarioActual.nombreUsuario.equals(carpeta.dueño.nombreUsuario)
                    && carpeta.permiso.permisoDueño == 7) || (carpeta.permiso.permisoResto == 7));
        }
        if (archivo != null){
            return ((usuarioActual.nombreUsuario.equals(archivo.dueño.nombreUsuario)
                    && archivo.permiso.permisoDueño == 7) || (archivo.permiso.permisoResto == 7));
        }
        return false;
    }

    public Carpeta findDirectory(String ruta, Carpeta carpPadre, Usuario usuarioActual) {
        if (this.permisoLectura(carpPadre.permiso, usuarioActual, carpPadre, null)){
            if (carpPadre.carpetas != null) {
                for (Carpeta nodo : carpPadre.carpetas) {
                    if (nodo.nombreDirectorio.equals(ruta)) {
                        return nodo;
                    }
                }
                return null;
            }
            return null;
        }
        return null;
    }
    
    public String handleFiles(String origen, String destino, Usuario usuarioActual, Boolean onlyMove){
        Carpeta carpOrigen = null;
            Carpeta aux = null;
            Carpeta carpDestino = null;
            Carpeta aux2 = null;
            String[] rutasOrigen = origen.substring(1).split("/");
            String[] rutasDestino = destino.substring(1).split("/");
            Boolean esRutaInicialDestino = false;
            Boolean esRutaInicialOrigen = false;

            // Encuentra la carpeta/archivo de Destino
            if (carpetasRuta.get(0).nombreDirectorio.equals(rutasDestino[0])) {
                carpDestino = carpetasRuta.get(0);
                aux2 = carpDestino;
                esRutaInicialDestino = true;
            } else {
                carpDestino = directorioActual;
            }
            for (String ruta : rutasDestino) {
                if (!esRutaInicialDestino) {
                    aux2 = carpDestino;
                    carpDestino = findDirectory(ruta, carpDestino, usuarioActual);
                }
                esRutaInicialDestino = false;
                if (carpDestino == null) {
                    return "La ruta de destino especificada no es correcta";
                }
            }

            // Encuentra la carpeta/archivo de Origen
            if (carpetasRuta.get(0).nombreDirectorio.equals(rutasOrigen[0])) {
                carpOrigen = carpetasRuta.get(0);
                aux = carpOrigen;
                esRutaInicialOrigen = true;
            } else {
                carpOrigen = directorioActual;
            }
            for (String ruta : rutasOrigen) {
                if (!esRutaInicialOrigen) {
                    aux = carpOrigen;
                    carpOrigen = findDirectory(ruta, carpOrigen, usuarioActual);
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
                            if (this.permisoTotal(archvOrigen.permiso, usuarioActual, null, archvOrigen)){
                                // muevo el archivo de origen a destino
                                carpDestino.archivos.add(archvOrigen);
                                if (onlyMove){
                                    aux.archivos.remove(archvOrigen);
                                }
                                return "Se movio el archivo de origen a destino";
                            }
                             return "No se tienen permisos para realizar esta accion";
                        } else {
                            if (this.permisoTotal(archvOrigen.permiso, usuarioActual, null, archvOrigen)){
                                archvOrigen.nombreArch = destino;
                                return "Se renombro el archivo de origen por " + destino;
                            }
                            return "No se tienen permisos para realizar esta accion";
                        }
                    }
                } else {
                    return "La ruta de origen no existe o es incorrecta";
                }
            } else {
                if (carpDestino != null) { 
                    if (this.permisoTotal(carpDestino.permiso, usuarioActual, carpDestino, null)){
                        // agrego la carpeta al destino
                        carpDestino.carpetas.add(carpOrigen);
                        if (onlyMove){
                            // Borro la carpeta del origen
                            aux.carpetas.remove(carpOrigen);
                            return "Se movio la carpeta de origen a destino";
                        }
                         return "Se copio la carpeta de origen a destino";
                    }
                    return "No se tienen permisos para realizar esta accion";
                } else {
                     if (this.permisoTotal(carpOrigen.permiso, usuarioActual, carpOrigen, null)){
                        // renombramos la carpeta origen por el nombre de destino
                        carpOrigen.nombreDirectorio = destino;
                        return "Se renombro la ruta de origen por " + destino;
                     }
                      return "No se tienen permisos para realizar esta accion";
                }
            }
    }

    public String mv(String origen, String destino, Usuario usuarioActual) {
        if (origen.isEmpty() || destino.isEmpty()) {
            return "No es posible mover un archivo sin ruta de origen o destino";
        } else {
            return this.handleFiles(origen, destino, usuarioActual, true);
        }
    }

    public String cp(String origen, String destino, Usuario usuarioActual) {
        if (origen.isEmpty() || destino.isEmpty()) {
            return "No es posible mover un archivo sin ruta de origen o destino";
        } else {
            return this.handleFiles(origen, destino, usuarioActual, false);
        }
    }

    public String cat(String nombreArchivo, Usuario usuarioActual) {
        String contenido = "";
        if (directorioActual.archivos != null) {
            for (Archivo arch : directorioActual.archivos) {
                if (arch.nombreArch.equals(nombreArchivo)) {
                    for (String line : arch.linea) {
                        contenido = contenido.concat(" ").concat(line);
                    }
                    if (!contenido.isEmpty()) {
                        if (permisoLectura(directorioActual.permiso, usuarioActual, directorioActual, null)) {
                            return contenido;
                        }
                        return "No se tiene permiso para realizar esta acción";
                    }
                    return "El archivo no tiene contenido";
                }
            }
        }
        return "No se encontró el archivo";
    }

    public String rm(String nombreArchivo, Usuario usuarioActual) {
        if (directorioActual.archivos != null) {
            for (Archivo arch : directorioActual.archivos) {
                if (arch.nombreArch.equals(nombreArchivo)) {
                    if (permisoTotal(directorioActual.permiso, usuarioActual, directorioActual, arch)) {
                        directorioActual.archivos.remove(arch);
                        return "El archivo se eliminó con exito";
                    }
                    return "No se tiene permiso para realizar esta acción";
                }
            }
            return "El archivo no pudo ser borrado, chequee que el mismo exista bajo la ruta";
        } else {
            return "El directorio actual no posee archivos";
        }
    }

    public String cd(String ruta, Usuario usuarioActual) {
        if (ruta.isEmpty()) {
            return "La ruta especificada es incorrecta";
        } else {
            Carpeta aux = null;
            ArrayList<Carpeta> nuevaRuta = new ArrayList<Carpeta>();
            Carpeta carpDestino = null;
            String[] rutasDestino = ruta.substring(1).split("/");
            Boolean esRutaInicialDestino = false;

            if (ruta.equals("..")) {
                if (directorioActual.nombreDirectorio.equals("Inicio")) {
                    return "No se puede realizar esta accion";
                }
                carpetasRuta.remove(carpetasRuta.size() - 1);
                directorioActual = directorioActual.carpetaPadre;
                return this.pwd();
            }

            // Encuentra la carpeta/archivo de Destino
            if (carpetasRuta.get(0).nombreDirectorio.equals(rutasDestino[0])) {
                carpDestino = carpetasRuta.get(0);
                nuevaRuta.add(carpDestino);
                esRutaInicialDestino = true;
            }

            for (String r : rutasDestino) {
                if (!esRutaInicialDestino) {
                    aux = carpDestino;
                    carpDestino = findDirectory(r, carpDestino, usuarioActual);
                    if (carpDestino != null) {
                        nuevaRuta.add(carpDestino);
                    }
                }
                esRutaInicialDestino = false;
            }
            if (carpDestino == null) {
                return "La ruta es incorrecta";
            } else {
                if (this.permisoLectura(carpDestino.permiso, usuarioActual, carpDestino, null)){
                    carpetasRuta = nuevaRuta;
                    directorioActual = carpDestino;
                    return this.pwd();
                }
                return "No se tienen permisos para realizar esta accion";
            }
        }
    }

    public String permisosLinux(int permiso) {
        switch (permiso) {
            case 0:
                return ("- - -");
            case 1:
                return ("- - x");
            case 2:
                return ("- w -");
            case 3:
                return ("- w x");
            case 4:
                return ("r - -");
            case 5:
                return ("r - x");
            case 6:
                return ("r w -");
            case 7:
                return ("r w x");
        }
        return "";
    }

    public String ls() {
        if (directorioActual.archivos.isEmpty() && directorioActual.carpetas.isEmpty()) {
            return "Esta carpeta no tiene contenido";
        } else {
            String espacio = " ";
            String contenido = "\n";
            if (!directorioActual.archivos.isEmpty()) {
                for (Archivo arch : directorioActual.archivos) {
                    String permisosDeDueño = this.permisosLinux(arch.permiso.permisoDueño);
                    String permisosDeGrupo = this.permisosLinux(arch.permiso.permisoGrupo);
                    String permisosDeResto = this.permisosLinux(arch.permiso.permisoResto);
                    contenido = contenido + permisosDeDueño + espacio + permisosDeGrupo + espacio + permisosDeResto + espacio + arch.dueño.nombreUsuario + espacio + arch.fechaHora + espacio + arch.nombreArch + "\n";
                }
            }
            if (!directorioActual.carpetas.isEmpty()) {
                for (Carpeta carp : directorioActual.carpetas) {
                    String permisosDeDueño = this.permisosLinux(carp.permiso.permisoDueño);
                    String permisosDeGrupo = this.permisosLinux(carp.permiso.permisoGrupo);
                    String permisosDeResto = this.permisosLinux(carp.permiso.permisoResto);
                    contenido = contenido + permisosDeDueño + espacio + permisosDeGrupo + espacio + permisosDeResto + espacio + carp.dueño.nombreUsuario + espacio + carp.fechaHora + espacio + carp.nombreDirectorio + "\n";
                }
            }
            return contenido;
        }
    }

    ;

    public String chmod(String permiso, String nombreArchivo, Usuario usuarioActual) {
        if (!nombreArchivo.isEmpty()) {
            if (directorioActual.archivos != null) {
                for (Archivo arch : directorioActual.archivos) {
                    if (arch.nombreArch.equals(nombreArchivo)) {
                        if (usuarioActual.nombreUsuario.equals(directorioActual.dueño.nombreUsuario) || usuarioActual.esAdmin) {
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

   public String lsGrep(String palabraABuscar) {
        String retornoArch = "\n";
        String retornoCarp = "\n";
        if (directorioActual.archivos != null) {
            for (Archivo arch: directorioActual.archivos){    
                 if (arch.nombreArch.equals(palabraABuscar)) {
                     retornoArch = retornoArch + arch.nombreArch + "\n";
                 }
            }
        } else if (directorioActual.carpetas != null) {
                    for (Carpeta carp: directorioActual.carpetas){
                        if (carp.nombreDirectorio.equals(palabraABuscar)) {
                            retornoCarp = retornoCarp + carp.nombreDirectorio + "\n";
                        }
                       
                    }
        } else {
            return "No se pudo encontrar el archivo o carpeta.";
        }
            return retornoArch + retornoCarp;
    }
    
}

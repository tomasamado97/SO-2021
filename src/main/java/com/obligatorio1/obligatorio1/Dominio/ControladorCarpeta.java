package com.obligatorio1.obligatorio1.Dominio;

import com.sun.tools.javac.util.ArrayUtils;
import java.util.ArrayList;

public class ControladorCarpeta {
    public ArrayList<Carpeta> carpetasRuta;
    public Carpeta directorioActual;
    
      public ControladorCarpeta(){
         carpetasRuta = null;
         directorioActual = null;
     }
    
    public String pwd(){
        String ruta = "/";
        for (Carpeta carpeta : carpetasRuta){
            ruta = ruta + "/" + carpeta.nombreDirecto;
        }
        return ruta;
    }
    
    public String mkdir(String nombreDir){
        ArrayList<Carpeta> directoriosExistentes = directorioActual.carpetas;
        for (Carpeta directorio: directoriosExistentes){
            if (directorio.nombreDirecto.equals(nombreDir)){
                return "Ya existe un directorio con ese nombre";
            }
        }
        Carpeta nuevoDir = new Carpeta(nombreDir, directorioActual);
        directorioActual.carpetas.add(nuevoDir);
        return "Se creo" + nuevoDir.nombreDirecto + "en " + directorioActual.nombreDirecto;
    }
    
    public String rmdir(String nombreDir){
        if (nombreDir.equals("/")){
            return "No se puede borrar la carpeta inicial.";
        }else{
            ArrayList<Carpeta> directoriosExistentes = directorioActual.carpetas;
            for(Carpeta directorio: directoriosExistentes){
                if (directorio.nombreDirecto.equals(nombreDir)){
                    directorio.borrarCarpeta(directorio.nombreDirecto);
                }
            }
            return "Se borro el directorio";
        }
    }
    
     public String touch(String nombreArchivo) {
       
        if(nombreArchivo.isEmpty()){
            return "No se puede crear un archivo sin nombre";
        }else {
            boolean archivoRepetido = false;
            for(Archivo arch: directorioActual.archivos){
                if (arch.nombreArch.equals(nombreArchivo)){
                    archivoRepetido = true;
                }
            }
            if (archivoRepetido) {
                return "Ya existe un archivo con este nombre, utilice otro.";
            } else {
                Archivo nuevoArchivo = new Archivo();
                directorioActual.archivos.add(nuevoArchivo);
                return "El archivo se agregó con exito";
            }
        }
    }
    
     public String echo(String texto, String nombreArchivo){
        if(nombreArchivo.isEmpty()){
            return "No se puede agregar el texto un archivo sin nombre";
        }else {
            for(Archivo arch: directorioActual.archivos){
                if (arch.nombreArch.equals(nombreArchivo)){
                    arch.linea.add(texto);
                }
            }
            return "Se agrego el texto al archivo";
        }
     }
     
     public Carpeta findDirectory(String ruta, Carpeta carpPadre){
         for (Carpeta nodo: carpPadre.carpetas){
             if (nodo.nombreDirecto.equals(ruta)){
                 return nodo;
             }
         }
         return null;
     }
     
     public String mv(String origen, String destino){
         if (origen.isEmpty() || destino.isEmpty()){
             return "No es posible mover un archivo sin ruta de origen o destino";
         }else{
               Carpeta carpOrigen = null;
               Carpeta aux = null;
               String[] rutasOrigen = origen.split("/");
               String[] rutasDestino = destino.split("/");
               Boolean esRutaInicial = false;
               if (carpetasRuta.get(0).nombreDirecto.equals(rutasOrigen[0])){
                   carpOrigen = carpetasRuta.get(0);
                   esRutaInicial = true;
               }else{
                   carpOrigen = directorioActual;
               }
               for (String ruta: rutasOrigen){
                   if (!esRutaInicial){
                        aux = carpOrigen;
                        carpOrigen = findDirectory(ruta, carpOrigen);
                   }
                   esRutaInicial = false;
                   if (carpOrigen == null){
                       return "La ruta de origen especificada no es correcta";
                   }
               }
               if (carpOrigen == null){
                   Boolean esArchivo = false;
                   Archivo archv = null;
                   for (Archivo arch: aux.archivos){
                       if (rutasOrigen[rutasOrigen.length -1].equals(arch.nombreArch)){
                           esArchivo = true;
                           archv = arch;
                       }
                   }
                   if (esArchivo){
                       
                   }else{
                       return "La ruta de origen no existe o es incorrecta";
                   }
               }else{
                   
               }
              
         }
         return "";
     };
     
      public String cp(String origen, String destino){
         if (origen.isEmpty() || destino.isEmpty()){
             return "No es posible copiar un archivo sin ruta de origen o destino";
         }else{
             if (pwd().equals(origen)){
                 Carpeta directorioAMover = directorioActual;
             }
         }
         return "";
     };
      
      public String cat(String nombreArchivo){
          if (nombreArchivo.isEmpty()){
              return "El nombre del archivo ingresado es incorrecto";
          }else{
              String contenido = "";
              for (Archivo arch: directorioActual.archivos){
                  if (arch.nombreArch.equals(nombreArchivo)){
                      for (String line: arch.linea){
                          contenido = contenido.concat(" ").concat(line);
                      }
                  }
              }
              if (contenido.isEmpty()){
                  return "El archivo no se encontro o no tiene contenido";
              }else{
                  return contenido;
              }
          }
      }
      
       public String rm(String nombreArchivo){
          if (nombreArchivo.isEmpty()){
              return "El nombre del archivo ingresado es incorrecto";
          }else{
              Boolean removed = false;
              for (Archivo arch: directorioActual.archivos){
                  if (arch.nombreArch.equals(nombreArchivo)){
                     directorioActual.archivos.remove(arch);
                     removed = true;
                  }
              }
              if (removed){
                  return "El archivo se elimino con exito";
              }else{
                  return "El archivo no pudo ser borrado, chequee que el mismo exista bajo la ruta";
              }
          }
      }
       
       public void cd(String ruta){
           
       }
}

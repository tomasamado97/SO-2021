package com.obligatorio1.obligatorio1.Dominio;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ControladorCarpeta {
    public ArrayList<Carpeta> carpetasRuta;
    public Carpeta directorioActual;
    
      public ControladorCarpeta(){
         carpetasRuta = new ArrayList<Carpeta>();
         directorioActual =  null;
     }
    
    public String pwd(){
        String ruta = "/";
        for (Carpeta carpeta : carpetasRuta){
            ruta = ruta + "/" + carpeta.nombreDirectorio;
        }
        return ruta;
    }
    
    public String mkdir(String nombreDir, Usuario usuarioActual){
        ArrayList<Carpeta> directoriosExistentes = directorioActual.carpetas;
        for (Carpeta directorio: directoriosExistentes){
            if (directorio.nombreDirectorio.equals(nombreDir)){
                return "Ya existe un directorio con ese nombre";
            }
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss");  
        String fechaHora = now.format(format);
        Carpeta nuevoDir = new Carpeta(nombreDir, 7, 7, 5, directorioActual, usuarioActual, fechaHora);
        directorioActual.carpetas.add(nuevoDir);
        return "Se creo" + nuevoDir.nombreDirectorio + "en " + directorioActual.nombreDirectorio;
    }
    
    public String rmdir(String nombreDir){
        if (nombreDir.equals("/")){
            return "No se puede borrar la carpeta inicial.";
        }else{
            ArrayList<Carpeta> directoriosExistentes = directorioActual.carpetas;
            for(Carpeta directorio: directoriosExistentes){
                if (directorio.nombreDirectorio.equals(nombreDir)){
                    directorio.borrarCarpeta(directorio.nombreDirectorio);
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
             if (nodo.nombreDirectorio.equals(ruta)){
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
               Carpeta carpDestino = null;
               Carpeta aux2 = null;
               String[] rutasOrigen = origen.split("/");
               String[] rutasDestino = destino.split("/");
               Boolean esRutaInicialDestino = false;
               Boolean esRutaInicialOrigen = false;
                
               // Encuentra la carpeta/archivo de Destino
               if (carpetasRuta.get(0).nombreDirectorio.equals(rutasDestino[0])){
                   carpDestino = carpetasRuta.get(0);
                   esRutaInicialDestino = true;
               }else{
                   carpDestino = directorioActual;
               }
               for (String ruta: rutasDestino){
                   if (!esRutaInicialDestino){
                        aux2 = carpDestino;
                        carpDestino = findDirectory(ruta, carpDestino);
                   }
                   esRutaInicialDestino = false;
                   if (carpDestino == null){
                       return "La ruta de destino especificada no es correcta";
                   }
               }
               
               
               // Encuentra la carpeta/archivo de Origen
               if (carpetasRuta.get(0).nombreDirectorio.equals(rutasOrigen[0])){
                   carpOrigen = carpetasRuta.get(0);
                   esRutaInicialOrigen = true;
               }else{
                   carpOrigen = directorioActual;
               }
               for (String ruta: rutasOrigen){
                   if (!esRutaInicialOrigen){
                        aux = carpOrigen;
                        carpOrigen = findDirectory(ruta, carpOrigen);
                   }
                   esRutaInicialOrigen = false;
                   if (carpOrigen == null){
                       return "La ruta de origen especificada no es correcta";
                   }
               }
               
               // Chequea que sea carpeta o archivo y que existan
               if (carpOrigen == null){
                   Boolean esArchivoDestino = false;
                   Boolean esArchivoOrigen = false;
                   Archivo archvOrigen = null;
                   Archivo archvDestino = null;
                   for (Archivo arch: aux.archivos){
                       if (rutasOrigen[rutasOrigen.length -1].equals(arch.nombreArch)){
                           esArchivoOrigen = true;
                           archvOrigen = arch;
                       }
                   }
                   for (Archivo arch: aux2.archivos){
                       if (rutasDestino[rutasDestino.length -1].equals(arch.nombreArch)){
                           esArchivoDestino = true;
                           archvDestino = arch;
                       }
                   }
                   if (esArchivoOrigen){
                       if (esArchivoDestino){
                           // ya existe un archivo con ese nombre en el destino
                           return "No se puede renombrar el archivo de origen debido a que en la ruta de destino ya existe un archivo con ese nombre";
                       }else{
                           if (carpDestino != null){
                               // muevo el archivo de origen a destino
                               carpDestino.archivos.add(archvOrigen);
                               aux.archivos.remove(archvOrigen);
                               return "Se movio el archivo de origen a destino";
                           }else{
                               archvOrigen.nombreArch = destino;
                               return "Se renombro el archivo de origen por " + destino;
                           }
                       }
                   }else{
                       return "La ruta de origen no existe o es incorrecta";
                   }
               }else{
                   if (carpDestino != null){
                       // agrego la carpeta al destino
                       carpDestino.carpetas.add(carpOrigen);
                       // Borro la carpeta del origen
                       carpOrigen.carpetaPadre.carpetas.remove(carpOrigen);
                       return "Se movio la carpeta de origen a destino";
                   }else{
                       // renombramos la carpeta origen por el nombre de destino
                       carpOrigen.nombreDirectorio = destino;
                       return "Se renombro la ruta de origen por " + destino;
                   }
               }
         }
     };
      
       public String cp(String origen, String destino){
         if (origen.isEmpty() || destino.isEmpty()){
             return "No es posible mover un archivo sin ruta de origen o destino";
         }else{
               Carpeta carpOrigen = null;
               Carpeta aux = null;
               Carpeta carpDestino = null;
               Carpeta aux2 = null;
               String[] rutasOrigen = origen.split("/");
               String[] rutasDestino = destino.split("/");
               Boolean esRutaInicialDestino = false;
               Boolean esRutaInicialOrigen = false;
                
               // Encuentra la carpeta/archivo de Destino
               if (carpetasRuta.get(0).nombreDirectorio.equals(rutasDestino[0])){
                   carpDestino = carpetasRuta.get(0);
                   esRutaInicialDestino = true;
               }else{
                   carpDestino = directorioActual;
               }
               for (String ruta: rutasDestino){
                   if (!esRutaInicialDestino){
                        aux2 = carpDestino;
                        carpDestino = findDirectory(ruta, carpDestino);
                   }
                   esRutaInicialDestino = false;
               }
               
               
               // Encuentra la carpeta/archivo de Origen
               if (carpetasRuta.get(0).nombreDirectorio.equals(rutasOrigen[0])){
                   carpOrigen = carpetasRuta.get(0);
                   esRutaInicialOrigen = true;
               }else{
                   carpOrigen = directorioActual;
               }
               for (String ruta: rutasOrigen){
                   if (!esRutaInicialOrigen){
                        aux = carpOrigen;
                        carpOrigen = findDirectory(ruta, carpOrigen);
                   }
                   esRutaInicialOrigen = false;
               }
               
               // Chequea que sea carpeta o archivo y que existan
               if (carpOrigen == null){
                   Boolean esArchivoDestino = false;
                   Boolean esArchivoOrigen = false;
                   Archivo archvOrigen = null;
                   Archivo archvDestino = null;
                   for (Archivo arch: aux.archivos){
                       if (rutasOrigen[rutasOrigen.length -1].equals(arch.nombreArch)){
                           esArchivoOrigen = true;
                           archvOrigen = arch;
                       }
                   }
                   for (Archivo arch: aux2.archivos){
                       if (rutasDestino[rutasDestino.length -1].equals(arch.nombreArch)){
                           esArchivoDestino = true;
                           archvDestino = arch;
                       }
                   }
                   if (esArchivoOrigen){
                       if (esArchivoDestino){
                           // ya existe un archivo con ese nombre en el destino
                           return "No se puede renombrar el archivo de origen debido a que en la ruta de destino ya existe un archivo con ese nombre";
                       }else{
                           if (carpDestino != null){
                               // muevo el archivo de origen a destino
                               carpDestino.archivos.add(archvOrigen);
                               return "Se copio el archivo de origen a destino";
                           }else{
                               return "La ruta de destino no es correcta";
                           }
                       }
                   }else{
                       return "La ruta de origen no existe o es incorrecta";
                   }
               }else{
                   if (carpDestino != null){
                       // agrego la carpeta al destino
                       carpDestino.carpetas.add(carpOrigen);
                       return "Se copio la carpeta de origen a destino";
                   }else{
                       return "La ruta de destino no es correcta";
                   }
               }
         }
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
       
       public String cd(String ruta){
            if (ruta.isEmpty()){
                return "La ruta especificada es incorrecta";
            }else{
                  Carpeta aux = null;
                  ArrayList<Carpeta> nuevaRuta = new ArrayList<Carpeta>();
                  Carpeta carpDestino = null;
                  String[] rutasDestino = ruta.split("/");
                  Boolean esRutaInicialDestino = false;

                  // Encuentra la carpeta/archivo de Destino
                  if (carpetasRuta.get(0).nombreDirectorio.equals(rutasDestino[0])){
                      carpDestino = carpetasRuta.get(0);
                      nuevaRuta.add(carpDestino);
                      esRutaInicialDestino = true;
                  }else{
                      carpDestino = directorioActual;
                  }
                  for (String r: rutasDestino){
                      if (!esRutaInicialDestino){
                           aux = carpDestino;
                           carpDestino = findDirectory(r, carpDestino);
                           if (carpDestino != null){
                               nuevaRuta.add(carpDestino);
                           }
                      }
                      esRutaInicialDestino = false;
                  }
                  if (carpDestino == null){
                      return "La ruta es incorrecta";
                  }else{
                      carpetasRuta = nuevaRuta;
                      directorioActual = carpDestino;
                      return pwd();
                  }
          }
       }
       
       public String ls(){
           return "";
       };
};

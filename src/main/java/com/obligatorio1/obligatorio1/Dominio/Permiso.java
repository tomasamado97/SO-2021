package com.obligatorio1.obligatorio1.Dominio;

public class Permiso {
    public int permisoDueño;
    public int permisoGrupo;
    public int permisoResto;

    public int getPermisoDueño() {
        return permisoDueño;
    }

    public void setPermisoDueño(int permisoDueño) {
        this.permisoDueño = permisoDueño;
    }

    public int getPermisoGrupo() {
        return permisoGrupo;
    }

    public void setPermisoGrupo(int permisoGrupo) {
        this.permisoGrupo = permisoGrupo;
    }

    public int getPermisoResto() {
        return permisoResto;
    }

    public void setPermisoResto(int permisoResto) {
        this.permisoResto = permisoResto;
    }
    
    public Permiso(int unPermisoDueño, int unPermisoGrupo, int unPermisoResto) {
        this.setPermisoDueño(unPermisoDueño);
        this.setPermisoGrupo(unPermisoGrupo);
        this.setPermisoResto(unPermisoResto);
    }
    
}

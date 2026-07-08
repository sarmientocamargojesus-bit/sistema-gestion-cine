package model.auth;

import model.sala.*;
import model.butaca.*;
import model.reserva.*;
import model.auth.*;


public enum Rol {
    ADMIN("Administrador"),
    CAJERO("Cajero");

    private final String nombre;

    Rol(String nombre) { this.nombre = nombre; }

    public String getNombre() { return nombre; }
}

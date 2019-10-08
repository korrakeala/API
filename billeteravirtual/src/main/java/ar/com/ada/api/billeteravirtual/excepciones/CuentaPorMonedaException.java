package ar.com.ada.api.billeteravirtual.excepciones;

import ar.com.ada.api.billeteravirtual.entities.*;

/**
 * CuentaPorMonedaException
 */
public class CuentaPorMonedaException extends Exception{

    public CuentaPorMonedaException(String mensaje){
        super(mensaje);
    }
}
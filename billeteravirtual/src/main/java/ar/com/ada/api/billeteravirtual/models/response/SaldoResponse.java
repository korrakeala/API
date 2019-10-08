package ar.com.ada.api.billeteravirtual.models.response;

import java.util.*;

import ar.com.ada.api.billeteravirtual.entities.*;

/**
 * SaldoResponse
 */
public class SaldoResponse {

    public boolean isOk = false;
    public String message = "";
    public int billeteraId;
    public String moneda;
    public double saldo;
    //public Billetera b;
    //public List saldos;
}
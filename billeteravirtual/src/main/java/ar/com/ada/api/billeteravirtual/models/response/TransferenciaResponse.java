package ar.com.ada.api.billeteravirtual.models.response;

/**
 * TransferenciaResponse
 */
public class TransferenciaResponse {

    public boolean isOk = false;
    public String message = "";
    public int billeteraIdOrig;
    public int billeteraIdDest;
    public double importe;
    public String concepto;
    public int operacionId;
}
package ar.com.ada.api.billeteravirtual.models.response;

import java.math.BigDecimal;

/**
 * TransferenciaResponse
 */
public class TransferenciaResponse {

    public boolean isOk = false;
    public String message = "";
    public int billeteraIdOrig;
    public int billeteraIdDest;
    public BigDecimal importe;
    public String concepto;
    public int operacionId;
}
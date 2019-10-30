package ar.com.ada.api.billeteravirtual.models.request;

import java.math.BigDecimal;

/**
 * TransferenciaRequest
 */
public class TransferenciaRequest {

    public String emailUsuarioDest;
    public BigDecimal importe;
    public String concepto;
}
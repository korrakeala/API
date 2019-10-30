package ar.com.ada.api.billeteravirtual.models.request;

import java.math.BigDecimal;

/**
 * MovimientoRequest
 */
public class MovimientoRequest {

    public String moneda;
    public String concepto;
    public BigDecimal importe;
}
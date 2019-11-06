package ar.com.ada.api.billeteravirtual.models.response;

import java.math.BigDecimal;

/**
 * MovimientoResponse
 */
public class MovimientoResponse {

    public boolean isOk = false;
    public String message = "";
    public int billeteraId;
    public String moneda;
    public int movimientoId;
    public BigDecimal saldo;
    
}
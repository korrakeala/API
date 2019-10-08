package ar.com.ada.api.billeteravirtual.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.billeteravirtual.excepciones.*;
import ar.com.ada.api.billeteravirtual.models.request.*;
import ar.com.ada.api.billeteravirtual.models.response.*;
import ar.com.ada.api.billeteravirtual.services.*;
/**
 * BilleteraController
 */
@RestController
public class BilleteraController {

    @Autowired
    BilleteraService bs;

    @Autowired
    MovimientoService ms;

    @Autowired
    CuentaService cs;

    @PostMapping("billeteras/movimientos")
    public MovimientoResponse postAgregarPlata(@RequestBody MovimientoRequest req) throws CuentaPorMonedaException {
        MovimientoResponse r = new MovimientoResponse();
        
        int movimientoId = ms.Movimiento(req.billeteraId, req.moneda, req.concepto, req.importe, req.tipo);

        r.isOk = true;
        r.message = "Transacción exitosa.";
        r.billeteraId = req.billeteraId;
        r.moneda = req.moneda;
        r.movimientoId = movimientoId;
        return r;

    }

    @GetMapping("billeteras/saldos")
    public SaldoResponse getConsultarSaldo(@RequestBody SaldoRequest req){
        SaldoResponse r = new SaldoResponse();

        double saldo = bs.consultarSaldo(req.billeteraId, req.moneda);

        r.isOk = true;
        r.billeteraId = req.billeteraId;
        r.moneda = req.moneda;
        r.saldo = saldo;
        return r;
    }
}
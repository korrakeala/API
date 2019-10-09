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

    @PostMapping("billeteras/depositos")
    public MovimientoResponse postAgregarPlata(@RequestBody MovimientoRequest req) throws CuentaPorMonedaException {
        MovimientoResponse r = new MovimientoResponse();
        
        int movimientoId = ms.depositar(req.billeteraId, req.moneda, req.concepto, req.importe, req.tipo);

        r.isOk = true;
        r.message = "Transacción exitosa.";
        r.billeteraId = req.billeteraId;
        r.moneda = req.moneda;
        //cómo hago para que me devuelva movimiendoId?
        r.movimientoId = movimientoId;
        return r;

    }

    @GetMapping("billeteras/saldos/{id}")
    public double getConsultarSaldo(@PathVariable int billeteraId, String moneda){

        double saldo = bs.consultarSaldo(billeteraId, moneda);

        return saldo;
    }

    @PostMapping("billeteras/transferencias")
    public TransferenciaResponse postTransferencia(@RequestBody TransferenciaRequest req){
        TransferenciaResponse r = new TransferenciaResponse();

        int operacionId = bs.transferir(req.billeteraIdOrig, req.billeteraIdDest, req.importe);

        r.isOk = true;
        r.billeteraIdOrig = req.billeteraIdOrig;
        r.billeteraIdDest = req.billeteraIdDest;
        r.importe = req.importe;
        r.operacionId = operacionId;
        return r; //cómo devolver los ids de movimientos? conviene?
    }
}
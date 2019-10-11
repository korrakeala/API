package ar.com.ada.api.billeteravirtual.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.billeteravirtual.entities.*;
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

    @PostMapping("billeteras/{id}/depositos")
    public MovimientoResponse postAgregarPlata(@PathVariable int id, @RequestBody MovimientoRequest req) throws CuentaPorMonedaException {
        MovimientoResponse r = new MovimientoResponse();

        int movimientoId = ms.depositarExtraer(id, req.moneda, req.concepto, req.importe, "Entrada");

        r.isOk = true;
        r.message = "Transacción exitosa.";
        r.billeteraId = id;
        r.moneda = req.moneda;
        r.movimientoId = movimientoId;
        return r;

    }

    @PostMapping("billeteras/{id}/extracciones")
    public MovimientoResponse postExtraerPlata(@PathVariable int id, @RequestBody MovimientoRequest req) throws CuentaPorMonedaException {
        MovimientoResponse r = new MovimientoResponse();
        // req.importe en negativo para respetar lógica de entradas positivas y salidas
        // negativas
        int movimientoId = ms.depositarExtraer(id, req.moneda, req.concepto, -req.importe, "Salida");

        r.isOk = true;
        r.message = "Transacción exitosa.";
        r.billeteraId = id;
        r.moneda = req.moneda;
        r.movimientoId = movimientoId;
        return r;

    }

    @GetMapping("/billeteras/{id}/saldos")
    public ArrayList<SaldoResponse> getSaldos(@PathVariable int id) {
        Billetera b = bs.buscarPorId(id);
        ArrayList<SaldoResponse> ls = new ArrayList<>();
        for (Cuenta c : b.getCuentas()) {
            SaldoResponse r = new SaldoResponse();
            r.billeteraId = id;
            r.moneda = c.getMoneda();
            r.saldo = c.getSaldo();
            ls.add(r);
        }
        return ls;
    }

    @GetMapping("billeteras/{id}/saldos/{moneda}")
    public SaldoResponse getConsultarSaldo(@PathVariable int id, @PathVariable String moneda)
            throws CuentaPorMonedaException {
        SaldoResponse r = new SaldoResponse();

        double saldo = bs.consultarSaldo(id, moneda);

        r.billeteraId = id;
        r.moneda = moneda;
        r.saldo = saldo;
        return r;
    }

    @PostMapping("billeteras/{id}/transferencias/{id2}")
    public TransferenciaResponse postTransferencia(@PathVariable int id, @PathVariable int id2,
            @RequestBody TransferenciaRequest req) throws CuentaPorMonedaException {
        TransferenciaResponse r = new TransferenciaResponse();

        int operacionId = bs.transferir(id, id2, req.importe, req.concepto);

        r.isOk = true;
        r.billeteraIdOrig = id;
        r.billeteraIdDest = id2;
        r.importe = req.importe;
        r.concepto = req.concepto;
        r.operacionId = operacionId;
        return r; // cómo devolver los ids de movimientos? conviene?
    }

    @PostMapping("billeteras/{id}/cuentas/{moneda}")
    public CrearCuentaResponse postCuenta(@PathVariable int id, @PathVariable String moneda)
            throws CuentaPorMonedaException {
        CrearCuentaResponse r = new CrearCuentaResponse();

        Billetera b = bs.buscarPorId(id);
        Cuenta c = new Cuenta(b, moneda);
        bs.save(b);
        ms.depositarExtraer(id, moneda, "Carga inicial", 50, "Entrada");
        bs.save(b);

        r.billeteraId = id;
        r.message = "Cuenta en "+ moneda + " generada con éxito.";
        r.isOk = true;
        return r;

    }
}
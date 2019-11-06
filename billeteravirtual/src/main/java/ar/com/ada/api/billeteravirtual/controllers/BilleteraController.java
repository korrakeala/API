package ar.com.ada.api.billeteravirtual.controllers;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.excepciones.*;
import ar.com.ada.api.billeteravirtual.models.request.*;
import ar.com.ada.api.billeteravirtual.models.response.*;
import ar.com.ada.api.billeteravirtual.services.*;
import ar.com.ada.api.billeteravirtual.sistema.comms.EmailService;

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

    @Autowired
    UsuarioService us;

    @Autowired
    EmailService emailService;

    @PostMapping("billeteras/{id}/depositos")
    public MovimientoResponse postAgregarPlata(@PathVariable int id, @RequestBody MovimientoRequest req)
            throws CuentaPorMonedaException {
        MovimientoResponse r = new MovimientoResponse();

        Movimiento m = ms.depositarExtraer(id, req.moneda, req.concepto, req.importe, "Entrada");
       
        r.isOk = true;
        r.message = "Transacción exitosa.";
        r.billeteraId = id;
        r.moneda = req.moneda;
        r.movimientoId = m.getMovimientoId();
        r.saldo = m.getCuenta().getSaldo();
        return r;

    }

    @PostMapping("billeteras/{id}/extracciones")
    public MovimientoResponse postExtraerPlata(@PathVariable int id, @RequestBody MovimientoRequest req)
            throws CuentaPorMonedaException {
        MovimientoResponse r = new MovimientoResponse();
        // req.importe en negativo para respetar lógica de entradas positivas y salidas
        // negativas
        Movimiento m = ms.depositarExtraer(id, req.moneda, req.concepto, req.importe.negate(), "Salida");

        r.isOk = true;
        r.message = "Transacción exitosa.";
        r.billeteraId = id;
        r.moneda = req.moneda;
        r.movimientoId = m.getMovimientoId();
        r.saldo = m.getCuenta().getSaldo();
        return r;

    }

    @GetMapping("/billeteras/{id}/saldos")
    public ArrayList<SaldoResponse> getSaldos(Authentication authentication, Principal principal,
            @PathVariable int id) throws UsuarioNoAutorizadoException {
        Billetera b = bs.buscarPorId(id);
        if (b.getPersona().getUsuario().getUserName().equals(principal.getName())) {
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
        throw new UsuarioNoAutorizadoException("El usuario no posee permisos para consultar esa billetera.");
    }

    @GetMapping("billeteras/{id}/saldos/{moneda}")
    public SaldoResponse getConsultarSaldo(@PathVariable int id, @PathVariable String moneda)
            throws CuentaPorMonedaException {
        SaldoResponse r = new SaldoResponse();

        BigDecimal saldo = bs.consultarSaldo(id, moneda);

        r.billeteraId = id;
        r.moneda = moneda;
        r.saldo = saldo;
        return r;
    }

    @PostMapping("billeteras/{id}/transferencias")
    public TransferenciaResponse postTransferencia(@PathVariable int id, @RequestBody TransferenciaRequest req)
            throws CuentaPorMonedaException {
        TransferenciaResponse r = new TransferenciaResponse();

        int operacionId = bs.transferir(id, req.emailUsuarioDest, req.importe, req.concepto);

        Usuario uDest = us.buscarPorEmail(req.emailUsuarioDest);

        r.isOk = true;
        r.billeteraIdOrig = id;
        r.billeteraIdDest = uDest.getPersona().getBilletera().getBilleteraId();
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
        ms.depositarExtraer(id, moneda, "Carga inicial", (new BigDecimal(50)), "Entrada");
        bs.save(b);

        r.billeteraId = id;
        r.message = "Cuenta en " + moneda + " generada con éxito.";
        r.isOk = true;
        return r;

    }
}
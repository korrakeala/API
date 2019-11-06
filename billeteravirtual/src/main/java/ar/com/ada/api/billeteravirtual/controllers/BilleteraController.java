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
    EmailService es;

    @Autowired
    PersonaService ps;

    @PostMapping("billeteras/{id}/depositos")
    public MovimientoResponse postAgregarPlata(Principal principal, @PathVariable int id,
            @RequestBody MovimientoRequest req) throws CuentaPorMonedaException, UsuarioNoAutorizadoException {
        MovimientoResponse r = new MovimientoResponse();
        Usuario u = us.buscarPorUserName(principal.getName());
        Billetera b = bs.buscarPorId(id);

        if (b.getPersona().getUsuario().getUserName().equals(principal.getName())
                || u.getTipoUsuario().equals("Admin")) {
            Movimiento m = ms.depositarExtraer(id, req.moneda, req.concepto, req.importe, "Entrada");

            r.isOk = true;
            r.message = "Transacción exitosa.";
            r.billeteraId = id;
            r.moneda = req.moneda;
            r.movimientoId = m.getMovimientoId();
            r.saldo = m.getCuenta().getSaldo();
            return r;
        } else {
            throw new UsuarioNoAutorizadoException("El usuario no posee permisos para operar con esa billetera.");
        }

    }

    @PostMapping("billeteras/{id}/extracciones")
    public MovimientoResponse postExtraerPlata(Principal principal, @PathVariable int id,
            @RequestBody MovimientoRequest req) throws CuentaPorMonedaException, UsuarioNoAutorizadoException {
        MovimientoResponse r = new MovimientoResponse();
        // req.importe en negativo para respetar lógica de entradas positivas y salidas
        // negativas
        Usuario u = us.buscarPorUserName(principal.getName());
        Billetera b = bs.buscarPorId(id);

        if (b.getPersona().getUsuario().getUserName().equals(principal.getName())
                || u.getTipoUsuario().equals("Admin")) {
            Movimiento m = ms.depositarExtraer(id, req.moneda, req.concepto, req.importe.negate(), "Salida");

            r.isOk = true;
            r.message = "Transacción exitosa.";
            r.billeteraId = id;
            r.moneda = req.moneda;
            r.movimientoId = m.getMovimientoId();
            r.saldo = m.getCuenta().getSaldo();
            return r;
        } else {
            throw new UsuarioNoAutorizadoException("El usuario no posee permisos para operar con esa billetera.");
        }

    }

    @GetMapping("/billeteras/{id}/saldos")
    public ArrayList<SaldoResponse> getSaldos(Principal principal, @PathVariable int id)
            throws UsuarioNoAutorizadoException {
        Usuario u = us.buscarPorUserName(principal.getName());
        Billetera b = bs.buscarPorId(id);

        if (b.getPersona().getUsuario().getUserName().equals(principal.getName())
                || u.getTipoUsuario().equals("Admin")) {
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
    public SaldoResponse getConsultarSaldo(Principal principal, @PathVariable int id, @PathVariable String moneda)
            throws CuentaPorMonedaException, UsuarioNoAutorizadoException {
        SaldoResponse r = new SaldoResponse();

        Usuario u = us.buscarPorUserName(principal.getName());
        Billetera b = bs.buscarPorId(id);

        if (b.getPersona().getUsuario().getUserName().equals(principal.getName())
                || u.getTipoUsuario().equals("Admin")) {
            BigDecimal saldo = bs.consultarSaldo(id, moneda);

            r.billeteraId = id;
            r.moneda = moneda;
            r.saldo = saldo;
            return r;
        } else {
            throw new UsuarioNoAutorizadoException("El usuario no posee permisos para consultar esa billetera.");
        }

    }

    @PostMapping("billeteras/{id}/transferencias")
    public TransferenciaResponse postTransferencia(Principal principal, @PathVariable int id,
            @RequestBody TransferenciaRequest req) throws CuentaPorMonedaException, UsuarioNoAutorizadoException {
        TransferenciaResponse r = new TransferenciaResponse();

        Usuario u = us.buscarPorUserName(principal.getName());
        Billetera b = bs.buscarPorId(id);

        if (b.getPersona().getUsuario().getUserName().equals(principal.getName())
                || u.getTipoUsuario().equals("Admin")) {
            int operacionId = bs.transferir(id, req.emailUsuarioDest, req.importe, req.concepto);
            Usuario uDest = us.buscarPorEmail(req.emailUsuarioDest);

            r.isOk = true;
            r.billeteraIdOrig = id;
            r.billeteraIdDest = uDest.getPersona().getBilletera().getBilleteraId();
            r.importe = req.importe;
            r.concepto = req.concepto;
            r.operacionId = operacionId;
            return r;
        } else {
            throw new UsuarioNoAutorizadoException("El usuario no posee permisos para operar con esa billetera.");
        }

        // cómo devolver los ids de movimientos? conviene?
    }

    @PostMapping("billeteras/{id}/cuentas/{moneda}")
    public CrearCuentaResponse postCuenta(Principal principal, @PathVariable int id, @PathVariable String moneda)
            throws UsuarioNoAutorizadoException {
        CrearCuentaResponse r = new CrearCuentaResponse();

        Usuario u = us.buscarPorUserName(principal.getName());
        Billetera b = bs.buscarPorId(id);

        if (b.getPersona().getUsuario().getUserName().equals(principal.getName())
                || u.getTipoUsuario().equals("Admin")) {
            cs.crearCuenta(id, moneda);

            r.billeteraId = id;
            r.message = "Cuenta en " + moneda + " generada con éxito.";
            r.isOk = true;
            return r;
        } else {
            throw new UsuarioNoAutorizadoException("El usuario no posee permisos para operar con esa billetera.");
        }

    }

    @GetMapping("billeteras/")
    public List<Billetera> getBilleteras(Principal principal) throws UsuarioNoAutorizadoException {
        Usuario u = us.buscarPorUserName(principal.getName());

        if (u.getTipoUsuario().equals("Admin")) {
            List<Billetera> lb = bs.getBilleteras();
            return lb;
        }
        throw new UsuarioNoAutorizadoException("El usuario no posee autorización para realizar esta acción.");
    }
}
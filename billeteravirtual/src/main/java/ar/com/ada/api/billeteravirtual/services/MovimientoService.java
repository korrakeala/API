package ar.com.ada.api.billeteravirtual.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.excepciones.CuentaPorMonedaException;
import ar.com.ada.api.billeteravirtual.repo.MovimientoRepository;

/**
 * MovimientoService
 */
@Service
public class MovimientoService {

    @Autowired
    MovimientoRepository repo;

    @Autowired
    BilleteraService bs;

    @Autowired
    CuentaService cs;

    /**
     * Método para crear movimientos que viene desde fuera de la API, como cuenta
     * bancaria o tarjeta de crédito.
     * 
     * @param c
     * @param u
     * @param moneda
     * @param concepto por ejemplo "Carga inicial"
     * @param importe
     * @param tipo     "Entrada" o "Salida"
     * @throws CuentaPorMonedaException
     */
    public int Movimiento(int billeteraId, String moneda, String concepto, double importe, String tipo)
            throws CuentaPorMonedaException {
        Billetera b = bs.buscarPorId(billeteraId);
        Cuenta c = cs.getCuentaPorMoneda(billeteraId, moneda);
        Movimiento m = new Movimiento();
        Date f = new Date();
        m.setConcepto(concepto);
        m.setImporte(importe);
        m.setTipo(tipo);
        m.setFechaMov(f);
        m.setCuentaOrigenId(c.getCuentaId());
        m.setCuentaDestinoId(c.getCuentaId());
        m.setaUsuarioId(c.getBilletera().getPersona().getUsuario().getUsuarioId());
        m.setDeUsuarioId(c.getBilletera().getPersona().getUsuario().getUsuarioId());
        if (m.getTipo().equals("Entrada")) {
            c.setSaldo(c.getSaldo() + m.getImporte());
            c.setSaldoDisponible(c.getSaldo());
        } else {
            c.setSaldo(c.getSaldo() - m.getImporte());
            c.setSaldoDisponible(c.getSaldo());
        }
        m.setCuenta(c);
        bs.save(b);
        //RequestResponse no devuelve los ids, qué caranchos pasa?
        return m.getMovimientoId();
    }
}
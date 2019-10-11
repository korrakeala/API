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

    public void save(Movimiento m) {
        repo.save(m);
    }

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
    public int depositarExtraer(int billeteraId, String moneda, String concepto, double importe, String tipo)
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
        // if (m.getTipo().equals("Entrada")) {
        c.setSaldo(c.getSaldo() + m.getImporte());
        c.setSaldoDisponible(c.getSaldo());
        // } else {
        // if (m.getTipo().equals("Salida")){
        // c.setSaldo(c.getSaldo() - m.getImporte());
        // c.setSaldoDisponible(c.getSaldo());
        // }
        // }
        m.setCuenta(c);
        repo.save(m); // Debería devolver movimientoId simplemente con bs.save(b);, pero no sucede.
                      // Agregando esta línea, funciona.
        bs.save(b);
        return m.getMovimientoId();
    }

    /**
     * Hace una transferencia entre cuentas principales (de índice 0)
     * 
     * @param importe
     * @param bOrigen
     * @param bDestino
     * @throws CuentaPorMonedaException
     */
    public int movimientoTransferir(Billetera b, double importe, Cuenta cuentaDesde, Cuenta cuentaHasta,
            String concepto) throws CuentaPorMonedaException {
        if (cuentaDesde.getMoneda().equals(cuentaHasta.getMoneda())) {
            Movimiento m = new Movimiento();
            m.setImporte(importe);
            m.setCuenta(b.getCuenta(0));
            Date f = new Date();
            m.setConcepto(concepto);
            m.setTipo("Transferencia");
            m.setFechaMov(f);
            m.setCuentaOrigenId(cuentaDesde.getCuentaId());
            m.setCuentaDestinoId(cuentaHasta.getCuentaId());
            m.setDeUsuarioId(cuentaDesde.getUsuario().getUsuarioId());
            m.setaUsuarioId(cuentaHasta.getUsuario().getUsuarioId());
            cuentaDesde.setSaldo(cuentaDesde.getSaldo() + importe);
            cuentaDesde.setSaldoDisponible(cuentaDesde.getSaldoDisponible() + importe);
            repo.save(m);
            bs.save(b);
            return m.getMovimientoId();
        } else {
            throw new CuentaPorMonedaException("Las cuentas deben ser en la misma moneda.");
        }

    }
}
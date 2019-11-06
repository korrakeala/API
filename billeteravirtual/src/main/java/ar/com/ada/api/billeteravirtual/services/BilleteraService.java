package ar.com.ada.api.billeteravirtual.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.excepciones.CuentaPorMonedaException;
import ar.com.ada.api.billeteravirtual.repo.BilleteraRepository;
import ar.com.ada.api.billeteravirtual.sistema.comms.EmailService;

/**
 * BilleteraService
 */
@Service
public class BilleteraService {

    @Autowired
    BilleteraRepository repo;

    @Autowired
    MovimientoService ms;

    @Autowired
    CuentaService cs;

    @Autowired
    UsuarioService us;

    @Autowired
    EmailService emailService;

    public void save(Billetera b) {
        repo.save(b);
    }

    public List<Billetera> getBilleteras() {

        return repo.findAll();
    }

    public Billetera buscarPorId(int id) {

        Optional<Billetera> b = repo.findById(id);

        if (b.isPresent()) {
            return b.get();
        }
        return null;
    }

    public Billetera buscarPorPersona(Persona p) {

        return repo.findByPersona(p);
    }

    public BigDecimal consultarSaldo(int billeteraId, String moneda) throws CuentaPorMonedaException {
        Billetera b = this.buscarPorId(billeteraId);
        Cuenta c = cs.getCuentaPorMoneda(b.getBilleteraId(), moneda);

        return c.getSaldo();
    }

    public int transferir(int idOrig, String emailUsuarioDest, BigDecimal importe, String concepto)
            throws CuentaPorMonedaException {

        Usuario uDest = us.buscarPorEmail(emailUsuarioDest);

        Billetera b1 = this.buscarPorId(idOrig);
        Billetera b2 = this.buscarPorId(uDest.getPersona().getBilletera().getBilleteraId());
        // Acá puede quedar un importe negativo, o adaptarlo para que sean todos
        // positivos
        int mov = ms.movimientoTransferir(b1, importe.negate(), b1.getCuenta(0), b2.getCuenta(0), concepto);
        ms.movimientoTransferir(b2, importe, b2.getCuenta(0), b1.getCuenta(0), concepto);
        repo.save(b1);
        repo.save(b2);

        emailService.SendEmail(b1.getPersona().getUsuario().getUserEmail(), "Aviso de transferencia",
                "Hola " + b1.getPersona().getNombre() + "\nRegistramos una transferencia a "
                        + b2.getPersona().getNombre() + " desde tu cuenta en " + b1.getCuenta(0).getMoneda() + ".\n"
                        + "Si no fuiste vos, contactanos a la brevedad.");

        emailService.SendEmail(b2.getPersona().getUsuario().getUserEmail(), "Aviso de transferencia",
                "Hola " + b2.getPersona().getNombre() + "\nRecibiste una transferencia de "
                        + b1.getPersona().getNombre() + " a tu cuenta en " + b2.getCuenta(0).getMoneda() + ".\n"
                        + "Saluditos!");

        return mov;
    }

    /*
     * cómo devuelvo una lista de cuentas y saldos? public Cuenta getSaldos(int
     * billeteraId){ Billetera b = this.buscarPorId(billeteraId); Cuenta c; for (int
     * i = 0; i < b.getCuentas().size(); i++) { c = b.getCuentas().get(i); } return
     * null; }
     */
}
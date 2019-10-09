package ar.com.ada.api.billeteravirtual.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.repo.BilleteraRepository;

/**
 * BilleteraService
 */
@Service
public class BilleteraService {

    @Autowired
    BilleteraRepository repo;

    @Autowired
    MovimientoService ms;

    public void save(Billetera b){
        repo.save(b);
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

    public double consultarSaldo(int billeteraId, String moneda) {
        Billetera b = this.buscarPorId(billeteraId);
        double s = 0;
        for (Cuenta c : b.getCuentas()) {
            if (c.getMoneda().equals(moneda)){
                s = c.getSaldo();
            }
        }
        return s;
    }

    public int transferir(int billeteraIdOrig, int billeteraIdDest, double importe) {
        Billetera b1 = this.buscarPorId(billeteraIdOrig);
        Billetera b2 = this.buscarPorId(billeteraIdDest);
        int mov = b1.movimientoTransferir(-importe, b1.getCuenta(0), b2.getCuenta(0));
        b2.movimientoTransferir(importe, b2.getCuenta(0), b1.getCuenta(0));
        repo.save(b1);
        repo.save(b2);
        return mov;
    }

    /* cómo devuelvo una lista de cuentas y saldos?
    public Cuenta getSaldos(int billeteraId){
        Billetera b = this.buscarPorId(billeteraId);
        Cuenta c;
        for (int i = 0; i < b.getCuentas().size(); i++) {
            c = b.getCuentas().get(i);
        }
        return null;
    }*/
}
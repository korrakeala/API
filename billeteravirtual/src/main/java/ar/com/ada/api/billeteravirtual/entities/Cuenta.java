package ar.com.ada.api.billeteravirtual.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.math.BigDecimal;
import java.util.*;

/**
 * Cuenta
 */

@Entity
@Table(name = "cuenta")
public class Cuenta {

    @Id
    @Column(name = "cuenta_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cuentaId;
    private String moneda; // monedaId?
    private BigDecimal saldo = new BigDecimal(0); // (balance)
    @Column(name = "saldo_disponible")
    private BigDecimal saldoDisponible = new BigDecimal(0);
    // private String nroCuenta; // (univoco)
    @ManyToOne
    @JoinColumn(name = "billetera_id", referencedColumnName = "billetera_id")
    @JsonIgnore
    private Billetera billetera;
    @OneToMany (mappedBy = "cuenta", cascade = CascadeType.ALL)
    @LazyCollection (LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Movimiento> movimientos = new ArrayList<Movimiento>(); // (puede necesitar tabla intermedia)

    void dineroPendiente() {

    }

    void ultimosMovimientos() {

    }

    void dineroIngresado() {

    }

    void dineroExtraido() {

    }

    public Cuenta(int cuentaId, String moneda) {
        this.cuentaId = cuentaId;
        this.moneda = moneda;
    }

    public Cuenta() {
    }

    public int getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(int cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Billetera getBilletera() {
        return billetera;
    }

    public void setBilletera(Billetera billetera) {
        this.billetera = billetera;
        this.billetera.getCuentas().add(this);
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    //falta poner límite para no tener saldos negativos
    public BigDecimal getSaldoDisponible() {
        return saldo;
    }

    public void setSaldoDisponible(BigDecimal saldoDisponible) {
        this.saldoDisponible = null;
    }

    // Adaptar para sacar el print del método.
    public Cuenta(Billetera b, String moneda) {

        //this.saldo = 100;
        this.moneda = moneda;
        this.billetera = b;
        b.getCuentas().add(this);

    }

    public Usuario getUsuario(){

        Usuario u = this.getBilletera().getPersona().getUsuario();
        return u;
        
    }

    
}
package ar.com.ada.api.billeteravirtual.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.repo.UsuarioRepository;
import ar.com.ada.api.billeteravirtual.security.Crypto;
import ar.com.ada.api.billeteravirtual.sistema.comms.EmailService;
import ar.com.ada.api.billeteravirtual.entities.*;
import ar.com.ada.api.billeteravirtual.excepciones.CuentaPorMonedaException;
import ar.com.ada.api.billeteravirtual.excepciones.PersonaEdadException;

/**
 * UsuarioService
 */
@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repo;

    @Autowired
    BilleteraService bs;

    @Autowired
    MovimientoService ms;

    @Autowired
    PersonaService ps;

    @Autowired
    EmailService emailService;

    public int alta(String fullName, String dni, String email, int edad, String password)
            throws PersonaEdadException, CuentaPorMonedaException {
        Persona p = new Persona();
        p.setNombre(fullName);
        p.setDni(dni);
        p.setEmail(email);
        p.setEdad(edad);

        Usuario u = new Usuario();
        u.setUserName(p.getEmail());
        u.setUserEmail(p.getEmail());

        String passwordEnTextoClaro;
        String passwordEncriptada;

        passwordEnTextoClaro = password;
        passwordEncriptada = Crypto.encrypt(passwordEnTextoClaro, u.getUserName());

        u.setPassword(passwordEncriptada);
        p.setUsuario(u);
        ps.save(p);

        Billetera b = new Billetera(p);
        Cuenta c = new Cuenta(b, "AR$");
        c.setBilletera(b);
        bs.save(b);

        emailService.SendEmail(u.getUserEmail(), "Bienvenido a la Billetera Virtual ADA!!!",
                "Hola " + p.getNombre()
                        + "\nBienvenido a este hermoso proyecto hecho por todas las alumnas de ADA Backend 8va Mañana\n"
                        + "Ademas te regalamos 100 pesitos");

        ms.depositarExtraer(b.getBilleteraId(), c.getMoneda(), "Carga inicial", (new BigDecimal(100)), "Entrada");

        return u.getUsuarioId();
    }

    public void save(Usuario u) {
        repo.save(u);
    }

    public List<Usuario> getUsuarios() {

        return repo.findAll();
    }

    public Usuario buscarPorUserName(String userName) {

        return repo.findByUserName(userName);
    }

    public Usuario buscarPorEmail(String email) {

        return repo.findByUserEmail(email);
    }

    public Usuario buscarPorId(int id) {

        Optional<Usuario> u = repo.findById(id);

        if (u.isPresent())
            return u.get();
        return null;
    }

    public void login(String username, String password) {

        Usuario u = repo.findByUserName(username);

        if (u == null || !u.getPassword().equals(Crypto.encrypt(password, u.getUserName()))) {

            throw new BadCredentialsException("Usuario o contraseña invalida");
        }

    }

}
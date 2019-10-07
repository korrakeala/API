package ar.com.ada.api.billeteravirtual.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.billeteravirtual.repo.UsuarioRepository;
import ar.com.ada.api.billeteravirtual.security.Crypto;
import ar.com.ada.api.billeteravirtual.entities.*;
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

    public int alta(String fullName, String dni, String email, int edad, String password, String moneda)
            throws PersonaEdadException {
        Persona p = new Persona();
        p.setNombre(fullName);
        p.setDni(dni);
        p.setEmail(email);
        p.setEdad(edad);
        
        Usuario u = new Usuario();
        u.setUserName(p.getEmail());

        String passwordEnTextoClaro;
        String passwordEncriptada;
        String passwordEnTextoClaroDesencriptado;

        passwordEnTextoClaro = password;
        passwordEncriptada = Crypto.encrypt(passwordEnTextoClaro, u.getUserName());
        passwordEnTextoClaroDesencriptado = Crypto.decrypt(passwordEncriptada, u.getUserName());

        u.setPassword(passwordEncriptada);
        p.setUsuario(u);
        repo.save(u);

        Billetera b = new Billetera(p);
        Cuenta c = new Cuenta(b, moneda);

        c.setBilletera(b);

        bs.save(b);

        return u.getUsuarioId();
    }

    public void save(Usuario u){
        repo.save(u);
    }
    
    public List<Usuario> getUsuarios() {

        return repo.findAll();
    }

    public Usuario buscarPorUserName(String userName) {

        return repo.findByUserName(userName);
    }

    public Usuario buscarPorEmail(String email) {

        return repo.findByEmail(email);
    }

    public Usuario buscarPorId(int id) {

        Optional<Usuario> u = repo.findById(id);

        if (u.isPresent())
            return u.get();
        return null;
    }
    

}
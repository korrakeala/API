package ar.com.ada.api.billeteravirtual.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.billeteravirtual.entities.Usuario;
import ar.com.ada.api.billeteravirtual.excepciones.CuentaPorMonedaException;
import ar.com.ada.api.billeteravirtual.excepciones.PersonaEdadException;
import ar.com.ada.api.billeteravirtual.models.request.LoginRequest;
import ar.com.ada.api.billeteravirtual.models.request.RegistrationRequest;
import ar.com.ada.api.billeteravirtual.models.response.RegistrationResponse;
import ar.com.ada.api.billeteravirtual.security.jwt.JWTTokenUtil;
import ar.com.ada.api.billeteravirtual.services.JWTUserDetailsService;
import ar.com.ada.api.billeteravirtual.services.UsuarioService;
import ar.com.ada.api.billeteravirtual.models.response.JwtResponse;

/**
 * AuthController
 */
@RestController
public class AuthController {

    @Autowired
    UsuarioService us;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @PostMapping("auth/register")
    public RegistrationResponse postRegisterUser(@RequestBody RegistrationRequest req)
            throws PersonaEdadException, CuentaPorMonedaException {
        RegistrationResponse r = new RegistrationResponse();
        //aca creamos la persona y el usuario a traves del service.
        // hacer método baja para no perder mails válidos en las pruebas

        Usuario usuarioCreado = us.altaUsuario(req.fullName, req.dni, req.email, req.edad, req.password);

        r.isOk = true;
        r.message = "Te registraste con éxito, guardá tu número de billetera porque después lo vas a necesitar!";
        r.usuarioId = usuarioCreado.getUsuarioId();
        r.billeteraId = usuarioCreado.getPersona().getBilletera().getBilleteraId();
        return r;
    }

    @PostMapping("auth/registeradmin")
    public RegistrationResponse postRegisterAdmin(@RequestBody RegistrationRequest req)
            throws PersonaEdadException, CuentaPorMonedaException {
        RegistrationResponse r = new RegistrationResponse();

        Usuario admin = us.altaAdmin(req.fullName, req.dni, req.email, req.edad, req.password);

        r.isOk = true;
        r.message = "Te registraste como Admin con éxito!";
        r.usuarioId = admin.getUsuarioId();
        return r;
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest)
            throws Exception {

        us.login(authenticationRequest.userName, authenticationRequest.password);

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.userName);

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    
}
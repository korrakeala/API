package ar.com.ada.mongo.netfly.controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.mongo.netfly.models.request.LoginRequest;
import ar.com.ada.mongo.netfly.models.request.RegistrationRequest;
import ar.com.ada.mongo.netfly.models.response.JwtResponse;
import ar.com.ada.mongo.netfly.models.response.RegistrationResponse;
import ar.com.ada.mongo.netfly.security.jwt.JWTTokenUtil;
import ar.com.ada.mongo.netfly.services.JWTUserDetailsService;
import ar.com.ada.mongo.netfly.services.UsuarioService;

@RestController
public class AuthController {

    @Autowired
    UsuarioService us;

    /*
     * @Autowired private AuthenticationManager authenticationManager;
     */
    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @PostMapping("auth/register")
    public RegistrationResponse postRegisterUser(@RequestBody RegistrationRequest req)  {
        RegistrationResponse r = new RegistrationResponse();
        // aca creamos la persona y el usuario a travez del service.

        ObjectId uId = us.crearUsuario(req.fullName, req.email, req.password);
        // revisar por qué no manda el mail

        r.isOk = true;
        r.message = "Te registraste con exitoooo";
        r.userId = uId;
        return r;

    }

    @PostMapping("auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest)
            throws Exception {

        us.login(authenticationRequest.username, authenticationRequest.password);

        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(authenticationRequest.username);

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

}
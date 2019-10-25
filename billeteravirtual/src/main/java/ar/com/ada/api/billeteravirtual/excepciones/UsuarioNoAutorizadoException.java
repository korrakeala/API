package ar.com.ada.api.billeteravirtual.excepciones;

/**
 * UsuarioNoAutorizadoException
 */
public class UsuarioNoAutorizadoException extends Exception {

    public UsuarioNoAutorizadoException(String mensaje){
        super(mensaje);
    }
}
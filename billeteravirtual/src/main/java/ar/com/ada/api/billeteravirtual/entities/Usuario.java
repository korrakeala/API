package ar.com.ada.api.billeteravirtual.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Usuario
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "usuario_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usuarioId;
    @Column(name = "username")
    private String userName;
    @JsonIgnore
    private String password;
    @Column(name = "email")
    private String userEmail;
    @OneToOne
    @JoinColumn(name= "persona_id", referencedColumnName = "persona_id")
    @JsonIgnore
    private Persona persona;
    @Column(name = "tipo_usuario")
    private String tipoUsuario;
    //@Column(name = "persona_id")
    //private int personaId;

    public Usuario (String userName, String password, String email, String tipoUsuario){
        this.userName = userName;
        this.password = password;
        this.userEmail = email;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(){

    }

    public Usuario (String password){
        this.password = password;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
   

    /*public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public Usuario(int personaId) {
        this.personaId = personaId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }*/



}
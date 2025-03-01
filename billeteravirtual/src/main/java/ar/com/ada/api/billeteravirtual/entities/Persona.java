package ar.com.ada.api.billeteravirtual.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.com.ada.api.billeteravirtual.excepciones.PersonaEdadException;


/**
 * Persona
 */
@Entity
@Table(name = "persona")
@NamedQuery(name = "Persona.findAllByNombreContiene", query = "FROM Persona WHERE nombre like CONCAT('%', ?1,'%')")
@NamedQuery(name = "Persona.findAllByNombreAndDNI", query = "SELECT p FROM Persona p WHERE p.nombre = ?1 AND p.dni = ?2")
@NamedQuery(name = "Persona.findByDniUltimosDig", query = "FROM Persona WHERE dni like CONCAT('%', ?1)")
@NamedNativeQuery(name = "Persona.findAllByNombreConAndEdad", query = "SELECT * FROM persona WHERE nombre like CONCAT('%', ?,'%') AND edad = ?", resultClass = Persona.class)
// Sólo está definido acá
public class Persona {

    @Id
    @Column(name = "persona_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personaId;
    private String nombre;
    private String dni;
    private int edad;
    private String email;
    @JsonIgnore
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Billetera billetera;
    //@Column(name = "billetera_id")
    //private int billeteraId;
    //@JoinColumn(name= "persona_id", referencedColumnName = "persona_id")
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Usuario usuario;

    public Persona(String nombre, String dni, int edad, String email) {
        this.nombre = nombre;
        this.dni = dni;
        this.edad = edad;
        this.email = email;
    }

    public Persona() {
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int pesonaId) {
        this.personaId = pesonaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) throws PersonaEdadException {
        if(edad < 18)
        {
            //no se ejecuta nada mas despues del throw
            throw new PersonaEdadException(this, "ocurrio un error con la edad");


        }
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public void setPersona(Persona p) {
	}

   /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.usuario.setPersona(this); //Vinculamos ambos objetos entre si
    }
    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    public Billetera getBilletera() {
        return billetera;
    }

    public void setBilletera(Billetera billetera) {
        this.billetera = billetera;
        this.billetera.setPersona(this);
    }

    
}
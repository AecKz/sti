package tigre.sti.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsuario;

	private String clave;

	private String usuario;

	//bi-directional many-to-one association to Incidencia
	@OneToMany(mappedBy="usuario1")
	private List<Incidencia> incidencias1;

	//bi-directional many-to-one association to Incidencia
	@OneToMany(mappedBy="usuario2")
	private List<Incidencia> incidencias2;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="idPersona")
	private Persona persona;

	//bi-directional many-to-one association to Rol
	@ManyToOne
	@JoinColumn(name="idRol")
	private Rol rol;

	public Usuario() {
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<Incidencia> getIncidencias1() {
		return this.incidencias1;
	}

	public void setIncidencias1(List<Incidencia> incidencias1) {
		this.incidencias1 = incidencias1;
	}

	public Incidencia addIncidencias1(Incidencia incidencias1) {
		getIncidencias1().add(incidencias1);
		incidencias1.setUsuario1(this);

		return incidencias1;
	}

	public Incidencia removeIncidencias1(Incidencia incidencias1) {
		getIncidencias1().remove(incidencias1);
		incidencias1.setUsuario1(null);

		return incidencias1;
	}

	public List<Incidencia> getIncidencias2() {
		return this.incidencias2;
	}

	public void setIncidencias2(List<Incidencia> incidencias2) {
		this.incidencias2 = incidencias2;
	}

	public Incidencia addIncidencias2(Incidencia incidencias2) {
		getIncidencias2().add(incidencias2);
		incidencias2.setUsuario2(this);

		return incidencias2;
	}

	public Incidencia removeIncidencias2(Incidencia incidencias2) {
		getIncidencias2().remove(incidencias2);
		incidencias2.setUsuario2(null);

		return incidencias2;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

}
package tigre.sti.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the incidencia database table.
 * 
 */
@Entity
@NamedQuery(name="Incidencia.findAll", query="SELECT i FROM Incidencia i")
public class Incidencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idIncidencia;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	private String observacion;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="idEstado")
	private Estado estado;

	//bi-directional many-to-one association to Servicio
	@ManyToOne
	@JoinColumn(name="idServicio")
	private Servicio servicio;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioResponsable")
	private Usuario usuario1;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioSolicitante")
	private Usuario usuario2;

	//bi-directional many-to-one association to Solucion
	@OneToMany(mappedBy="incidencia")
	private List<Solucion> solucions;

	public Incidencia() {
	}

	public int getIdIncidencia() {
		return this.idIncidencia;
	}

	public void setIdIncidencia(int idIncidencia) {
		this.idIncidencia = idIncidencia;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Servicio getServicio() {
		return this.servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Usuario getUsuario1() {
		return this.usuario1;
	}

	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}

	public Usuario getUsuario2() {
		return this.usuario2;
	}

	public void setUsuario2(Usuario usuario2) {
		this.usuario2 = usuario2;
	}

	public List<Solucion> getSolucions() {
		return this.solucions;
	}

	public void setSolucions(List<Solucion> solucions) {
		this.solucions = solucions;
	}

	public Solucion addSolucion(Solucion solucion) {
		getSolucions().add(solucion);
		solucion.setIncidencia(this);

		return solucion;
	}

	public Solucion removeSolucion(Solucion solucion) {
		getSolucions().remove(solucion);
		solucion.setIncidencia(null);

		return solucion;
	}

}
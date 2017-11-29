package tigre.sti.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the solucion database table.
 * 
 */
@Entity
@NamedQuery(name="Solucion.findAll", query="SELECT s FROM Solucion s")
public class Solucion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idSolucion;

	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	//bi-directional many-to-one association to Incidencia
	@ManyToOne
	@JoinColumn(name="idIncidencia")
	private Incidencia incidencia;

	public Solucion() {
	}

	public int getIdSolucion() {
		return this.idSolucion;
	}

	public void setIdSolucion(int idSolucion) {
		this.idSolucion = idSolucion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Incidencia getIncidencia() {
		return this.incidencia;
	}

	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}

}
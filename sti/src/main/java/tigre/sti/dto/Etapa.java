package tigre.sti.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the etapa database table.
 * 
 */
@Entity
@NamedQuery(name="Etapa.findAll", query="SELECT e FROM Etapa e")
public class Etapa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEtapa;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to Incidencia
	@OneToMany(mappedBy="etapa")
	private List<Incidencia> incidencias;

	public Etapa() {
	}

	public int getIdEtapa() {
		return this.idEtapa;
	}

	public void setIdEtapa(int idEtapa) {
		this.idEtapa = idEtapa;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Incidencia> getIncidencias() {
		return this.incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	public Incidencia addIncidencia(Incidencia incidencia) {
		getIncidencias().add(incidencia);
		incidencia.setEtapa(this);

		return incidencia;
	}

	public Incidencia removeIncidencia(Incidencia incidencia) {
		getIncidencias().remove(incidencia);
		incidencia.setEtapa(null);

		return incidencia;
	}

}
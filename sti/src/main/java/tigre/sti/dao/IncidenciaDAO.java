package tigre.sti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tigre.sti.dto.Estado;
import tigre.sti.dto.Incidencia;
import tigre.sti.dto.Usuario;
import tigre.sti.entitymanagerfactory.EntityManagerFactoryDAO;

public class IncidenciaDAO extends EntityManagerFactoryDAO {
	public Incidencia crear(Incidencia objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(objeto);
			em.flush();
			em.getTransaction().commit();

			return objeto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return objeto;
		} finally {
			em.close();
		}
	}

	public Incidencia editar(Incidencia objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(objeto);
			em.getTransaction().commit();
			return objeto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			em.getTransaction().rollback();
			return objeto;
		} finally {
			em.close();
		}
	}

	public Incidencia eliminar(Incidencia objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Incidencia EntidadToBeRemoved = em.getReference(Incidencia.class,
					objeto.getIdIncidencia());
			em.remove(EntidadToBeRemoved);
			em.getTransaction().commit();
			return objeto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return objeto;
		} finally {
			em.close();
		}
	}

	public List<Incidencia> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Incidencia> query = em.createQuery(
					"SELECT e FROM Incidencia e order by e.fecha", Incidencia.class);
			List<Incidencia> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Incidencia buscarPorId(int id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Incidencia incidencia = new Incidencia();
		try {
			TypedQuery<Incidencia> query = em.createQuery(
					"SELECT c FROM Incidencia c where c.idIncidencia = :idIncidencia ", Incidencia.class)
					.setParameter("idIncidencia", id);
			List<Incidencia> results = query.getResultList();
			incidencia = results.get(0);
			return incidencia;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return incidencia;
		} finally {
			em.close();
		}
	}
	public List<Incidencia> buscarPorEstado(Estado estado) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Incidencia> query = em.createQuery(
					"SELECT c FROM Incidencia c "
					+ "JOIN FETCH c.estado e  "
					+ "JOIN FETCH c.usuario1 u1  "
					+ "JOIN FETCH c.usuario2 u2  "
					+ "JOIN FETCH c.servicio s  "
					+ "JOIN FETCH c.etapa et  "
					+ "JOIN FETCH c.prioridad p "
					+ "where c.estado= :estado", Incidencia.class)
					.setParameter("estado", estado);
			List<Incidencia> results = query.getResultList();			
			return results;		
		} finally {
			em.close();
		}
	}
	
	public List<Incidencia> buscarPorUsuarioSolicitante(Usuario usuario, int idEstado) {
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscarPorId(idEstado);
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Incidencia> query = em.createQuery(
					"SELECT c FROM Incidencia c JOIN FETCH c.usuario2 p  where c.usuario2= :usuario2 and c.estado = :estado", Incidencia.class)
					.setParameter("usuario2", usuario).setParameter("estado", estado);
			List<Incidencia> results = query.getResultList();			
			return results;		
		} finally {
			em.close();
		}
	}
	
	public List<Incidencia> buscarPorUsuarioResponsable(Usuario usuario, int idEstado) {
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscarPorId(idEstado);
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Incidencia> query = em.createQuery(
					"SELECT c FROM Incidencia c JOIN FETCH c.usuario1 p  where c.usuario1= :usuario1 and c.estado = :estado", Incidencia.class)
					.setParameter("usuario1", usuario).setParameter("estado", estado);
			List<Incidencia> results = query.getResultList();			
			return results;		
		} finally {
			em.close();
		}
	}

}

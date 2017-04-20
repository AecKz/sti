package tigre.sti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tigre.sti.dto.Incidencia;
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
					"SELECT e FROM Incidencia e order by e.idIncidencia", Incidencia.class);
			List<Incidencia> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Incidencia buscarPorId(String id) {
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

}

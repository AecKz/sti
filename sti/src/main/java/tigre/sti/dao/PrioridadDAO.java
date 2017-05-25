package tigre.sti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tigre.sti.dto.Prioridad;
import tigre.sti.entitymanagerfactory.EntityManagerFactoryDAO;

public class PrioridadDAO extends EntityManagerFactoryDAO {
	public Prioridad crear(Prioridad objeto) {
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

	public Prioridad editar(Prioridad objeto) {
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

	public Prioridad eliminar(Prioridad objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Prioridad EntidadToBeRemoved = em.getReference(Prioridad.class, objeto.getIdPrioridad());
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

	public List<Prioridad> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Prioridad> query = em.createQuery("SELECT e FROM Prioridad e order by e.nombre", Prioridad.class);
			List<Prioridad> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Prioridad buscarPorId(int id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Prioridad prioridad = new Prioridad();
		try {
			TypedQuery<Prioridad> query = em
					.createQuery("SELECT c FROM Prioridad c where c.idPrioridad = :idPrioridad ", Prioridad.class)
					.setParameter("idPrioridad", id);
			List<Prioridad> results = query.getResultList();
			prioridad = results.get(0);
			return prioridad;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return prioridad;
		} finally {
			em.close();
		}
	}
}

package tigre.sti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tigre.sti.dto.Solucion;
import tigre.sti.entitymanagerfactory.EntityManagerFactoryDAO;

public class SolucionDAO extends EntityManagerFactoryDAO {
	public Solucion crear(Solucion objeto) {
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

	public Solucion editar(Solucion objeto) {
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

	public Solucion eliminar(Solucion objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Solucion EntidadToBeRemoved = em.getReference(Solucion.class, objeto.getIdSolucion());
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

	public List<Solucion> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Solucion> query = em.createQuery("SELECT e FROM Solucion e order by e.fecha", Solucion.class);
			List<Solucion> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Solucion buscarPorId(int id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Solucion solucion = new Solucion();
		try {
			TypedQuery<Solucion> query = em
					.createQuery("SELECT c FROM Solucion c where c.idSolucion = :idSolucion ", Solucion.class)
					.setParameter("idSolucion", id);
			List<Solucion> results = query.getResultList();
			solucion = results.get(0);
			return solucion;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return solucion;
		} finally {
			em.close();
		}
	}
}

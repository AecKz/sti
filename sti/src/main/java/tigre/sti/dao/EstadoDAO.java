package tigre.sti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tigre.sti.dto.Estado;
import tigre.sti.entitymanagerfactory.EntityManagerFactoryDAO;

public class EstadoDAO extends EntityManagerFactoryDAO {
	public Estado crear(Estado objeto) {
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

	public Estado editar(Estado objeto) {
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

	public Estado eliminar(Estado objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Estado EntidadToBeRemoved = em.getReference(Estado.class, objeto.getIdEstado());
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

	public List<Estado> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Estado> query = em.createQuery("SELECT e FROM Estado e order by e.nombre", Estado.class);
			List<Estado> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Estado buscarPorId(int id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Estado estado = new Estado();
		try {
			TypedQuery<Estado> query = em
					.createQuery("SELECT c FROM Estado c where c.idEstado = :idEstado ", Estado.class)
					.setParameter("idEstado", id);
			List<Estado> results = query.getResultList();
			estado = results.get(0);
			return estado;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return estado;
		} finally {
			em.close();
		}
	}
}

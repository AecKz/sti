package tigre.sti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tigre.sti.entitymanagerfactory.EntityManagerFactoryDAO;
import tigre.sti.dto.Servicio;

public class ServicioDAO extends EntityManagerFactoryDAO {
	public Servicio crear(Servicio objeto) {
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

	public Servicio editar(Servicio objeto) {
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

	public Servicio eliminar(Servicio objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Servicio EntidadToBeRemoved = em.getReference(Servicio.class, objeto.getIdServicio());
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

	public List<Servicio> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Servicio> query = em.createQuery("SELECT e FROM Servicio e order by e.nombre", Servicio.class);
			List<Servicio> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Servicio buscarPorId(String id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Servicio servicio = new Servicio();
		try {
			TypedQuery<Servicio> query = em
					.createQuery("SELECT c FROM Servicio c where c.idServicio = :idServicio ", Servicio.class)
					.setParameter("idServicio", id);
			List<Servicio> results = query.getResultList();
			servicio = results.get(0);
			return servicio;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return servicio;
		} finally {
			em.close();
		}
	}

	/**
	 * Metodo para buscar el objeto Servicio a partir del idCategoria
	 * 
	 * @param idCategoria
	 * @return objeto Servicio
	 */
	public List<Servicio> buscarPorIdCategoria(int idCategoria) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Servicio> query = em
					.createQuery("SELECT u FROM Servicio u " + "JOIN FETCH u.categoria p "
							+ "where p.idCategoria = :idCategoria and u.servicio is null", Servicio.class)
					.setParameter("idCategoria", idCategoria);
			List<Servicio> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

}

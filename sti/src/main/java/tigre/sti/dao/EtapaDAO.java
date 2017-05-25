package tigre.sti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tigre.sti.dto.Etapa;
import tigre.sti.entitymanagerfactory.EntityManagerFactoryDAO;

public class EtapaDAO extends EntityManagerFactoryDAO {
	public Etapa crear(Etapa objeto) {
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

	public Etapa editar(Etapa objeto) {
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

	public Etapa eliminar(Etapa objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Etapa EntidadToBeRemoved = em.getReference(Etapa.class, objeto.getIdEtapa());
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

	public List<Etapa> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Etapa> query = em.createQuery("SELECT e FROM Etapa e order by e.nombre", Etapa.class);
			List<Etapa> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Etapa buscarPorId(int id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Etapa etapa = new Etapa();
		try {
			TypedQuery<Etapa> query = em
					.createQuery("SELECT c FROM Etapa c where c.idEtapa = :idEtapa ", Etapa.class)
					.setParameter("idEtapa", id);
			List<Etapa> results = query.getResultList();
			etapa = results.get(0);
			return etapa;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return etapa;
		} finally {
			em.close();
		}
	}
}

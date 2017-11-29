package tigre.sti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tigre.sti.dto.Rol;
import tigre.sti.entitymanagerfactory.EntityManagerFactoryDAO;

public class RolDAO extends EntityManagerFactoryDAO {
	public Rol crear(Rol objeto) {
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

	public Rol editar(Rol objeto) {
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

	public Rol eliminar(Rol objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Rol EntidadToBeRemoved = em.getReference(Rol.class, objeto.getIdRol());
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

	public List<Rol> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Rol> query = em.createQuery("SELECT e FROM Rol e order by e.rol", Rol.class);
			List<Rol> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Rol buscarPorId(int id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Rol rol = new Rol();
		try {
			TypedQuery<Rol> query = em
					.createQuery("SELECT c FROM Rol c where c.idRol = :idRol ", Rol.class)
					.setParameter("idRol", id);
			List<Rol> results = query.getResultList();
			rol = results.get(0);
			return rol;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return rol;
		} finally {
			em.close();
		}
	}
	public Rol buscarPorNombre(String nombreRol) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Rol rol = new Rol();
		try {
			TypedQuery<Rol> query = em
					.createQuery("SELECT c FROM Rol c where c.rol = :rol ", Rol.class)
					.setParameter("rol", nombreRol);
			List<Rol> results = query.getResultList();
			rol = results.get(0);
			return rol;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return rol;
		} finally {
			em.close();
		}
	}
}

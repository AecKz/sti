package tigre.sti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tigre.sti.entitymanagerfactory.EntityManagerFactoryDAO;
import tigre.sti.dto.Categoria;

public class CategoriaDAO extends EntityManagerFactoryDAO {
	public Categoria crear(Categoria objeto) {
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

	public Categoria editar(Categoria objeto) {
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

	public Categoria eliminar(Categoria objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Categoria EntidadToBeRemoved = em.getReference(Categoria.class, objeto.getIdCategoria());
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

	public List<Categoria> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Categoria> query = em.createQuery("SELECT e FROM Categoria e order by e.nombre",
					Categoria.class);
			List<Categoria> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Categoria buscarPorId(int id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Categoria categoria = new Categoria();
		try {
			TypedQuery<Categoria> query = em
					.createQuery("SELECT c FROM Categoria c where c.idCategoria = :idCategoria ", Categoria.class)
					.setParameter("idCategoria", id);
			List<Categoria> results = query.getResultList();
			categoria = results.get(0);
			return categoria;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return categoria;
		} finally {
			em.close();
		}
	}
}

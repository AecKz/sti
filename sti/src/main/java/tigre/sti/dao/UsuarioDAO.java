package tigre.sti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import tigre.sti.entitymanagerfactory.EntityManagerFactoryDAO;
import tigre.sti.dto.Usuario;

public class UsuarioDAO extends EntityManagerFactoryDAO {
	public Usuario crear(Usuario objeto) {
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

	public Usuario editar(Usuario objeto) {
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

	public Usuario eliminar(Usuario objeto) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			em.getTransaction().begin();
			Usuario EntidadToBeRemoved = em.getReference(Usuario.class,
					objeto.getIdUsuario());
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

	public List<Usuario> buscarTodos() {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		try {
			TypedQuery<Usuario> query = em.createQuery(
					"SELECT e FROM Usuario e order by e.usuario", Usuario.class);
			List<Usuario> results = query.getResultList();
			return results;
		} finally {
			em.close();
		}
	}

	public Usuario buscarPorId(String id) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Usuario usuario = new Usuario();
		try {
			TypedQuery<Usuario> query = em.createQuery(
					"SELECT c FROM Usuario c where c.idUsuario = :idUsuario ", Usuario.class)
					.setParameter("idUsuario", id);
			List<Usuario> results = query.getResultList();
			usuario = results.get(0);
			return usuario;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return usuario;
		} finally {
			em.close();
		}
	}
	public Usuario buscarPorUsuario(String usuario) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Usuario usr = new Usuario();
		try {
			TypedQuery<Usuario> query = em.createQuery(
					"SELECT c FROM Usuario c where c.usuario = :usuario ", Usuario.class)
					.setParameter("usuario", usuario);
			List<Usuario> results = query.getResultList();
			usr = results.get(0);
			return usr;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return usr;
		} finally {
			em.close();
		}
	}
	/**
	 * Metodo para buscar el objeto Usuario a partir del idPersona
	 * @param idPersona
	 * @return objeto Usuario
	 * */
	public Usuario buscarPorIdPersona(int idPersona) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Usuario usuario = new Usuario();
		try {
			TypedQuery<Usuario> query = em.createQuery(
					"SELECT u FROM Usuario u "
					+ "JOIN FETCH u.persona p "	
					+ "where p.idPersona = :idPersona ", Usuario.class)
					.setParameter("idPersona", idPersona);
			List<Usuario> results = query.getResultList();
			if(!results.isEmpty()){
				usuario = results.get(0);
			}			
			return usuario;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return usuario;
		} finally {
			em.close();
		}
	}
	
	public Boolean login (String usuarioLogin, String contrasenaLogin) {
		EntityManager em = obtenerEntityManagerFactory().createEntityManager();
		Boolean flagLogin = false;
		try {
			TypedQuery<Usuario> query = em.createQuery(
					"SELECT u.idUsuario FROM Usuario u where u.usuario = :usuarioLogin "
					+ "and u.clave = :contrasenaLogin " , Usuario.class)
					.setParameter("usuarioLogin", usuarioLogin).setParameter("contrasenaLogin", contrasenaLogin);
			List<Usuario> results = query.getResultList();
			if(!results.isEmpty()){
				flagLogin = true;
			}else{
				flagLogin = false;
			}
			return flagLogin;
		} catch (Exception e) {
			em.getTransaction().rollback();
			System.out.println(e.getMessage());
			return flagLogin;
		} finally {
			em.close();
		}
	}
}

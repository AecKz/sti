package tigre.sti.entitymanagerfactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public abstract class EntityManagerFactoryDAO {

	@PersistenceContext
	static EntityManagerFactory entityManagerFactory;

	public static EntityManagerFactory obtenerEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence
					.createEntityManagerFactory("sti");
		}
		return entityManagerFactory;
	}
}

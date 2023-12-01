package edu.virginia.sde.reviews;

import jakarta.persistence.PersistenceException;
import org.hibernate.Session;

import java.util.logging.Level;

public class DatabaseService {
    private Session session;
    public DatabaseService(Session session)
    {
        this.session = session;
    }
    public void addUser(User user)
    {
        try {
            session.persist(user);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            //ERROR OCCURRED WHEN TRYING TO ADD USER
            return;
        }
    }



}

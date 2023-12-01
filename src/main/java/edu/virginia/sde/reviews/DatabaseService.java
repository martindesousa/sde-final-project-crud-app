package edu.virginia.sde.reviews;

import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.query.Query;

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

    public void addCourse(Course course)
    {
        String hql = "FROM Course c WHERE c.subjectMnemonic = :"+course.getSubjectMnemonic()+
                " AND c.courseNumber = :"+course.getCourseNumber();
        Query<Course> query = session.createQuery(hql, Course.class);
        for(Course c : query.list())
        {
            if(c.conflictsWith(course))
            {
                return;
            }
        }
        session.persist(course);
        session.getTransaction().commit();
    }

    public void removeCourse(Course course)
    {
//        String hql = "FROM Review r WHERE r.course = :"+course;
//        Query<Review> query = session.createQuery(hql, Review.class);
//        for(Review r : query.list())
//        {
//            session.remove(r);
//        }
        session.remove(course);
        session.getTransaction().commit();
    }



}

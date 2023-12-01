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

    public boolean addCourse(Course course)
    {
        String hql = "FROM Course c WHERE c.subjectMnemonic = :"+course.getSubjectMnemonic()+
                " AND c.courseNumber = :"+course.getCourseNumber();
        Query<Course> query = session.createQuery(hql, Course.class);
        for(Course c : query.getResultList())
        {
            if(c.conflictsWith(course))
            {
                return false;
            }
        }
        session.persist(course);
        session.getTransaction().commit();
        return true;
    }

    public boolean removeCourse(Course course)
    {
//        String hql = "FROM Review r WHERE r.course = :"+course;
//        Query<Review> query = session.createQuery(hql, Review.class);
//        for(Review r : query.list())
//        {
//            session.remove(r);
//        }
        try {
            session.remove(course);
            session.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            //ERROR OCCURRED WHEN TRYING TO REMOVE COURSE
            return false;
        }
    }

    public boolean addReview(Review review)
    {
        String hql = "FROM Review r WHERE r.user = :"+review.getUser()+
                " AND r.course = :"+review.getCourse();
        Query<Review> query = session.createQuery(hql, Review.class);
        if(query.getResultList().isEmpty()) {
            session.persist(review);
            session.getTransaction().commit();
            return true;
        } else {
            return false;
        }
    }

    public boolean removeReview(Review review)
    {
        try {
            session.remove(review);
            session.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            //ERROR OCCURRED WHEN TRYING TO REMOVE REVIEW
            return false;
        }
    }
}

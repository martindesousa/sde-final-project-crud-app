package edu.virginia.sde.reviews;

import com.sun.javafx.logging.PlatformLogger;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.logging.Level;

public class DatabaseService {



    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        DatabaseService service = new DatabaseService(session);
        service.addUser(new User("ridge","RidgeMartinJad"));
        //System.out.println(service.getAllCourses().toString());
    }

    private Session session;
    public DatabaseService(Session session)
    {
        this.session = session;
    }


    public void addUser(User user)
    {
        try {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            //ERROR OCCURRED WHEN TRYING TO ADD USER
            return;
        }
    }

    public void closeSession(){
        session.close();
    }

    public boolean usernameExists(String typedUsername)
    {
        //String typedUsername = user.getUsername();
        String hql = "FROM User u WHERE u.username = :username";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("username", typedUsername);
        if (query.getResultList().isEmpty())
        {
            return false;
        }
        return true;
    }

    public boolean passwordCorrect(String typedUsername, String typedPassword)
    {
        /*if (!usernameExists(user))
        {
            return false;
        }
        */
        String hql = "FROM User u WHERE u.username = :username";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("username", typedUsername);
        if (query.getSingleResult().getPassword().equals(typedPassword))
        {
            return true;
        }
        return false;
    }


    public boolean existingCourse(Course course)
    {
        String hql = "FROM Course c WHERE c.subjectMnemonic = :"+course.getSubjectMnemonic()+
                " AND c.courseNumber = :"+course.getCourseNumber();
        Query<Course> query = session.createQuery(hql, Course.class);
        for(Course c : query.getResultList())
        {
            if(c.shareTitle(course))
            {
                return false;
            }
        }
        return true;

    }



    public void addCourse(Course course){
        try {
            session.beginTransaction();
            session.persist(course);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Virginia already added!");
        }
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
            session.beginTransaction();
            session.remove(review);
            session.getTransaction().commit();
            return true;
        } catch (PersistenceException e) {
            //ERROR OCCURRED WHEN TRYING TO REMOVE REVIEW
            return false;
        }
    }

    public ArrayList<Course> getAllCourses()
    {
        String hql = "FROM Course c";
        Query<Course> query = session.createQuery(hql, Course.class);
        return (ArrayList<Course>) query.getResultList();
    }

    public ArrayList<Course> getSameMnemonicCourses(String mnemonic)
    {
        String hql = "FROM Course c WHERE c.subjectMnemonic = :"+mnemonic;
        Query<Course> query = session.createQuery(hql, Course.class);
        return (ArrayList<Course>) query.getResultList();
    }

    public ArrayList<Course> getSameMnemonicCourses(String mnemonic, ArrayList<Course> courses)
    {
        ArrayList<Course> returnList = new ArrayList<>();
        for(Course c : courses)
        {
            if(c.getSubjectMnemonic().equals(mnemonic))
            {
                returnList.add(c);
            }
        }
        return returnList;
    }

    public ArrayList<Course> getSameNumberCourses(int courseNumber)
    {
        String hql = "FROM Course c WHERE c.courseNumber = :"+courseNumber;
        Query<Course> query = session.createQuery(hql, Course.class);
        return (ArrayList<Course>) query.getResultList();
    }

    public ArrayList<Course> getSameNumberCourses(int courseNumber, ArrayList<Course> courses)
    {
        ArrayList<Course> returnList = new ArrayList<>();
        for(Course c : courses)
        {
            if(c.getCourseNumber() == (courseNumber))
            {
                returnList.add(c);
            }
        }
        return returnList;
    }

    public ArrayList<Course> getSameTitleCourses(String title)
    {
        String hql = "FROM Course c WHERE c.courseTitle = :"+title;
        Query<Course> query = session.createQuery(hql, Course.class);
        return (ArrayList<Course>) query.getResultList();
    }

    public ArrayList<Course> getSameTitleCourses(String title, ArrayList<Course> courses)
    {
        ArrayList<Course> returnList = new ArrayList<>();
        for(Course c : courses)
        {
            if(c.getCourseTitle().equals(title))
            {
                returnList.add(c);
            }
        }
        return returnList;
    }
}

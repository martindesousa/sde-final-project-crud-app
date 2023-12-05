package edu.virginia.sde.reviews;

import com.sun.javafx.logging.PlatformLogger;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.sqlite.SQLiteException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class DatabaseService {

/*

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Session session = HibernateUtil.getSessionFactory().openSession();
        DatabaseService service = new DatabaseService(session);
        User me = service.getUser("user");
        List<Review> reviews = service.getUserReviews(me);
        service.updateRatings(reviews.get(0).getCourse());
        System.out.println(reviews.get(0).getCourse().getAvgRating());


    }

 */



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

    public void updateRatings(Course course){

        if (getCourseReviews(course).isEmpty()) //THIS HAPPENS IF THERE IS 1 REVIEW LEFT AND IT GETS DELETED, IN ORDER TO PREVENT "NOT NULL" ERROR
        {
            try {
                session.beginTransaction();
                course.setAvgRating(0);
                session.getTransaction().commit();
            } catch (PersistenceException e) {
                //ERROR OCCURRED WHEN TRYING TO REMOVE REVIEW
            }
        }
        else {
            List<Integer> reviewRatings = getCourseReviews(course).stream().map(review -> review.getRating()).collect(Collectors.toList());
            int sum = 0;
            for (int i : reviewRatings) {
                sum += i;
            }

            double avgRating = ((double) sum) / ((double) reviewRatings.size());

            try {
                session.beginTransaction();
                course.setAvgRating(avgRating);
                session.getTransaction().commit();
            } catch (PersistenceException e) {
                //ERROR OCCURRED WHEN TRYING TO REMOVE REVIEW
            }
        }

    }



    public boolean existingCourse(String mnemonic, int number, String title)
    {
        String hql = "FROM Course c WHERE c.subjectMnemonic = :mnemonic AND c.courseNumber = :number";
        Query<Course> query = session.createQuery(hql, Course.class);
        query.setParameter("mnemonic", mnemonic);
        query.setParameter("number", number);
        if(query.getResultList().isEmpty()){
            return false;
        }
        for(Course c : query.getResultList())
        {
            if(c.getCourseTitle().toUpperCase().equals(title.toUpperCase()))
            {
                return true;
            }
        }
        return false;

    }



    public void addCourse(Course course){
        try {
            session.beginTransaction();
            session.persist(course);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            // System.out.println("Virginia already added!");
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

    public boolean containsReview(User user, Course course){
        String hql = "FROM Review r WHERE r.user = : user AND r.course = :course";
        Query<Review> query = session.createQuery(hql, Review.class);
        query.setParameter("user", user);
        query.setParameter("course", course);
        if(query.getResultList().isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    public void addReview(Review review)
    {
        /*
        String hql = "FROM Review r WHERE r.user = : user AND r.course = :course";
        Query<Review> query = session.createQuery(hql, Review.class);
        query.setParameter("user", review.getUser());
        query.setParameter("course", review.getCourse());
        if(query.getResultList().isEmpty()) {
            session.persist(review);
            session.getTransaction().commit();
            return true;
        } else {
            return false;
        }

         */
        try {
            session.beginTransaction();
            session.persist(review);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            // System.out.println("Virginia already added!");
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

    public void updateReview(Review review, String comment, int rating){
        try {
            session.beginTransaction();
            review.setTime(new Timestamp(System.currentTimeMillis()));
            review.setComment(comment);
            review.setRating(rating);
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            //ERROR OCCURRED WHEN TRYING TO REMOVE REVIEW
        }

    }

    protected Review getReview(User user, Course course ){
        String hql = "FROM Review r WHERE r.user = : user AND r.course = :course";
        Query<Review> query = session.createQuery(hql, Review.class);
        query.setParameter("user", user);
        query.setParameter("course", course);
        return query.getSingleResult();
    }

    public ArrayList<Review> getCourseReviews(Course course){
        String hql = "FROM Review r WHERE r.course = :course";
        Query<Review> query = session.createQuery(hql, Review.class);
        query.setParameter("course", course);
        return (ArrayList<Review>) query.getResultList();
    }

    public ArrayList<Review> getUserReviews(User user){
        String hql = "FROM Review r WHERE r.user = :user";
        Query<Review> query = session.createQuery(hql, Review.class);
        query.setParameter("user", user);
        return (ArrayList<Review>) query.getResultList();
    }

    public ArrayList<Course> getAllCourses()
    {
        String hql = "FROM Course c";
        Query<Course> query = session.createQuery(hql, Course.class);
        return (ArrayList<Course>) query.getResultList();
    }

    protected User getUser(String username){
        String hql = "FROM User u WHERE u.username = :username";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    public ArrayList<Course> getSameMnemonicCourses(String mnemonic)
    {
        String hql = "FROM Course c WHERE c.subjectMnemonic = :mnemonic";
        Query<Course> query = session.createQuery(hql, Course.class);
        query.setParameter("mnemonic", mnemonic);
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
        String hql = "FROM Course c WHERE c.courseNumber = : number";
        Query<Course> query = session.createQuery(hql, Course.class);
        query.setParameter("number", courseNumber);
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
        String hql = "FROM Course c WHERE c.courseTitle = :title";
        Query<Course> query = session.createQuery(hql, Course.class);
        query.setParameter("title", title);
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

    public ArrayList<Course> getSimilarTitleCourses(String title)
    {
        return getSimilarTitleCourses(title, this.getAllCourses());
    }

    public ArrayList<Course> getSimilarTitleCourses(String title, ArrayList<Course> courses)
    {
        ArrayList<Course> returnList = new ArrayList<>();
        for(Course c : courses)
        {
            if(c.getCourseTitle().toUpperCase().contains(title.toUpperCase()))
            {
                returnList.add(c);
            }
        }
        return returnList;
    }
}

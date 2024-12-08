package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class CourseDAO {
    private SessionFactory factory;

    public CourseDAO() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();
    }

    public void addCourse(String title, int duration) {
        Session session = factory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Course course = new Course(title, duration);
            session.save(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Course getCourse(int id) {
        Session session = factory.getCurrentSession();
        Course course = null;
        try {
            session.beginTransaction();
            course = session.get(Course.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return course;
    }

    public void updateCourse(int id, String title, int duration) {
        Session session = factory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Course course = session.get(Course.class, id);
            if (course != null) {
                course.setTitle(title);
                course.setDuration(duration);
                session.update(course);
            } else {
                System.out.println("Course with ID " + id + " not found for update.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteCourse(int id) {
        Session session = factory.getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Course course = session.get(Course.class, id);
            if (course != null) {
                session.delete(course);
            } else {
                System.out.println("Course with ID " + id + " not found for deletion.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void close() {
        factory.close();
    }
}
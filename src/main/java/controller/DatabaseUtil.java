package controller;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Book;


/**
 * @author aidan - agdavidson
 * CIS175 - Spring 2024
 * Feb 15, 2024
 */
public class DatabaseUtil {
	static EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Booklist");

    public static EntityManagerFactory getEntityManagerFactory() {
        
        return emfactory;
    }
    
    public List <Book> getAllBooks(){
		EntityManager em = emfactory.createEntityManager();
		List <Book> allBooks = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
		em.close();
		return allBooks;
	}
}

package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import model.Book;

@WebServlet("/edit")
public class EditBookServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Step 1: Receive Request Parameters
        int bookId = Integer.parseInt(request.getParameter("id"));
        String newTitle = request.getParameter("newTitle");
        String newAuthor = request.getParameter("newAuthor");
        
        // Step 2: Retrieve Book
        EntityManagerFactory emf = DatabaseUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Book book = em.find(Book.class, bookId);
        
        // Step 3: Update Book Details
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
        }
        
        // Step 4: Persist Changes
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(book);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new ServletException("Error updating book details.", e);
        } finally {
            em.close();
        }
        
        // Step 5: Redirect
        response.sendRedirect(request.getContextPath() + "/IndexServlet");
    }
}

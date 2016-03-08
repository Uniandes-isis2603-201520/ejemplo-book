package co.edu.uniandes.csw.bookstore.ejbs;

import co.edu.uniandes.csw.bookstore.api.IBookLogic;
import co.edu.uniandes.csw.bookstore.entities.AuthorEntity;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bookstore.persistence.AuthorPersistence;
import co.edu.uniandes.csw.bookstore.persistence.BookPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class BookLogic implements IBookLogic {

    private static final Logger logger = Logger.getLogger(BookLogic.class.getName());

    @Inject
    private BookPersistence persistence;

    @Inject
    private AuthorPersistence authorPersistence;

    @Override
    public List<BookEntity> getBooks() {
        logger.info("Inicia proceso de consultar todos los libros");
        List<BookEntity> books = persistence.findAll();
        logger.info("Termina proceso de consultar todos los libros");
        return books;
    }

    @Override
    public BookEntity getBook(Long id) throws BusinessLogicException {
        logger.log(Level.INFO, "Inicia proceso de consultar libro con id={0}", id);
        BookEntity book = persistence.find(id);
        if (book == null) {
            logger.log(Level.SEVERE, "El libro con el id {0} no existe", id);
            throw new BusinessLogicException("El libro solicitado no existe");
        }
        logger.log(Level.INFO, "Termina proceso de consultar libro con id={0}", id);
        return book;
    }

    @Override
    public BookEntity createBook(BookEntity entity) {
        logger.info("Inicia proceso de creación de libro");
        persistence.create(entity);
        logger.info("Termina proceso de creación de libro");
        return entity;
    }

    @Override
    public BookEntity updateBook(BookEntity entity) {
        logger.log(Level.INFO, "Inicia proceso de actualizar libro con id={0}", entity.getId());
        BookEntity newEntity = persistence.update(entity);
        logger.log(Level.INFO, "Termina proceso de actualizar libro con id={0}", entity.getId());
        return newEntity;
    }

    @Override
    public void deleteBook(Long id) {
        logger.log(Level.INFO, "Inicia proceso de borrar libro con id={0}", id);
        persistence.delete(id);
        logger.log(Level.INFO, "Termina proceso de borrar libro con id={0}", id);
    }

    @Override
    public List<AuthorEntity> getAuthors(Long bookId) {
        return persistence.find(bookId).getAuthors();
    }

    @Override
    public AuthorEntity getAuthor(Long bookId, Long authorId) {
        List<AuthorEntity> authors = persistence.find(bookId).getAuthors();
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(authorId);
        int index = authors.indexOf(authorEntity);
        if (index >= 0) {
            return authors.get(index);
        }
        return null;
    }

    @Override
    public AuthorEntity addAuthor(Long authorId, Long bookId) {
        BookEntity bookEntity = persistence.find(bookId);
        AuthorEntity authorEntity = authorPersistence.find(authorId);
        bookEntity.getAuthors().add(authorEntity);
        return authorEntity;
    }

    @Override
    public void removeAuthor(Long authorId, Long bookId) {
        BookEntity bookEntity = persistence.find(bookId);
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(authorId);
        bookEntity.getAuthors().remove(authorEntity);
    }

    @Override
    public List<AuthorEntity> replaceAuthors(List<AuthorEntity> authors, Long bookId) {
        BookEntity bookEntity = persistence.find(bookId);
        List<AuthorEntity> authorList = authorPersistence.findAll();
        bookEntity.setAuthors(authors);
        return bookEntity.getAuthors();
    }
}

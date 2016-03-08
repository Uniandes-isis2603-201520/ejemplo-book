package co.edu.uniandes.csw.bookstore.resources;

import co.edu.uniandes.csw.bookstore.api.IBookLogic;
import co.edu.uniandes.csw.bookstore.converters.AuthorConverter;
import co.edu.uniandes.csw.bookstore.converters.BookConverter;
import co.edu.uniandes.csw.bookstore.dtos.AuthorDTO;
import co.edu.uniandes.csw.bookstore.dtos.BookDTO;
import co.edu.uniandes.csw.bookstore.entities.BookEntity;
import co.edu.uniandes.csw.bookstore.providers.StatusCreated;
import co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("books")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private static final Logger logger = Logger.getLogger(BookResource.class.getName());

    @Inject
    private IBookLogic bookLogic;

    /**
     * Obtiene la lista de los registros de Book.
     *
     * @return Colección de objetos de BookDTO cada uno con sus respectivos
     * Review
     * @generated
     */
    @GET
    public List<BookDTO> getBook() {
        logger.info("Se ejecuta método getBooks");
        return BookConverter.listEntity2DTO(bookLogic.getBooks());
    }

    /**
     * Obtiene los datos de una instancia de Book a partir de su ID.
     *
     * @param id Identificador de la instancia a consultar
     * @return Instancia de BookDTO con los datos del Book consultado y sus
     * Review
     * @generated
     */
    @GET
    @Path("{id: \\d+}")
    public BookDTO getBook(@PathParam("id") Long id) throws BusinessLogicException {
        logger.log(Level.INFO, "Se ejecuta método getBook con id={0}", id);
        return BookConverter.fullEntity2DTO(bookLogic.getBook(id));
    }

    /**
     * Se encarga de crear un book en la base de datos.
     *
     * @param dto Objeto de BookDTO con los datos nuevos
     * @return Objeto de BookDTO con los datos nuevos y su ID.
     * @generated
     */
    @POST
    @StatusCreated
    public BookDTO createBook(BookDTO dto) {
        logger.info("Se ejecuta método createBook");
        BookEntity entity = BookConverter.fullDTO2Entity(dto);
        return BookConverter.fullEntity2DTO(bookLogic.createBook(entity));
    }

    /**
     * Actualiza la información de una instancia de Book.
     *
     * @param id Identificador de la instancia de Book a modificar
     * @param dto Instancia de BookDTO con los nuevos datos.
     * @return Instancia de BookDTO con los datos actualizados.
     * @generated
     */
    @PUT
    @Path("{id: \\d+}")
    public BookDTO updateBook(@PathParam("id") Long id, BookDTO dto) {
        logger.log(Level.INFO, "Se ejecuta método updateBook con id={0}", id);
        BookEntity entity = BookConverter.fullDTO2Entity(dto);
        entity.setId(id);
        return BookConverter.fullEntity2DTO(bookLogic.updateBook(entity));
    }

    /**
     * Elimina una instancia de Book de la base de datos.
     *
     * @param id Identificador de la instancia a eliminar.
     * @generated
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteBook(@PathParam("id") Long id) {
        logger.log(Level.INFO, "Se ejecuta método deleteBook con id={0}", id);
        bookLogic.deleteBook(id);
    }

    /**
     * Obtiene una colección de instancias de AuthorDTO asociadas a una
     * instancia de Book
     *
     * @param bookId Identificador de la instancia de Book
     * @return Colección de instancias de AuthorDTO asociadas a la instancia de
     * Book
     * @generated
     */
    @GET
    @Path("{bookId: \\d+}/authors")
    public List<AuthorDTO> listAuthors(@PathParam("bookId") Long bookId) {
        return AuthorConverter.listEntity2DTO(bookLogic.getAuthors(bookId));
    }

    /**
     * Obtiene una instancia de Author asociada a una instancia de Book
     *
     * @param bookId Identificador de la instancia de Book
     * @param authorId Identificador de la instancia de Author
     * @generated
     */
    @GET
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public AuthorDTO getAuthors(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        return AuthorConverter.fullEntity2DTO(bookLogic.getAuthor(bookId, authorId));
    }

    /**
     * Asocia un Author existente a un Book
     *
     * @param bookId Identificador de la instancia de Book
     * @param authorId Identificador de la instancia de Author
     * @return Instancia de AuthorDTO que fue asociada a Book
     * @generated
     */
    @POST
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public AuthorDTO addAuthors(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        return AuthorConverter.fullEntity2DTO(bookLogic.addAuthor(bookId, authorId));
    }

    /**
     * Remplaza las instancias de Author asociadas a una instancia de Book
     *
     * @param bookId Identificador de la instancia de Book
     * @param authors Colección de instancias de AuthorDTO a asociar a instancia
     * de Book
     * @return Nueva colección de AuthorDTO asociada a la instancia de Book
     * @generated
     */
    @PUT
    @Path("{bookId: \\d+}/authors")
    public List<AuthorDTO> replaceAuthors(@PathParam("bookId") Long bookId, List<AuthorDTO> authors) {
        return AuthorConverter.listEntity2DTO(bookLogic.replaceAuthors(AuthorConverter.listDTO2Entity(authors), bookId));
    }

    /**
     * Desasocia un Author existente de un Book existente
     *
     * @param bookId Identificador de la instancia de Book
     * @param authorId Identificador de la instancia de Author
     * @generated
     */
    @DELETE
    @Path("{bookId: \\d+}/authors/{authorId: \\d+}")
    public void removeAuthors(@PathParam("bookId") Long bookId, @PathParam("authorId") Long authorId) {
        bookLogic.removeAuthor(bookId, authorId);
    }
}

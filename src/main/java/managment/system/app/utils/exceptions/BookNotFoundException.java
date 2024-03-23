package managment.system.app.utils.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundException extends ResponseStatusException {
    public BookNotFoundException(HttpStatusCode status) {
        super(status, "Book not found");
    }
}

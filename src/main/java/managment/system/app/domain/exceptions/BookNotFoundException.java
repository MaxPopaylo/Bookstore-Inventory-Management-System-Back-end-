package managment.system.app.domain.exceptions;

public class BookNotFoundException  extends RuntimeException {
    public BookNotFoundException() {
        super("Book not found");
    }
}

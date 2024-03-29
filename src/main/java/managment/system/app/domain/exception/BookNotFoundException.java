package managment.system.app.domain.exception;

public class BookNotFoundException  extends RuntimeException {
    public BookNotFoundException() {
        super("Book not found");
    }
}

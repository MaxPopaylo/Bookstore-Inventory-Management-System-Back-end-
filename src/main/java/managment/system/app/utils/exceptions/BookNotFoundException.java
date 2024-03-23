package managment.system.app.utils.exceptions;

public class BookNotFoundException  extends RuntimeException {
    public BookNotFoundException() {
        super("Book not found");
    }
}

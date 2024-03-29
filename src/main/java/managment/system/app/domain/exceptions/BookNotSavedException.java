package managment.system.app.domain.exceptions;

public class BookNotSavedException extends RuntimeException {
    public BookNotSavedException() {
        super("Book not saved");
    }
}

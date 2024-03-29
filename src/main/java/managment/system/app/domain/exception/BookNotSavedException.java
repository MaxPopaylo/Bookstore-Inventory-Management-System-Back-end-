package managment.system.app.domain.exception;

public class BookNotSavedException extends RuntimeException {
    public BookNotSavedException() {
        super("Book not saved");
    }
}

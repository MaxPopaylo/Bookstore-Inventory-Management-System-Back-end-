package managment.system.app.utils.exceptions;

public class BookNotSavedException extends RuntimeException {
    public BookNotSavedException() {
        super("Book not saved");
    }
}

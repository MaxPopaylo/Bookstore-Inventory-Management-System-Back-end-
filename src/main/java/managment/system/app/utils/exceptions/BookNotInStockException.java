package managment.system.app.utils.exceptions;


import org.apache.commons.lang3.StringUtils;

public class BookNotInStockException extends RuntimeException {
    public BookNotInStockException(int availableQuantity) {
        super((availableQuantity == 0) ? "Book not in stock now" : "Only " + availableQuantity + " books are available");
    }
}

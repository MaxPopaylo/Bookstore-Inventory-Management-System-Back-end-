package managment.system.app.application.ports;

import io.grpc.StatusRuntimeException;
import managment.system.app.domain.exceptions.*;

public interface BookExceptionHandler {

    StatusRuntimeException handleValidationError(BookNotFoundException cause);
    StatusRuntimeException handleValidationError(BookNotSavedException cause);
    StatusRuntimeException handleValidationError(BookNotInStockException cause);

}

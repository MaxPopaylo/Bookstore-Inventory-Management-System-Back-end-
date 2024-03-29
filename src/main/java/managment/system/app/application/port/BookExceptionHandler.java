package managment.system.app.application.port;

import io.grpc.StatusRuntimeException;
import managment.system.app.domain.exception.*;

public interface BookExceptionHandler {

    StatusRuntimeException handleValidationError(BookNotFoundException cause);
    StatusRuntimeException handleValidationError(BookNotSavedException cause);
    StatusRuntimeException handleValidationError(BookNotInStockException cause);

}

package managment.system.app.utils;


import com.google.rpc.Code;
import com.google.rpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import managment.system.app.utils.exceptions.BookNotFoundException;
import managment.system.app.utils.exceptions.BookNotInStockException;
import managment.system.app.utils.exceptions.BookNotSavedException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class BookExceptionHandler {

    @GrpcExceptionHandler(BookNotFoundException.class)
    public StatusRuntimeException handleValidationError(BookNotFoundException cause) {

        Status status = Status.newBuilder()
                .setCode(Code.NOT_FOUND_VALUE)
                .setMessage(cause.getMessage())
                .build();

        return StatusProto.toStatusRuntimeException(status);
    }

    @GrpcExceptionHandler(BookNotSavedException.class)
    public StatusRuntimeException handleValidationError(BookNotSavedException cause) {

        Status status = Status.newBuilder()
                .setCode(Code.CANCELLED_VALUE)
                .setMessage(cause.getMessage())
                .build();

        return StatusProto.toStatusRuntimeException(status);
    }

    @GrpcExceptionHandler(BookNotInStockException.class)
    public StatusRuntimeException handleValidationError(BookNotInStockException cause) {

        Status status = Status.newBuilder()
                .setCode(Code.UNAVAILABLE_VALUE)
                .setMessage(cause.getMessage())
                .build();

        return StatusProto.toStatusRuntimeException(status);
    }

}

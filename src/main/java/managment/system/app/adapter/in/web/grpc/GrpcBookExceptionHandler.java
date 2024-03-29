package managment.system.app.adapter.in.web.grpc;


import com.google.rpc.Code;
import com.google.rpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import managment.system.app.application.port.BookExceptionHandler;
import managment.system.app.domain.exception.*;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class GrpcBookExceptionHandler implements BookExceptionHandler {

    @Override
    @GrpcExceptionHandler(BookNotFoundException.class)
    public StatusRuntimeException handleValidationError(BookNotFoundException cause) {

        Status status = Status.newBuilder()
                .setCode(Code.NOT_FOUND_VALUE)
                .setMessage(cause.getMessage())
                .build();

        return StatusProto.toStatusRuntimeException(status);
    }

    @Override
    @GrpcExceptionHandler(BookNotSavedException.class)
    public StatusRuntimeException handleValidationError(BookNotSavedException cause) {

        Status status = Status.newBuilder()
                .setCode(Code.CANCELLED_VALUE)
                .setMessage(cause.getMessage())
                .build();

        return StatusProto.toStatusRuntimeException(status);
    }

    @Override
    @GrpcExceptionHandler(BookNotInStockException.class)
    public StatusRuntimeException handleValidationError(BookNotInStockException cause) {

        Status status = Status.newBuilder()
                .setCode(Code.UNAVAILABLE_VALUE)
                .setMessage(cause.getMessage())
                .build();

        return StatusProto.toStatusRuntimeException(status);
    }

}

package managment.system.app.service;

import app.grpc.book.BookOuterClass;
import app.grpc.book.BookServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class BookService extends BookServiceGrpc.BookServiceImplBase {

}

package managment.system.app.service;

import app.grpc.book.BookServiceGrpc;
import app.grpc.book_types.BookTypes;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import managment.system.app.dao.BookDao;
import managment.system.app.entity.Book;
import managment.system.app.mapper.BookMapper;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class BookService extends BookServiceGrpc.BookServiceImplBase {

    private final BookDao dao;

    public void getAll(Empty request,
                       StreamObserver<BookTypes.Book> responseObserver) {

        List<? extends Book> books = dao.getAll();


//        var response = BookOuterClass.getAllResponse.newBuilder().addAllBook()
    }

    @Override
    public void getById(BookTypes.getByIdRequest request,
                        StreamObserver<BookTypes.getByIdResponse> responseObserver) {

        UUID id = UUID.fromString(request.getId().getValue());
        Book book = dao.getById(id);

        BookTypes.Book responseBook = BookMapper.mapper.toProtoEntity(book);

        var response = BookTypes.getByIdResponse
                .newBuilder()
                .setBook(responseBook)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

}

package managment.system.app.service;

import app.grpc.book.BookServiceGrpc;
import app.grpc.book_types.BookTypes;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import managment.system.app.dao.BookDao;
import managment.system.app.dto.BookDto;
import managment.system.app.entity.Book;
import managment.system.app.mapper.BookMapper;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;
import java.util.UUID;

@GrpcService
@RequiredArgsConstructor
public class BookGrpcService extends BookServiceGrpc.BookServiceImplBase {

    private final BookDao dao;

    public void getAll(Empty request,
                       StreamObserver<BookTypes.Book> responseObserver) {

        List<? extends Book> books = dao.getAll();
        for (var book : books) {
            responseObserver.onNext(BookMapper.mapper.toProtoEntity(book));
        }
        responseObserver.onCompleted();

    }

    @Override
    public void getById(BookTypes.getByIdRequest request,
                        StreamObserver<BookTypes.getByIdResponse> responseObserver) {

        UUID id = UUID.fromString(request.getId().getValue());
        Book book = dao.getById(id);

        var response = BookTypes.getByIdResponse
                .newBuilder()
                .setBook(BookMapper.mapper.toProtoEntity(book))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void save(BookTypes.saveRequest request,
                        StreamObserver<BookTypes.saveResponse> responseObserver) {

        BookDto dto = BookMapper.mapper.toDtoFromProto(request.getDto());
        Book book = dao.save(dto);

        var response = BookTypes.saveResponse
                .newBuilder()
                .setBook(BookMapper.mapper.toProtoEntity(book))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void delete(BookTypes.deleteRequest request,
                       StreamObserver<Empty> responseObserver) {

        UUID id = UUID.fromString(request.getId().getValue());
        dao.delete(id);

        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();

    }

    @Override
    public void update(BookTypes.updateRequest request,
                       StreamObserver<BookTypes.updateResponse> responseObserver) {

        System.out.println(request.getId().getValue());

        UUID id = UUID.fromString(request.getId().getValue());
        BookDto dto = BookMapper.mapper.toDtoFromProto(request.getDto());

        Book book = dao.update(id, dto);

        var response = BookTypes.updateResponse
                .newBuilder()
                .setBook(BookMapper.mapper.toProtoEntity(book))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void sell(BookTypes.sellRequest request,
                     StreamObserver<BookTypes.sellResponse> responseObserver) {

        UUID id = UUID.fromString(request.getId().getValue());
        int quantity = request.getQuantity();

        Book book = dao.sell(id, quantity);

        var response = BookTypes.sellResponse
                        .newBuilder()
                        .setQuantity(book.getQuantity())
                        .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void receive(BookTypes.receiveRequest request,
                       StreamObserver<BookTypes.receiveResponse> responseObserver) {

        UUID id = UUID.fromString(request.getId().getValue());
        int quantity = request.getQuantity();

        Book book = dao.receive(id, quantity);

        var response = BookTypes.receiveResponse
                .newBuilder()
                .setQuantity(book.getQuantity())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }



}



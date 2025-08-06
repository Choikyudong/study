package com.example.grpc.grpc;

import helloproto.Empty;
import helloproto.HelloProto;
import helloproto.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

	@Override
	public void sayHelloProto(Empty request, StreamObserver<HelloProto> responseObserver) {
		HelloProto helloProto = HelloProto.newBuilder()
				.setName("Hello Proto!")
				.build();

		responseObserver.onNext(helloProto);
		responseObserver.onCompleted();
	}

}

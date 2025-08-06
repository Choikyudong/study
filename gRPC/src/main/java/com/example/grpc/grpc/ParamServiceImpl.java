package com.example.grpc.grpc;

import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;
import param.MessageDetail;
import param.ParamRequest;
import param.ParamResponse;
import param.ParamServiceGrpc;

import java.time.Instant;
import java.util.List;

@GrpcService
public class ParamServiceImpl extends ParamServiceGrpc.ParamServiceImplBase {

	@Override
	public void getMessages(ParamRequest request, StreamObserver<ParamResponse> responseObserver) {
		long id = request.getId();
		if (id < 1) {
			Status status = Status.INVALID_ARGUMENT.withDescription("에러!");
			responseObserver.onError(status.asRuntimeException());
			return;
		}

		Instant now = Instant.now();
		Timestamp timestamp = Timestamp.newBuilder()
				.setSeconds(now.getEpochSecond())
				.setNanos(now.getNano())
				.build();

		List<MessageDetail> messageDetails = List.of(
				MessageDetail.newBuilder()
						.setId(id)
						.setContent("콘텐츠1:" + request.getMyEnum().name())
						.setCreatedAt(timestamp)
						.build(),
				MessageDetail.newBuilder()
						.setId(id)
						.setContent("콘텐츠2:" + request.getMyEnum().name())
						.setCreatedAt(timestamp)
						.build()
		);

		ParamResponse paramResponse = ParamResponse.newBuilder()
				.setTitle(request.getText() + id)
				.addAllMessages(messageDetails)
				.build();

		responseObserver.onNext(paramResponse);
		responseObserver.onCompleted();
	}

}

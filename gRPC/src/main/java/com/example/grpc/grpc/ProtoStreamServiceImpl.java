package com.example.grpc.grpc;

import com.google.protobuf.StringValue;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;
import protostream.ProtoStreamServiceGrpc;
import protostream.StreamRequest;
import protostream.StreamTextRequest;

import java.util.concurrent.TimeUnit;

@GrpcService
public class ProtoStreamServiceImpl extends ProtoStreamServiceGrpc.ProtoStreamServiceImplBase {

	@Override
	public void serverStream(StreamRequest request, StreamObserver<StringValue> responseObserver) {
		long times = request.getTimes();

		if (times < 1) {
			Status status = Status.INVALID_ARGUMENT.withDescription("에러!");
			responseObserver.onError(status.asRuntimeException());
			return;
		}

		try {
			for (long i = 0; i < times; i++) {
				// WebFlux의 .map(seq -> "스트리밍 메시지 #" + seq)와 동일
				String messageContent = "스트리밍 메시지 #" + (i + 1);

				StringValue response = StringValue.newBuilder().setValue(messageContent).build();

				responseObserver.onNext(response);

				TimeUnit.SECONDS.sleep(1); // 1초 대기
			}
		} catch (InterruptedException e) {
			Status status = Status.INVALID_ARGUMENT.withDescription("서버 스트리밍 중 오류 발생!");
			responseObserver.onError(status.asRuntimeException());
			Thread.currentThread().interrupt();
		} finally {
			responseObserver.onCompleted();
		}
	}

	@Override
	public StreamObserver<StreamTextRequest> clientStream(StreamObserver<StringValue> responseObserver) {
		return new StreamObserver<>() {
			// 클라이언트가 보낸 메시지
			private final StringBuilder sb = new StringBuilder();

			@Override
			public void onNext(StreamTextRequest request) {
				sb.append(request.getStr());
			}

			@Override
			public void onError(Throwable t) {
				Status status = Status.INVALID_ARGUMENT.withDescription("에러!");
				responseObserver.onError(status.asRuntimeException());
			}

			@Override
			public void onCompleted() {
				StringValue response = StringValue.newBuilder()
						.setValue(sb.toString())
						.build();
				responseObserver.onNext(response);
				responseObserver.onCompleted();
			}
		};
	}

	@Override
	public StreamObserver<StreamRequest> twoWayStream(StreamObserver<StringValue> responseObserver) {
		return new StreamObserver<>() {
			@Override
			public void onNext(StreamRequest request) {
				long times = request.getTimes();
				String result;

				if (times < 50) {
					result = "50 이하";
				} else if (times > 149) {
					result = "150 넘음!";
				} else {
					result = "50 ~ 150임";
				}

				StringValue response = StringValue.newBuilder()
						.setValue(result)
						.build();

				// 클라이언트가 보낼 때마다 즉시 응답
				responseObserver.onNext(response);
			}

			@Override
			public void onError(Throwable t) {
				Status status = Status.INVALID_ARGUMENT.withDescription("에러!");
				responseObserver.onError(status.asRuntimeException());
			}

			@Override
			public void onCompleted() {
				responseObserver.onCompleted();
			}
		};
	}

}

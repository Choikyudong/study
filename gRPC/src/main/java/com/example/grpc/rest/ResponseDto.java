package com.example.grpc.rest;

import java.util.List;

public record ResponseDto(String title, List<MessageDetailDto> messages) {
}

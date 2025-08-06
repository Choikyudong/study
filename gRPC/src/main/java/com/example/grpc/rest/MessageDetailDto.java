package com.example.grpc.rest;

import java.time.LocalDateTime;

public record MessageDetailDto(Long id, String content, LocalDateTime createdAt) {
}

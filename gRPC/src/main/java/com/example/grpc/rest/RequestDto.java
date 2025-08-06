package com.example.grpc.rest;

import com.example.grpc.MyEnum;

public record RequestDto(Long id, String text, MyEnum myEnum) {
}

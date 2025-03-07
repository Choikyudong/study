package com.example.savepost.dto;

import java.util.List;

public record PostSaveDto(String subject, String content, List<ContentSaveDto> contents) {
}

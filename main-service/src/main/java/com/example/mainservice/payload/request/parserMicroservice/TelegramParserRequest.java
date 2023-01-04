package com.example.mainservice.payload.request.parserMicroservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramParserRequest {
    private String requestMassage;
    private Integer postsCount;
}

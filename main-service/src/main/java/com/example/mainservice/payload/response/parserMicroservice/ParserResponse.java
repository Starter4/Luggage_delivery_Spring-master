package com.example.mainservice.payload.response.parserMicroservice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParserResponse {
    private String title;
    private String text;
    private String newsLink;
    private byte[] image;
    private String source;
}

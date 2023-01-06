package com.example.mainservice.payload.response.parserMicroservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualNews {
    private String newsTitle;
    private String newsText;
    private String publishedDate;
    private String newsLink;
    private String newsResource;
    private String imageLink;
    private String resourceType;
}

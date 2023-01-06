package com.example.mainservice.payload.request.parserMicroservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParserRequest {
    private String query;
    private List<String> selectedTags;
    //private List<String> mediaList;
    private String period;
}

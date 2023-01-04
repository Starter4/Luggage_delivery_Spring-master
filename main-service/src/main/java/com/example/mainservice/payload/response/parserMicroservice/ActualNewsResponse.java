package com.example.mainservice.payload.response.parserMicroservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualNewsResponse {
    private List<ActualNews> actualNews;
}

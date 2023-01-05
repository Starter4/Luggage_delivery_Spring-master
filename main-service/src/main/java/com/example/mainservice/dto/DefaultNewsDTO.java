package com.example.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultNewsDTO {
    private Long id;
    private Date created;
    private Date updated;
    private String lastModifiedBy;
    private String title;
    private String newsInfo;
    private String link;
    private boolean active;
    private String source;
    private List<String> tagList;
}

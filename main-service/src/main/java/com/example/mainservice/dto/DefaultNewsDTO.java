package com.example.mainservice.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class DefaultNewsDTO {
    private Long id;
    private Date created;
    private Date updated;
    private String lastModifiedBy;
    private String title;
    private String newsInfo;
    private String link;
    private String imageLink;
    private boolean active;
    private String source;
    private List<String> tagList;
}

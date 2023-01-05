package com.example.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MediaPlatformDTO {
    private Long id;
    private Date created;
    private Date updated;
    private String createdBy;
    private String lastModifiedBy;
    private String mediaPlatform;
    private boolean active;
}

package com.example.mainservice.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaPlatformDTO {
    private Long id;
    private Date created;
    private Date updated;
    private String createdBy;
    private String lastModifiedBy;
    private String platformName;
    private boolean active;
}

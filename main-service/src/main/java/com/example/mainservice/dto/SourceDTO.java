package com.example.mainservice.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SourceDTO {
    private Long id;
    private String sourceName;
    private Date created;
    private Date updated;
    private String createdBy;
    private String lastModifiedBy;
    private boolean enable;
    private MediaPlatformDTO mediaPlatformDTO;
    private Set<DefaultNewsDTO> defaultNewsDTOSet;
    private Set<UserDTO> userDTOSet;
}

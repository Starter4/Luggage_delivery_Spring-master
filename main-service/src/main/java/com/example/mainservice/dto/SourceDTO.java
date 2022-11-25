package com.example.mainservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SourceDTO {
    private Long id;
    private Date created;
    private Date updated;
    private String createdBy;
    private String lastModifiedBy;
    private boolean enable;
    private MediaPlatformDTO mediaPlatformDTO;
    private Set<DefaultNewsDTO> defaultNewsDTOSet;
    private Set<UserDTO> userDTOSet;
}

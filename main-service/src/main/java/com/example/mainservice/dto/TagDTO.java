package com.example.mainservice.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDTO {
    private Long id;
    private String tagName;
    private Date created;
    private Date updated;
    private String createdBy;
    private String lastModifiedBy;
    private boolean enable;
    private Set<UserDTO> userDTOSet;
    private Set<DefaultNewsDTO> defaultNewsDTOSet;
}

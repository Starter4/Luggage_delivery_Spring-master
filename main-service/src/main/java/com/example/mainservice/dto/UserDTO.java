package com.example.mainservice.dto;

import com.example.mainservice.entity.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String login;
    private Date lastOnline;
    private String userPhone;
    private int age;
    private boolean enable;
    private Role userRole;
    private Set<MailDTO> mailDTOSet;

}

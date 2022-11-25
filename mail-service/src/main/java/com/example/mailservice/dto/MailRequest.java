package com.example.mailservice.dto;

import com.example.mailservice.dto.enums.MailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest {
    private String username;
    private String email;
    private String title;
    private MailType mailType;
    private String information;
    private String token;
}

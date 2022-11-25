package com.example.mailservice.dto;

import com.example.mailservice.dto.enums.MailStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailResponse {
    MailStatus mailStatus;
}

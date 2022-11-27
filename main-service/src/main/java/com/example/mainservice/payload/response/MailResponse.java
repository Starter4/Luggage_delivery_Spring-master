package com.example.mainservice.payload.response;

import com.example.mainservice.entity.enums.MailStatus;
import lombok.Data;

@Data
public class MailResponse {
    private MailStatus mailStatus;
}

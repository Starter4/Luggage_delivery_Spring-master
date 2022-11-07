package com.example.mainservice.repository;

import com.example.mainservice.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail,Long> {
}

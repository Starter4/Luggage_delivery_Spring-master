package com.example.mainservice.repository;

import com.example.mainservice.entity.DefaultNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultNewsRepository extends JpaRepository<DefaultNews,Long> {
}

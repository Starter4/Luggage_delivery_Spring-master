package com.example.mainservice.repository;

import com.example.mainservice.entity.MediaPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaPlatformRepository extends JpaRepository<MediaPlatform,Long> {
}

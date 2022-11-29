package com.example.mainservice.repository;

import com.example.mainservice.entity.MediaPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaPlatformRepository extends JpaRepository<MediaPlatform,Long> {
    List<MediaPlatform> findAllByMediaPlatform(String name);
}

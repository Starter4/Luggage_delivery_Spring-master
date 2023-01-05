package com.example.mainservice.repository;

import com.example.mainservice.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceRepository extends JpaRepository<Source,Long> {
    List<Source> findAllByActive(boolean activeStatus);
    void deleteBySourceName(String sourceName);
    Source findBySourceName(String sourceName);
}

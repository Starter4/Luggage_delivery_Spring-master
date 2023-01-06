package com.example.mainservice.repository;

import com.example.mainservice.entity.DefaultNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DefaultNewsRepository extends JpaRepository<DefaultNews,Long> {

    List<DefaultNews> findAllByTitle(String title);
    Page<DefaultNews> findAll(Pageable pageable);
    void deleteByTitle(String title);
}

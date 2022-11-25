package com.example.mainservice.repository;

import com.example.mainservice.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByTagName(String tagName);
    @Override
    Page<Tag> findAll(Pageable pageable);
    Page<Tag> findTagsByUserTagSet_Login(String login, Pageable pageable);
    void deleteTagByTagName(String tagName);
    @Modifying
    @Query(value = "UPDATE tag SET active = :newStatus WHERE tag_name = :tagName", nativeQuery = true)
    void setTagValidity(@Param("newStatus") boolean newStatus, @Param("tagName") String tagName);
}

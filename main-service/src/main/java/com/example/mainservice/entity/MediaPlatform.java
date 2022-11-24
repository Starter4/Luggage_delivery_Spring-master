package com.example.mainservice.entity;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "media_platform")
public class MediaPlatform extends BaseEntity{

    @Column(name = "media")
    private String mediaPlatform;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "mediaPlatform")
    private Set<Source> sourceSet;

    @ManyToMany
    @JoinTable(name = "user_has_media_platform",
    joinColumns = {@JoinColumn(name = "media_platform_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> userMediaPlatformSet;
}

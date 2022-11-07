package com.example.mainservice.entity;

import javax.persistence.*;
import java.util.Set;

public class MediaPlatform extends BaseEntity{

    @Column(name = "media_platform")
    private String mediaPlatform;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "mediaPlatform")
    private Set<Source> sourceSet;

    @ManyToMany
    @JoinTable(name = "user_has_media_platform",
    joinColumns = {@JoinColumn(name = "id")},
    inverseJoinColumns = {@JoinColumn(name = "id")})
    private Set<User> userMediaPlatformSet;
}

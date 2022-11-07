package com.example.mainservice.entity;

import javax.persistence.*;
import java.util.Set;

public class Source extends BaseEntity{

    @Column(name = "source_name")
    public String sourceName;

    @Column(name = "active")
    private boolean active;

    @OneToMany
    @JoinColumn(name = "media_platform_id")
    private MediaPlatform mediaPlatform;

    @ManyToMany(mappedBy = "source")
    private DefaultNews defaultNews;

    @ManyToMany
    @JoinTable(name = "source_has_user",
    joinColumns = {@JoinColumn(name = "id")},
    inverseJoinColumns = {@JoinColumn(name = "id")})
    private Set<User> userSourceSet;
}

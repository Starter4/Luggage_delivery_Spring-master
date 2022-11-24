package com.example.mainservice.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "source")
public class Source extends BaseEntity{

    @Column(name = "sourse_name")
    public String sourceName;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private MediaPlatform mediaPlatform;

    @OneToMany(mappedBy = "source")
    private Set<DefaultNews> defaultNewsSet;

    @ManyToMany
    @JoinTable(name = "source_has_user",
    joinColumns = {@JoinColumn(name = "source_id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> userSourceSet;
}

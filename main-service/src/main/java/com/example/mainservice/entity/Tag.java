package com.example.mainservice.entity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

public class Tag extends BaseEntity{

    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "active")
    private boolean active;

    @ManyToMany
    @JoinTable(name = "tag_has_user",
    joinColumns = {@JoinColumn(name = "id")},
    inverseJoinColumns = {@JoinColumn(name = "id")})
    private Set<User> userTagSet;

    @ManyToMany(mappedBy = "tagSet")
    private Set<DefaultNews> defaultNewsSet;
}

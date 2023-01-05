package com.example.mainservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "source")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Source /*extends BaseEntity*/{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //@CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    /*@LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date updated;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;*/

    @Column(name = "source_name")
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

package com.example.mainservice.entity;

import com.example.mainservice.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date updated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_online")
    private Date lastOnline;

    @NotBlank
    @Pattern(regexp = "\\w{4,64}")
    @Size(min = 4, max = 64, message = "length must be from 4 to 64 characters")
    @Column(name = "username")
    private String username;

    @NotNull
    @Pattern(regexp = "^([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$")
    @Column(name = "login")
    @Email
    private String login;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁёІіЇїЄє]{1,40}$")
    @Size(min = 1, max = 64, message = "length must be from 1 to 64 characters")
    @Column(name = "firstname")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zА-Яа-яЁёІіЇїЄє]{1,40}$")
    @Size(min = 1, max = 64, message = "length must be from 1 to 64 characters")
    @Column(name = "lastname")
    private String lastName;

    @NotBlank
    @Size(min = 8, max = 64, message = "length must be from 8 to 64 characters")
    @Column(name = "password")
    private String password;

    @Pattern(regexp = "\\(?\\+?[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})? ?(\\w{1,10}\\s?\\d{1,6})?")
    @Column(name = "phone_number")
    private String userPhone;

    @Column(name = "age")
    private int age;

    @Column(name = "active")
    private boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Mail> mailSet;

    @OneToMany(mappedBy = "user")
    private Set<ConfirmationToken> confirmationTokenSet;

    @ManyToMany(mappedBy = "userTagSet")
    private Set<Tag> tagSet;

    @ManyToMany(mappedBy = "userSourceSet")
    private Set<Source> sourceSet;

    @ManyToMany(mappedBy = "userMediaPlatformSet")
    private Set<MediaPlatform> mediaPlatformSet;

    @OneToMany(mappedBy = "user")
    private Set<ConfirmationToken> confirmationTokens;
}

package com.example.mainservice.entity;

import com.example.mainservice.entity.enums.MailType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "mail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @NotNull
    @Size(max = 64)
    @Column(name = "title")
    private String title;

    @Size(max = 500)
    @Column(name = "mail_info")
    private String mailInfo;

    @Size(max = 256)
    @Column(name = "link")
    private String link;

    @Column(name = "mail_type")
    @Enumerated(EnumType.STRING)
    private MailType mailType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}

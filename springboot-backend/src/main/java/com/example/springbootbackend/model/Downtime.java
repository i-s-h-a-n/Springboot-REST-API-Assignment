package com.example.springbootbackend.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "downtime")
public class Downtime {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "flow_name")
    private String flowName;

    @Column(name = "downtime_from")
    private LocalDateTime downFrom;

    @Column(name = "downtime_to")
    private LocalDateTime downTo;
}

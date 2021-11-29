package com.softserve.teachua.model;

import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@Builder
@With
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelCenterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long autogeneratedId;

    private Long centerExternalId;

    private String name;

    private String cityName;

    private String address;

    private String coordinates;

    private String district;

    private String station;

    @Column(length = 3000)
    private String webContact;

    private String phone;

    @Column(length = 3000)
    private String description;
}

package com.luppy.parkingppak.domain;

import com.luppy.parkingppak.domain.enumclass.NaviType;
import com.luppy.parkingppak.domain.enumclass.OilType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private OilType oilType;
    @Enumerated(EnumType.STRING)
    private NaviType naviType;
    private Boolean verified;
    private String provider;

    @OneToOne
    private Card card;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private List<Travel> travelList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="account_parkinglot_list")
    private List<ParkingLot> parkingLotList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="account_gasstation_list")
    private List<GasStation> gasStationList = new ArrayList<>();
}

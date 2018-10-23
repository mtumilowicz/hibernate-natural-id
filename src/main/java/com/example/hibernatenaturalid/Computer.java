package com.example.hibernatenaturalid;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mtumilowicz on 2018-10-23.
 */
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Computer {
    @Id
    Integer id;
    
    @NaturalId
    Long serialNumber;
    
    @NaturalId
    String macAddress;
}

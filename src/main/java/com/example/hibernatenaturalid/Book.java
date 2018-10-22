package com.example.hibernatenaturalid;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by mtumilowicz on 2018-10-22.
 */
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class Book {
    @Id
    Integer id;
    
    @NaturalId
    String isbn;
}

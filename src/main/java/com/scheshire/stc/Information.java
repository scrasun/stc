package com.scheshire.stc;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Model for stored information
 */
@Getter
@Setter
@Entity
@Table(name = "information")
public class Information {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column(name="name")
    private String name;
    
    @Convert(converter = EncryptionConverter.class)
    @Column(name="secrets")
    private String secrets;

}

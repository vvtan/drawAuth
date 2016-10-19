package com.victor.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by vvtan on 16/10/19.
 */
@Data
@Entity
public class Auth implements Serializable {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
    private Long id;

    @Column
    private String share_resources;

    @Column
    private String image_matrix;

    @Column
    private String relation_check;

    @Column
    private String message;

}

package com.bay.member.demo.entity;

import com.bay.member.demo.entity.impl.TimeStampImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends TimeStampImpl implements Serializable {

    private static final long serialVersionUID = -9082834988260886994L;

    @Id
    private String username;
    private String password;
    private String address;
    private String phone;
    private Integer salary;
    private String referenceCode;
    private String memberType;

}

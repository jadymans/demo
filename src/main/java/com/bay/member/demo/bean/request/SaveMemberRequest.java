package com.bay.member.demo.bean.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveMemberRequest implements Serializable {

    private static final long serialVersionUID = 6584836398970761231L;

    private String username;
    private String password;
    private String address;
    private String phone;
    private Integer salary;
}

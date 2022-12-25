package com.bay.member.demo.bean.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRestResponse implements Serializable {

    private static final long serialVersionUID = -7183172852759793105L;


    private String status;
    private String description;
    private Object data;
}

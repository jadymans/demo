package com.bay.member.demo.component;

import com.bay.member.demo.bean.request.SaveMemberRequest;
import com.bay.member.demo.bean.response.MemberRestResponse;
import com.bay.member.demo.entity.Member;
import com.bay.member.demo.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static com.bay.member.demo.constant.DemoConstant.FailStatus.DATA_NOT_FOUND;
import static com.bay.member.demo.constant.DemoConstant.FailStatus.MEMBER_SALARY_LESS_THAN_15000;
import static com.bay.member.demo.constant.DemoConstant.MemberType.*;
import static com.bay.member.demo.constant.DemoConstant.SuccessStatus.SUCCESS;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberComponent {

    private final MemberRepo memberRepo;

    public MemberRestResponse getMember() {

        var resp = memberRepo.findAll();
        if (!isEmpty(resp)) {
            log.info("Found Member : {} rows", resp.size());
            return setSuccessResponse(resp);
        } else {
            log.info("Member Not Found!!");
            return MemberRestResponse.builder()
                    .status(DATA_NOT_FOUND)
                    .description("Member Not Found!!")
                    .build();
        }
    }
    public MemberRestResponse getMember(String username) {

        var resp = memberRepo.findById(username)
                .orElseThrow(() -> new NoResultException("member not found by username : " + username));

        if (resp.getSalary() < 15000) {
            return MemberRestResponse.builder()
                    .status(MEMBER_SALARY_LESS_THAN_15000)
                    .description("Member salary less than 15,000")
                    .data(null)
                    .build();
        } else {
            return setSuccessResponse(resp);
        }
    }

    public MemberRestResponse saveMember(SaveMemberRequest request) throws Exception {
        try{
            var salary = request.getSalary();
            var phoneNo = request.getPhone();

            var member = Member.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .address(request.getPassword())
                    .phone(phoneNo)
                    .salary(salary)
                    .referenceCode(getRefCode(phoneNo))
                    .memberType(getMemberType(salary))
                    .build();

            memberRepo.save(member);

            return setSuccessResponse(member);
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }

    }

    private String getMemberType(Integer salary){
        if(salary > 50000){
            return PLATINUM;
        }else if(salary >= 30000){
            return GOLD;
        }else{
            return SILVER;
        }
    }

    private String getRefCode(String phoneNo){
        var formatter = new SimpleDateFormat("yyyyMMdd");
        var now = LocalDate.now();
        var dateNow = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
        var dateFormat = formatter.format(dateNow);
        var fourLastPhone = phoneNo.length() <= 4 ? phoneNo : phoneNo.substring(phoneNo.length() - 4);

        return dateFormat.concat(fourLastPhone);
    }

    private MemberRestResponse setSuccessResponse(Object data){
        return MemberRestResponse.builder()
                .status(SUCCESS)
                .description("success")
                .data(data)
                .build();
    }
}


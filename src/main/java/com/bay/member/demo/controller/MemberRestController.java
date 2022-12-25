package com.bay.member.demo.controller;

import com.bay.member.demo.bean.request.SaveMemberRequest;
import com.bay.member.demo.bean.response.MemberRestResponse;
import com.bay.member.demo.component.MemberComponent;
import com.bay.member.demo.constant.DemoConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;

import static com.bay.member.demo.constant.DemoConstant.ErrorStatus.SOMETHING_WRONG;
import static com.bay.member.demo.constant.DemoConstant.FailStatus.DATA_NOT_FOUND;
import static com.bay.member.demo.constant.DemoConstant.FailStatus.MEMBER_SALARY_LESS_THAN_15000;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberRestController {

    private final MemberComponent memberComponent;

    @GetMapping("") // Get All Member
    public ResponseEntity<MemberRestResponse> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(memberComponent.getMember());

        } catch (Exception e) {
            log.error("Get Member Fail : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MemberRestResponse.builder()
                            .status(SOMETHING_WRONG)
                            .description("Get Member Fail : " + e.getMessage())
                            .build()
                    );
        }
    }

    @GetMapping("/get") // Get by Username
    public ResponseEntity<MemberRestResponse> getByUsername(@RequestParam String username) {
        try {
//            TODO: test username = null

            return ResponseEntity.status(HttpStatus.OK).body(memberComponent.getMember(username));

        } catch (NoResultException noResultException) {
            log.error(noResultException.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MemberRestResponse.builder()
                            .status(DATA_NOT_FOUND)
                            .description(noResultException.getMessage())
                            .build()
                    );

        } catch (Exception exception) {
            log.error("Get Member By Username Fail : {} -> {}", username, exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MemberRestResponse.builder()
                            .status(SOMETHING_WRONG)
                            .description("Get Member By Username Fail : " + username + " -> " + exception.getMessage())
                            .build()
                    );
        }
    }

    @PostMapping("/saveMember")
    public ResponseEntity<MemberRestResponse> getByUsername(@RequestBody SaveMemberRequest request) {
        try {
            if (isEmpty(request.getUsername())
                    || isEmpty(request.getPassword())
                    || isEmpty(request.getSalary())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(MemberRestResponse.builder()
                                .status(SOMETHING_WRONG)
                                .description("username or password or salary is empty")
                                .build()
                        );
            }

            if (request.getSalary() < 15000) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(MemberRestResponse.builder()
                                .status(MEMBER_SALARY_LESS_THAN_15000)
                                .description("Member salary less than 15,000")
                                .build()
                        );
            }

            var memberRestResponse = memberComponent.saveMember(request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(memberRestResponse);

        } catch (Exception exception) {
            log.error("Save member Fail : " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MemberRestResponse.builder()
                            .status(SOMETHING_WRONG)
                            .description("Save member Fail : " + exception.getMessage())
                            .build()
                    );
        }
    }
}

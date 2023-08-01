package com.web.stard.controller;

import com.web.stard.domain.Member;
import com.web.stard.service.MemberService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter @Setter
@RestController
//@Controller
@RequestMapping("/user/mypage")
public class MyPageController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* 정보 반환 */
    @GetMapping("/update")
    public Member getMember(@RequestParam("id") String id) {
        Member member = memberService.find(id);
        return member;
    }

    /* 닉네임 중복 확인 */
    @PostMapping("/check/nickname")
    public boolean checkNickname(@RequestParam("nickname") String nickname) {
        return memberService.checkNickname(nickname);
        // true -> 이미 존재 (사용 불가), false -> 없음 (사용 가능)
    }

    /* 비밀번호 확인 (필요한 경우 사용) */
    @PostMapping("/check/password")
    public boolean checkPassword(@RequestParam("id") String id,
                                 @RequestParam("password") String password) {
        return memberService.checkPw(id, password); // true -> 맞음 / false -> 틀림
    }

    /* 닉네임 변경 */
    @PostMapping("/update/nickname")
    public ResponseEntity<String> updateNickname(@RequestParam("id") String id,
                                                 @RequestParam("nickname") String nickname) {
        memberService.updateMember("nickname", id, nickname);
        return ResponseEntity.ok().build();
    }

    /* 이메일 변경 */
    @PostMapping("/update/email")
    public ResponseEntity<String> updateEmail(@RequestParam("id") String id,
                                              @RequestParam("email") String email) {
        memberService.updateMember("email", id, email);
        return ResponseEntity.ok().build();
    }

    /* 전화번호 변경 */
    @PostMapping("/update/phone")
    public ResponseEntity<String> updatePhone(@RequestParam("id") String id,
                                              @RequestParam("phone") String phone) {
        memberService.updateMember("phone", id, phone);
        return ResponseEntity.ok().build();
    }

    /* 비밀번호 변경 */
    @PostMapping("/update/password")
    public ResponseEntity<String> updatePassword(@RequestParam("id") String id,
                                                 @RequestParam("password") String password,
                                                 @RequestParam("newPassword") String newPassword) {
        // 패스워드 일치 여부 확인
        if (!memberService.checkPw(id, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
        }

        memberService.updateMember("password", id, newPassword);
        return ResponseEntity.ok().build();
    }

    /* 거주지 변경 */
    @PostMapping("/update/address")
    public ResponseEntity<String> updateAddress(@RequestParam("id") String id,
                                                @RequestParam("city") String city,
                                                @RequestParam("district") String district) {
        memberService.updateAddress(id, city, district);
        return ResponseEntity.ok().build();
    }

    /* 관심분야 변경 */
    @PostMapping("/update/interest")
    public ResponseEntity<String> updateInterest(@RequestParam("id") String id,
                                                 @RequestParam("interestList") List<String> interests) {
        memberService.updateInterest(id, interests);
        return ResponseEntity.ok().build();
    }

    /* 회원 탈퇴 (아직 기능 X 그냥 메소드만 만듦) */
    @PostMapping("/delete")
    public void delete(@RequestParam("id") String id) {
        memberService.deleteMember(id);
    }
}

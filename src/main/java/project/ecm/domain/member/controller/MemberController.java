package project.ecm.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.ecm.domain.member.dto.MemberSignUpDto;
import project.ecm.domain.member.service.MemberService;

@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 가입", description = "프로필 정보만 입력, 주소 정보는 별도 API 분리")
    @PostMapping("/")
    public ResponseEntity<?> signup(@Valid @RequestBody MemberSignUpDto memberSignUpDto) {
        memberService.signup(memberSignUpDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원 정보 수정")
    @PutMapping("/")
    public ResponseEntity<?> update() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/")
    public ResponseEntity<?> delete() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "이메일 중복 확인, 유효한 이메일 형식인지 검증")
    @GetMapping("/validate/{email}")
    public ResponseEntity<?> validateEmail(@PathVariable String email) {
        return ResponseEntity.ok().build();
    }

}

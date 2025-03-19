package project.ecm.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.ecm.domain.member.dto.MemberSignUpDto;
import project.ecm.domain.member.entity.Member;
import project.ecm.domain.member.exception.MemberExceptionType;
import project.ecm.domain.member.repository.MemberRepository;
import project.ecm.global.exception.BaseException;

import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(MemberSignUpDto memberSignUpDto) {
        if (memberRepository.existsByEmail(memberSignUpDto.email())) {
            throw new BaseException(MemberExceptionType.DUPLICATED_USERNAME);
        }

        Member member = Member.of(memberSignUpDto);

        member.encodePassword(passwordEncoder.encode(memberSignUpDto.password()));

        memberRepository.save(member);
    }

    public void update() {
        log.info("update");
    }

    public void delete() {
        log.info("delete");
    }

    public boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);

        if (email == null || !pattern.matcher(email).matches()) {
            return false;
        }

        return !memberRepository.existsByEmail(email);
    }

}

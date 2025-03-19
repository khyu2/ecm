package project.ecm.global.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.ecm.global.exception.BaseException;
import project.ecm.global.exception.BaseExceptionType;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;
    private final EmailVerificationService emailVerificationService;

    public void sendEmail(String toEmail) {
        String title = "ECM 서비스 이메일 인증 코드";
        String code = createCode();

        SimpleMailMessage emailForm = createEmailForm(toEmail, title, code);
        try {
            emailSender.send(emailForm);
            emailVerificationService.saveVerificationCode(toEmail, code);
        } catch (RuntimeException e) {
            log.error("MailService.sendEmail exception occur toEmail: {}, " +
                    "title: {}, code: {}", toEmail, title, code);
            throw new BaseException(BaseExceptionType.UNABLE_TO_SEND_EMAIL);
        }
    }

    public boolean verifyCode(String email, String inputCode) {
        return emailVerificationService.verifyCode(email, inputCode);
    }

    // 발신할 이메일 데이터 세팅
    private SimpleMailMessage createEmailForm(String toEmail,
                                              String title,
                                              String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(title);
        message.setText(text);

        return message;
    }

    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("Random code create error");
            throw new BaseException(BaseExceptionType.UNKNOWN_SERVER_ERROR);
        }
    }
}
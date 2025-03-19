package project.ecm.domain.member.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import project.ecm.domain.member.dto.MemberSignUpDto;
import project.ecm.global.utils.BaseTimeEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE tb_member SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
@Table(name = "tb_member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 15)
    private String tel;

    @Column(length = 100)
    private String profileImage;

    @Column(nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private LocalDateTime deletedAt;

    @OneToOne
    @JoinColumn(name = "member_address_id")
    private MemberAddress address;

    public static Member of(MemberSignUpDto memberSignUpDto) {
        return Member.builder()
                .email(memberSignUpDto.email())
                .password(memberSignUpDto.password())
                .nickname(memberSignUpDto.nickname())
                .tel(memberSignUpDto.tel())
                .birth(memberSignUpDto.birth())
                .profileImage(memberSignUpDto.profileImage())
                .role(MemberRole.USER)
                .build();
    }

    public void encodePassword(String password) {
        this.password = password;
    }
}

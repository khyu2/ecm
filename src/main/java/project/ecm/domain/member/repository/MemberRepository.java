package project.ecm.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ecm.domain.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}

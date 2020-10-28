package br.ufrn.dct.apf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ufrn.dct.apf.model.Member;

@Repository("memberRepository")
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m WHERE m.project.id = :projectId")
    List<Member> findByProjectId(@Param("projectId")  Long projectId);

    @Query("SELECT m FROM Member m WHERE m.user.id = :userId AND m.project.id = :projectId")
    Member findByProjectIdAndUserId(
        @Param("projectId") Long projectId,
        @Param("userId") Long userId
    );
}

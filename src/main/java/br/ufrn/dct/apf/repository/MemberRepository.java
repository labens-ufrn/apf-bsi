package br.ufrn.dct.apf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufrn.dct.apf.model.Member;

@Repository("memberRepository")
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    public List<Member> findByProjectId(Long projectId);
    
    public List<Member> findByProjectIdAndUserId(Long projectId, Long userId);

}

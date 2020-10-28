package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.repository.MemberRepository;

import static java.util.Objects.isNull;

@Service
public class MemberService extends AbstractService {

    @Autowired
    private MemberRepository repository;

    public List<Member> findAll() {
        return repository.findAll();
    }

    public Member findOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Member save(Member member) throws BusinessRuleException {
        checkMemberNull(member);

        if (isMember(member.getProject().getId(), member.getUser().getId())) {
            return repository.saveAndFlush(member);
        }

        return member;
    }

    public Member getMember(Long projectId, Long memberId) {
        return repository.findByProjectIdAndUserId(projectId, memberId);
    }

    public Boolean isMember(Long projectId, Long memberId) {
        return getMember(projectId, memberId) != null;
    }

    public List<Member> getMembersByProject(Long projectId) {
        return repository.findByProjectId(projectId);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void checkMemberNull(Member member) throws BusinessRuleException {
        if (isNull(member) || isNull(member.getProject()) || isNull(member.getUser())) {
            throw new BusinessRuleException("error.member.service.member.is.null");
        }
    }
}

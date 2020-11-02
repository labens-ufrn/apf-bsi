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

    /**
     * Checks whether the member object is already part of the team, if not, the object is saved. 
     * If the member is already linked to the project, it returns the member instance.
     * @param member A member instance with no id.
     * @return the member instance.
     * @throws BusinessRuleException If the parameter is null.
     */
    public Member save(Member member) throws BusinessRuleException {
        checkMemberNull(member);
        Member memberDB = getMember(member.getProject().getId(), member.getUser().getId());
        if (isNull(memberDB)) {
            return repository.saveAndFlush(member);
        }
        return memberDB;
    }

    public Member getMember(Long projectId, Long userId) {
        return repository.findByProjectIdAndUserId(projectId, userId);
    }

    public Boolean isMember(Long projectId, Long userId) {
        return getMember(projectId, userId) != null;
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

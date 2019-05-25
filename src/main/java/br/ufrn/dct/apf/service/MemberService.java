package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.repository.MemberRepository;

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
        Member memberDB = getMember(member);
        if (memberDB == null) {
            return repository.saveAndFlush(member);
        }
        return memberDB;
    }

    private Member getMember(Member member) {
        List<Member> team = repository.findByProjectIdAndUserId(
                    member.getProject().getId(), member.getUser().getId());
        if (team.isEmpty()) {
            return null;
        }
        return team.get(0);
    }

    public List<Member> findByProjectIdAndUserId(Long projectId, Long userId) {
        return repository.findByProjectIdAndUserId(projectId, userId);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void checkMemberNull(Member member) throws BusinessRuleException {
        if (checkNull(member) || checkNull(member.getProject()) || checkNull(member.getUser())) {
            throw new BusinessRuleException("error.member.service.member.is.null");
        }
    }
}

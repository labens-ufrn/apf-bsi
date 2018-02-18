package br.ufrn.dct.apf.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.dct.apf.model.Member;
import br.ufrn.dct.apf.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;

    public List<Member> findAll() {
        return repository.findAll();
    }

    public Member findOne(Long id) {
        return repository.findOne(id);
    }

    public Member save(Member member) {
        return repository.saveAndFlush(member);
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}

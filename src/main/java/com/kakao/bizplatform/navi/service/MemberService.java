package com.kakao.bizplatform.navi.service;

import com.kakao.bizplatform.navi.domain.Member;
import com.kakao.bizplatform.navi.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Member save(Member member) {
        return repository.save(member);
    }

    @Transactional
    public void update(List<Member> addedMemberList, int i) {
        Member one = repository.getOne(addedMemberList.get(i).getSeq());
        one.setName("" + i);
//        System.out.println(addedMemberList.get(i));
    }
}

package com.kakao.bizplatform.navi.member;

import com.kakao.bizplatform.navi.domain.Member;
import com.kakao.bizplatform.navi.domain.MemberRepository;
import com.kakao.bizplatform.navi.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class MemberTest {
    @Autowired
    MemberRepository repository;

    @Autowired
    MemberService memberService;

    @AfterEach
    void afterEach() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("insert 테스트")
    void insert() {
        Member member = Member.builder().name("name").nick("nick").build();
        repository.save(member);

        List<Member> memberList = repository.findAll();

        Member member1 = memberList.get(0);
        assertThat(member.getName()).isEqualTo(member1.getName());
        assertThat(member.getNick()).isEqualTo(member1.getNick());
    }

    @Test
    @DisplayName("insert update 시간 비교")
    void insertAndUpdate() {
        int repeatTimes = 100_000;
        List<Member> memberList = new ArrayList<>();

        for (int i = 0; i < repeatTimes; i++) {
            memberList.add(Member.builder().name("name").nick("nick").build());
        }

        long insertStartTime = System.currentTimeMillis();
        for (int i = 0; i < repeatTimes; i++) {
            memberService.save(memberList.get(i));
        }
        long insertEndTime = System.currentTimeMillis();

        System.out.println(String.format("시간: %d", insertEndTime - insertStartTime));

        long updateStartTime = System.currentTimeMillis();
        for (int i = 0; i < repeatTimes; i++) {
            memberService.update(memberList, i);
        }
        long updateEndTime = System.currentTimeMillis();

        System.out.println(String.format("시간: %d", updateEndTime - updateStartTime));
    }


}
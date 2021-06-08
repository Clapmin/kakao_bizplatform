package com.kakao.bizplatform.navi.domain;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String nick;

    @Builder
    public Member(String name, String nick) {
        this.name = name;
        this.nick = nick;
    }
}

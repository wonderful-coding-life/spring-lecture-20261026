package com.example.demo.service;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse subscribe(MemberRequest memberRequest) {
        // 데이터베이스에 회원 정보 저장
        var member = Member.builder()
                .name(memberRequest.getName())
                .email(memberRequest.getEmail())
                .age(memberRequest.getAge()).build();
        memberRepository.save(member);

        // 웰컴 이메일 전송
        // 가입 포인트 누적

        return mapToMemberResponse(member);
    }

    @Transactional
    public List<MemberResponse> subscribeBatch(List<MemberRequest> memberRequests) {
        return memberRequests.stream().map(this::subscribe).toList();
    }

    private MemberResponse mapToMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .age(member.getAge()).build();
    }
}

package com.example.demo.controller;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.entity.Member;
import com.example.demo.exception.MemberNotFoundException;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse postMembers(@RequestBody MemberRequest memberRequest) {
        return memberService.subscribe(memberRequest);
    }

    @PostMapping("/api/v2/members")
    public List<MemberResponse> postMembersBatch(@RequestBody List<MemberRequest> memberRequests) {
        return memberService.subscribeBatch(memberRequests);
    }

    @GetMapping("/members")
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/members/{id}")
    public Member getMembersById(@PathVariable("id") Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    @PutMapping("/members/{id}")
    public Member putMembers(@PathVariable("id") Long id, @RequestBody Member member) {
        memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        member.setId(id);
        return memberRepository.save(member);
    }

    @PatchMapping("/members/{id}")
    public Member patchMembers(@PathVariable("id") Long id, @RequestBody Member patch) {
        var member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        if (patch.getName() != null) member.setName(patch.getName());
        if (patch.getEmail() != null) member.setEmail(patch.getEmail());
        if (patch.getAge() != null) member.setAge(patch.getAge());
        return memberRepository.save(member);
    }

    @DeleteMapping("/members/{id}")
    public void deleteMembers(@PathVariable("id") Long id) {
        var member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);
    }












    /****************************************/

    @PutMapping("/members/{id}/photo")
    public void putMemberPhoto(@PathVariable("id") Long id, @RequestPart("photo") MultipartFile photo) throws IOException {
        var member = memberRepository.findById(id).orElseThrow();

        // 회원 프로필 사진 저장
        String originalName = photo.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String filename = UUID.randomUUID() + extension;
        Path uploadDir = Paths.get("./uploads/images");
        Files.createDirectories(uploadDir);
        photo.transferTo(uploadDir.resolve(filename));

        member.setProfilePhotoUrl("/images/" + filename);
        memberRepository.save(member);
    }
}

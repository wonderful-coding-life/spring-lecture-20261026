package com.example.demo.controller;

import com.example.demo.entity.Member;
import com.example.demo.exception.MemberNotFoundException;
import com.example.demo.repository.MemberRepository;
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

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    public Member postMembers(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    @GetMapping("/members")
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/members/{id}")
    public Member getMembersById(@PathVariable("id") Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
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

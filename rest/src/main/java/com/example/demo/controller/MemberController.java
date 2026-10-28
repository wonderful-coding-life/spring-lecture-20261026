package com.example.demo.controller;

import com.example.demo.dto.MemberRequest;
import com.example.demo.dto.MemberResponse;
import com.example.demo.entity.Member;
import com.example.demo.exception.MemberNotFoundException;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "회원", description = "회원 등록, 조회, 수정 및 프로필 사진 관리 API")
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/members")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원 등록", description = "이름, 이메일, 나이를 입력해 새 회원을 등록합니다.")
    public MemberResponse postMembers(@RequestBody MemberRequest memberRequest) {
        return memberService.subscribe(memberRequest);
    }

    @PostMapping("/api/v2/members")
    @Operation(summary = "회원 일괄 등록", description = "회원 정보 목록을 한 번의 요청으로 등록합니다.")
    public List<MemberResponse> postMembersBatch(@RequestBody List<MemberRequest> memberRequests) {
        return memberService.subscribeBatch(memberRequests);
    }

    @GetMapping("/members")
    @Operation(summary = "회원 전체 조회", description = "등록된 모든 회원 정보를 반환합니다.")
    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/members/{id}")
    @Operation(summary = "회원 단건 조회", description = "ID로 회원 정보를 조회합니다. 해당 ID가 없으면 404를 반환합니다.")
    public Member getMembersById(@Parameter(description = "조회할 회원 ID", example = "1") @PathVariable("id") Long id) {
        return memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
    }

    @PutMapping("/members/{id}")
    @Operation(summary = "회원 정보 전체 수정", description = "회원의 이름, 이메일, 나이 정보를 전달한 값으로 교체합니다.")
    public Member putMembers(@Parameter(description = "수정할 회원 ID", example = "1") @PathVariable("id") Long id, @RequestBody Member member) {
        memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        member.setId(id);
        return memberRepository.save(member);
    }

    @PatchMapping("/members/{id}")
    @Operation(summary = "회원 정보 부분 수정", description = "전달한 필드만 수정합니다. 생략하거나 null인 필드는 유지됩니다.")
    public Member patchMembers(@Parameter(description = "수정할 회원 ID", example = "1") @PathVariable("id") Long id, @RequestBody Member patch) {
        var member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        if (patch.getName() != null) member.setName(patch.getName());
        if (patch.getEmail() != null) member.setEmail(patch.getEmail());
        if (patch.getAge() != null) member.setAge(patch.getAge());
        return memberRepository.save(member);
    }

    @DeleteMapping("/members/{id}")
    @Operation(summary = "회원 삭제", description = "ID에 해당하는 회원을 삭제합니다.")
    public void deleteMembers(@Parameter(description = "삭제할 회원 ID", example = "1") @PathVariable("id") Long id) {
        var member = memberRepository.findById(id).orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);
    }












    /****************************************/

    @PutMapping("/members/{id}/photo")
    @Operation(summary = "프로필 사진 등록", description = "회원의 프로필 사진 파일을 업로드합니다.")
    public void putMemberPhoto(@Parameter(description = "사진을 등록할 회원 ID", example = "1") @PathVariable("id") Long id, @RequestPart("photo") MultipartFile photo) throws IOException {
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

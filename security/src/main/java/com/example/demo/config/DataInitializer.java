package com.example.demo.config;

import com.example.demo.entity.Member;
import com.example.demo.entity.Product;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (productRepository.count() == 0) {
            var products = List.of(
                    Product.builder().name("아이폰").price(1200000).build(),
                    Product.builder().name("맥북").price(1500000).build()
            );
            productRepository.saveAll(products);
        }
        if (memberRepository.count() == 0) {
            var passwordEncoder = new BCryptPasswordEncoder();
            var members = List.of(
                    Member.builder()
                            .name("seojun")
                            .password(passwordEncoder.encode("12345678"))
                            .authority("ROLE_USER").build(),
                    Member.builder()
                            .name("kwangcheol")
                            .password(passwordEncoder.encode("12345678"))
                            .authority("ROLE_ADMIN").build()
            );
            memberRepository.saveAll(members);
        }
    }
}

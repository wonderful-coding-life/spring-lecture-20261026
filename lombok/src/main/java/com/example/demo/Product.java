package com.example.demo;

import lombok.*;

//@Getter
//@Setter
//@EqualsAndHashCode
//@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private String description;
    private Integer price;
}

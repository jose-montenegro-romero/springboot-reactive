package com.nequi.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    private Long id;

    @Column(value = "name")
    private String name;

    @Column(value = "author")
    private String author;

}


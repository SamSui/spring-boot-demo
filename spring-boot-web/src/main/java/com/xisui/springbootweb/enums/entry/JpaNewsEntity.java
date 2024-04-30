package com.xisui.springbootweb.enums.entry;

import com.xisui.springbootweb.enums.JpaNewsStatusConverter;
import com.xisui.springbootweb.enums.NewsStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "t_jpa_news")
public class JpaNewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = JpaNewsStatusConverter.class)
    private NewsStatus status;
}

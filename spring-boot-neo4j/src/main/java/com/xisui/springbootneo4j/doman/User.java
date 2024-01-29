package com.xisui.springbootneo4j.doman;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class User {

    @Id
    private final String name;

    private Integer born;

    public User(Integer born, String name) {
        this.born = born;
        this.name = name;
    }
}

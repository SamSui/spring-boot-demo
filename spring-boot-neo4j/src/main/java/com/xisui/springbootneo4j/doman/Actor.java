package com.xisui.springbootneo4j.doman;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.ArrayList;
import java.util.List;

@RelationshipProperties
@Data
public class Actor {
	@Id
	@GeneratedValue
	private Long id;

	@TargetNode
	private final User person;

	private List<String> roles = new ArrayList<>();

	public Actor(User person) {
		this.person = person;
	}
}

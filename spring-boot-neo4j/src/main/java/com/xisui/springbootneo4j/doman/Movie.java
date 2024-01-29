package com.xisui.springbootneo4j.doman;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("Movie")
@Data
public class Movie {

	@Id
	private final String title;

	@Property("tagline")
	private final String description;

	@Relationship(type = "ACTED_IN", direction = Relationship.Direction.INCOMING)
	private List<Actor> actors = new ArrayList<>();		//自定义Actor关系

	@Relationship(type = "DIRECTED", direction = Relationship.Direction.INCOMING)
	private List<User> directors = new ArrayList<>();		//默认指向Person节点的关系

	private Integer released;

	public Movie(String title, String description) {
		this.title = title;
		this.description = description;
	}
}

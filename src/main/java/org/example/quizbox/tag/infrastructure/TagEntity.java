package org.example.quizbox.tag.infrastructure;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.quizbox.tag.domain.Tag;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Tag toDomain() {
        return new Tag(this.id, this.name);
    }

    public static TagEntity from(Tag tag) {
        return new TagEntity(tag.getId(), tag.getName());
    }
}

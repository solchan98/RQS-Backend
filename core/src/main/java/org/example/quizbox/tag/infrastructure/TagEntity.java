package org.example.quizbox.tag.infrastructure;

import jakarta.persistence.*;
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

    @Column(nullable = false, unique = true)
    private String name;

    public Tag toDomain() {
        return new Tag(this.id, this.name);
    }

    public static TagEntity from(Tag tag) {
        return new TagEntity(tag.getId(), tag.getName());
    }
}

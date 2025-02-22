package kr.ac.chosun.devossian.domain.tags.post_tag_mapping.domain;

import jakarta.persistence.*;
import kr.ac.chosun.devossian.domain.tags.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public PostTag(Tag tag, String uuid) {
        this.uuid = uuid;
        this.tag = tag;
    }
}

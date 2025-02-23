package kr.ac.chosun.devossian.domain.maintenance.domain;

import jakarta.persistence.*;
import kr.ac.chosun.devossian.domain.user.domain.User;
import kr.ac.chosun.devossian.global.BaseTimeEntity;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Maintenance extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) //DOC: 유저기능 추가로 인한 코드 변경
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "uuid", nullable = false)
    private String uuid;

    @Builder
    public Maintenance(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.uuid = UUID.randomUUID().toString();
    }

    public void updateTitle(String title){
        if(title != null){
            this.title = title;
        }
    }

    public void updateContent(String content){
        if(content != null){
            this.content = content;
        }
    }


}

package org.example.quizbox.preview.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.quizbox.common.Audit;

import java.util.List;
import java.util.Objects;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PreviewQuizPack extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    private Long taskId;

    @Setter
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Setter
    @Column(name = "keywords")
    @Convert(converter = StringListConverter.class)
    private List<String> keywords;

    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "quiz_pack_id")
    private List<PreviewQuiz> quizzes;

    public PreviewQuizPack(long taskId, long userId, String title, List<String> keywords) {
        this.taskId = taskId;
        this.userId = userId;
        this.title = title;
        this.keywords = keywords;
        this.initAudit(userId);
    }

    public void update(long userId, PreviewQuizPack previewQuizPack) {
        if (userId != this.userId) {
            // TODO:
            throw new RuntimeException("오토 퀴즈팩을 찾을 수 없습니다.(03)");
//            throw new BusinessException(404, "오토 퀴즈팩을 찾을 수 없습니다.(03)"); // 본인 퀴즈팩이 아닌 경우, 403이 아닌 404 예외
        }

        this.title = previewQuizPack.title;
        this.description = previewQuizPack.description;
        this.quizzes = previewQuizPack.quizzes;
        this.keywords = previewQuizPack.keywords;
        this.updateAudit(userId);
    }

    // TODO: 테스트 작성
    public PreviewQuizPackType getPreviewQuizPackType() {
        return Objects.isNull(taskId)
                ? PreviewQuizPackType.MANUAL
                : PreviewQuizPackType.AUTO;
    }
}

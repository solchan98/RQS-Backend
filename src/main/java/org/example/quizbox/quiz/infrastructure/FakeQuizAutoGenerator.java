package org.example.quizbox.quiz.infrastructure;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.example.quizbox.quiz.domain.Option;
import org.example.quizbox.quiz.domain.Quiz;
import org.example.quizbox.quiz.domain.QuizAutoGenerator;
import org.example.quizbox.quiz.domain.QuizContent;
import org.example.quizbox.quiz.domain.QuizPackMember;
import org.example.quizbox.tag.domain.Tags;
import org.springframework.stereotype.Component;

@Component
public class FakeQuizAutoGenerator implements QuizAutoGenerator {

    @Override
    public Set<Quiz> generate(QuizPackMember creator, Tags tags, int hopeCount) {
        List<Quiz> quizzes = List.of(
                new Quiz(creator, new QuizContent("Spring Data JPA는 무엇의 약자입니까?"),
                        Set.of(
                                new Option("Spring Data Java Persistence API", true),
                                new Option("Spring Data Java Programming API", false),
                                new Option("Spring Data JPA Application Programming Interface", false),
                                new Option("Spring Data JDBC Persistence API", false),
                                new Option("Spring Data REST API", false)
                        )
                ),
                new Quiz(creator, new QuizContent("JPA는 무엇을 위한 표준 사양입니까?"),
                        Set.of(
                                new Option("자바 객체 관계 매핑", true),
                                new Option("자바 스프링 프레임워크", false),
                                new Option("자바 웹 애플리케이션", false),
                                new Option("자바 데이터베이스 연결", false),
                                new Option("자바 스트림 처리", false)
                        )
                ),
                new Quiz(creator, new QuizContent("Hibernate는 무엇입니까?"),
                        Set.of(
                                new Option("JPA 구현체", true),
                                new Option("ORM 프레임워크", true),
                                new Option("데이터베이스", false),
                                new Option("웹 서버", false),
                                new Option("프로그래밍 언어", false)
                        )
                ),
                new Quiz(creator, new QuizContent("Spring Data JPA의 주요 기능은 무엇입니까?"),
                        Set.of(
                                new Option("데이터 접근 계층 간소화", true),
                                new Option("데이터베이스 쿼리 작성 단순화", true),
                                new Option("트랜잭션 관리", true),
                                new Option("웹 애플리케이션 개발", false),
                                new Option("보안 기능 제공", false)
                        )
                ),
                new Quiz(creator, new QuizContent("___는 객체와 관계형 데이터베이스 사이의 매핑을 제공하는 프레임워크입니다."),
                        Set.of(
                                new Option("JPA", true),
                                new Option("Spring Data JPA", true),
                                new Option("Hibernate", true),
                                new Option("JDBC", false),
                                new Option("Servlet", false)
                        )
                ),
                new Quiz(creator, new QuizContent("Spring Data JPA는 ___를 기반으로 합니다."),
                        Set.of(
                                new Option("JPA", true),
                                new Option("Hibernate", false),
                                new Option("JDBC", false),
                                new Option("MyBatis", false),
                                new Option("Spring Framework", false)
                        )
                ),
                new Quiz(creator, new QuizContent("___는 자바 애플리케이션에서 데이터베이스를 쉽게 사용할 수 있도록 도와주는 기술입니다."),
                        Set.of(
                                new Option("JPA", true),
                                new Option("Hibernate", true),
                                new Option("Spring Data JPA", true),
                                new Option("SQL", false),
                                new Option("NoSQL", false)
                        )
                ),
                new Quiz(creator, new QuizContent("다음 중 JPA의 주요 기능이 아닌 것은 무엇입니까?"),
                        Set.of(
                                new Option("객체 관계 매핑", false),
                                new Option("데이터베이스 쿼리 작성", false),
                                new Option("트랜잭션 관리", false),
                                new Option("웹 서버 기능", true),
                                new Option("데이터 접근 계층 추상화", false)
                        )
                ),
                new Quiz(creator, new QuizContent("Spring Data JPA를 사용하면 ___를 줄일 수 있습니다."),
                        Set.of(
                                new Option("보일러플레이트 코드", true),
                                new Option("개발 시간", true),
                                new Option("데이터베이스 쿼리 수", true),
                                new Option("테스트 시간", false),
                                new Option("애플리케이션 성능", false)
                        )
                ),
                new Quiz(creator, new QuizContent("Hibernate는 ___의 한 예시입니다."),
                        Set.of(
                                new Option("JPA 구현", true),
                                new Option("ORM 프레임워크", true),
                                new Option("데이터베이스 시스템", false),
                                new Option("웹 서버", false),
                                new Option("API", false)
                        )
                )
        );

        if (hopeCount > quizzes.size()) {
            return new HashSet<>(quizzes);
        }

        return new HashSet<>(quizzes.subList(0, hopeCount));
    }
}

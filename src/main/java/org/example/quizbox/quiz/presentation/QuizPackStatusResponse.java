package org.example.quizbox.quiz.presentation;

import org.example.quizbox.quiz.domain.QuizPack;
import org.example.quizbox.quiz.domain.QuizPackStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record QuizPackStatusResponse(
        long quizPackId,
        String quizPackTitle,
        long memberCount,
        long quizCount,
        Set<TagResponse> tags,
        LocalDateTime createdAt
) {

    public static QuizPackStatusResponse from(QuizPackStatus status) {
        return new QuizPackStatusResponse(
                status.quizPackId(),
                status.quizPackTitle(),
                status.quizPackMemberCount(),
                status.quizCount(),
                status.tags().getValues().stream().map(TagResponse::from).collect(Collectors.toSet()),
                status.createdAt()
        );
    }

    public static List<QuizPackStatusResponse> dummy() {
        return List.of(
                new QuizPackStatusResponse(1L, "JPA 코어 부시기", 5, 10, Set.of(new TagResponse(1L, "JPA"), new TagResponse(2L, "Hibernate")), LocalDateTime.now().minusDays(2)),
                new QuizPackStatusResponse(2L, "Spring Boot 기초", 3, 15, Set.of(new TagResponse(3L, "Spring"), new TagResponse(4L, "Boot")), LocalDateTime.now().minusDays(3)),
                new QuizPackStatusResponse(3L, "REST API 만들기", 8, 12, Set.of(new TagResponse(5L, "API"), new TagResponse(6L, "REST")), LocalDateTime.now().minusDays(1)),
                new QuizPackStatusResponse(4L, "Docker 완전 정복", 10, 20, Set.of(new TagResponse(7L, "Docker"), new TagResponse(8L, "Container")), LocalDateTime.now().minusDays(5)),
                new QuizPackStatusResponse(5L, "Kubernetes 실습", 6, 18, Set.of(new TagResponse(9L, "Kubernetes"), new TagResponse(10L, "Cluster")), LocalDateTime.now().minusDays(7)),
                new QuizPackStatusResponse(6L, "Java Stream 활용", 7, 14, Set.of(new TagResponse(11L, "Java"), new TagResponse(12L, "Stream")), LocalDateTime.now().minusDays(4)),
                new QuizPackStatusResponse(7L, "React 기본기 다지기", 4, 9, Set.of(new TagResponse(13L, "React"), new TagResponse(14L, "Frontend")), LocalDateTime.now().minusDays(6)),
                new QuizPackStatusResponse(8L, "TypeScript 심화", 2, 10, Set.of(new TagResponse(15L, "TypeScript"), new TagResponse(16L, "JavaScript")), LocalDateTime.now().minusDays(8)),
                new QuizPackStatusResponse(9L, "Spring Security 입문", 5, 11, Set.of(new TagResponse(17L, "Spring"), new TagResponse(18L, "Security")), LocalDateTime.now().minusDays(9)),
                new QuizPackStatusResponse(10L, "OAuth2 제대로 이해하기", 9, 16, Set.of(new TagResponse(19L, "OAuth2"), new TagResponse(20L, "Auth")), LocalDateTime.now().minusDays(10)),
                new QuizPackStatusResponse(11L, "PostgreSQL 활용법", 3, 8, Set.of(new TagResponse(21L, "PostgreSQL"), new TagResponse(22L, "Database")), LocalDateTime.now().minusDays(11)),
                new QuizPackStatusResponse(12L, "Redis와 캐싱", 4, 6, Set.of(new TagResponse(23L, "Redis"), new TagResponse(24L, "Cache")), LocalDateTime.now().minusDays(12)),
                new QuizPackStatusResponse(13L, "AWS 기초", 8, 20, Set.of(new TagResponse(25L, "AWS"), new TagResponse(26L, "Cloud")), LocalDateTime.now().minusDays(13)),
                new QuizPackStatusResponse(14L, "Lambda로 서버리스", 7, 14, Set.of(new TagResponse(27L, "Lambda"), new TagResponse(28L, "Serverless")), LocalDateTime.now().minusDays(14)),
                new QuizPackStatusResponse(15L, "Git 고급 사용법", 5, 12, Set.of(new TagResponse(29L, "Git"), new TagResponse(30L, "VersionControl")), LocalDateTime.now().minusDays(15)),
                new QuizPackStatusResponse(16L, "Terraform으로 인프라 관리", 6, 13, Set.of(new TagResponse(31L, "Terraform"), new TagResponse(32L, "IAC")), LocalDateTime.now().minusDays(16)),
                new QuizPackStatusResponse(17L, "Webpack 번들링", 4, 7, Set.of(new TagResponse(33L, "Webpack"), new TagResponse(34L, "Frontend")), LocalDateTime.now().minusDays(17)),
                new QuizPackStatusResponse(18L, "HTML5와 CSS3", 10, 20, Set.of(new TagResponse(35L, "HTML"), new TagResponse(36L, "CSS")), LocalDateTime.now().minusDays(18)),
                new QuizPackStatusResponse(19L, "JUnit 테스트 작성", 8, 15, Set.of(new TagResponse(37L, "JUnit"), new TagResponse(38L, "Testing")), LocalDateTime.now().minusDays(19)),
                new QuizPackStatusResponse(20L, "Python으로 데이터 분석", 12, 25, Set.of(new TagResponse(39L, "Python"), new TagResponse(40L, "DataAnalysis")), LocalDateTime.now().minusDays(20))
        );
    }
}


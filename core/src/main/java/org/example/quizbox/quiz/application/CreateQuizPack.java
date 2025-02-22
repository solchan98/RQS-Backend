package org.example.quizbox.quiz.application;

import java.util.Set;

public record CreateQuizPack(String title, Set<Long> tagIds) {

}

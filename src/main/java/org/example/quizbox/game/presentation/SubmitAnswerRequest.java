package org.example.quizbox.game.presentation;

import java.util.Set;

public record SubmitAnswerRequest(Set<Long> answerIds) {

}

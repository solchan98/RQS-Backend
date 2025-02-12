package org.example.quizbox.quiz.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.example.quizbox.common.presentation.ExternalExceptionResponse;
import org.example.quizbox.quiz.domain.AddQuizAutoCreateTaskResult;
import org.example.quizbox.quiz.domain.QuizAutoCreateTaskManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.example.quizbox.quiz.domain.AddQuizAutoCreateTaskResult.AddTaskStatus.FAILED;

@Component
public class NodeServerQuizAutoCreateTaskManager implements QuizAutoCreateTaskManager {

    private final RestClient restClient;

    public NodeServerQuizAutoCreateTaskManager(@Qualifier(value = "nodeClient") RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public AddQuizAutoCreateTaskResult addTask(long memberId, String quizPackTitle, String base64File, String fileMineType) {
        Map<String, Object> body = Map.of(
                "userId", memberId,
                "quizPackTitle", quizPackTitle,
                "base64File", base64File,
                "fileMineType", fileMineType
        );

        try {
            return restClient.post()
                    .uri(uriBuilder -> uriBuilder.path("/quiz-queue")
                            .build())
                    .body(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (req, res) -> {
                        handleError(res);
                    })
                    .toEntity(AddQuizAutoCreateTaskResult.class)
                    .getBody();
        } catch (ResourceAccessException e) {
            return new AddQuizAutoCreateTaskResult(memberId, null, FAILED, "현재 작업 등록 불가능 상태");
        } catch (AddQuizAutoCreateTaskException e) {
            return new AddQuizAutoCreateTaskResult(memberId, null, FAILED, e.getMessage());
        }
    }

    private void handleError(ClientHttpResponse res) {
        ExternalExceptionResponse externalExceptionResponse = toExternalExceptionResponse(res);
        throw new AddQuizAutoCreateTaskException(HttpStatus.valueOf(externalExceptionResponse.getStatus()),
                externalExceptionResponse.getMessage());
    }

    private ExternalExceptionResponse toExternalExceptionResponse(ClientHttpResponse res) {
        ObjectMapper objectMapper = new ObjectMapper(); // TODO
        try {
            if (res.getStatusCode().is4xxClientError()) {
                return new ExternalExceptionResponse(HttpStatus.UNAUTHORIZED.value(), "현재 작업 등록 불가능 상태(4)");
            }

            String responseBody = new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8);
            return objectMapper.readValue(responseBody, ExternalExceptionResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

@Getter
class AddQuizAutoCreateTaskException extends RuntimeException {
    private final HttpStatus status;

    public AddQuizAutoCreateTaskException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
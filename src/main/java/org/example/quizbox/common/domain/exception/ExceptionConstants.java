package org.example.quizbox.common.domain.exception;

public enum ExceptionConstants {
    // SE (Server Error)
    SE1("SE1", "서버 에러"),

    // QP (Quiz Pack)
    QP1("QP1", "존재하지 않는 퀴즈팩"),
    QP2("QP2", "퀴즈 생성에 실패"),
    QP3("QP3", "동일한 팩 내에서 퀴즈 이름 중복"),

    // QA (Quiz Answer)
    QA1("QA1", "퀴즈 생성 시, 보기는 최소 2개 이상"),
    QA2("QA2", "퀴즈 생성 시, 정답은 최소 1개 이상"),

    // QG(Quiz Game)
    QG1("QG1", "퀴즈게임 시작 시, 퀴즈팩은 필수"),
    QG2("QG2", "현재 퀴즈 답변 후 퀴즈 뽑기 가능"),
    QG3("QG3", "현재 진행중인 퀴즈가 아닌 경우 답 제출 불가"),
    QG4("QG4", "퀴즈의 보기가 아닌 값은 답으로 제출 불가"),
    QG5("QG5", "동일 퀴즈에 대하여 답 중복 제출 불가"),
    QG6("QG6", "답변은 최소 1개 이상 필수");


    private final String code;

    private final String detail;

    ExceptionConstants(String code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    public String code() {
        return code;
    }

    public String detail() {
        return detail;
    }
}

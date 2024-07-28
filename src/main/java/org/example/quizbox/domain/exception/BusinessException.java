package org.example.quizbox.domain.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String key) {
        super(key);
    }

    public BusinessException(String key, Throwable ex) {
        super(key, ex);
    }

    public static BusinessException atLeastOneQuizRequired() {
        return new BusinessException(ExceptionConstants.GM1.code());
    }

    public static BusinessException hasAlreadyBeenAnswered() {
        return new BusinessException(ExceptionConstants.GM2.code());
    }
    public static BusinessException isNotAQuizCurrentlyInProgress() {
        return new BusinessException(ExceptionConstants.GM3.code());
    }
    public static BusinessException quizIsStillInProgress() {
        return new BusinessException(ExceptionConstants.GM4.code());
    }
    public static BusinessException gameDoesNotExist() {
        return new BusinessException(ExceptionConstants.GM5.code());
    }
    public static BusinessException notAGameParticipant() {
        return new BusinessException(ExceptionConstants.GM6.code());
    }
    public static BusinessException checkPlayer() {
        return new BusinessException(ExceptionConstants.GM7.code());
    }
}

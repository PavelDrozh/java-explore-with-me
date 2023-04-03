package ru.practicum.explorewithme.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.exceptions.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse argumentValidationExceptionHandler(MethodArgumentNotValidException exception) {
        log.error("Аргумент метода не прошел валидацию", exception);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), "Некорректные данные",
                exception.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse headerExceptionHandler(MissingRequestHeaderException e) {
        log.error("Отсутствует заголовок запроса", e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getCause().toString(),
                e.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEntityNotFoundException(final EntityNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage(),
                e.getCause().toString(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final BadRequestException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), "Отсутствует необходимый параметр запроса",
                e.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCategoryNotFound(final CategoryNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), "Категория не найдена",
                e.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCompilationNotFound(final CompilationNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), "Подборка не найдена",
                e.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEventNotFound(final EventNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), "Событие не найдено",
                e.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEventRequestNotFound(final EventRequestNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), "Запрос не найден",
                e.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(final UserNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), "Пользователь не найден",
                e.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(final ConflictException e) {
        return new ErrorResponse(HttpStatus.CONFLICT.toString(), "Конфликт данных",
                e.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConstraintException(final DataIntegrityViolationException e) {
        return new ErrorResponse(HttpStatus.CONFLICT.toString(), "Конфликт данных",
                e.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingRequestParameter(final MissingServletRequestParameterException e) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), "Отсутствует необходимый параметр запроса",
                e.getMessage(), LocalDateTime.now().toString());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.info(e.getMessage(), e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Что-то пошло не так",
                                 "Непредвиденная ошибка", LocalDateTime.now().toString());
    }
}


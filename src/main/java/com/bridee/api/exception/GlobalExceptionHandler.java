package com.bridee.api.exception;

import com.bridee.api.dto.response.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CsvDownloadErro.class)
    public ResponseEntity<ErrorResponseDto> csvDownload(CsvDownloadErro e, HttpServletRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setPath(request.getContextPath());
        errorResponseDto.setTimestamp(Instant.now());

        return ResponseEntity.unprocessableEntity().body(errorResponseDto);
    }

    @ExceptionHandler(BadRequestEntityException.class)
    public ResponseEntity<ErrorResponseDto> badRequest(BadRequestEntityException e, HttpServletRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponseDto.setPath(request.getContextPath());
        errorResponseDto.setTimestamp(Instant.now());

        return ResponseEntity.ok(errorResponseDto);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ErrorResponseDto> unprocessableEntity(UnprocessableEntityException e, HttpServletRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setPath(request.getContextPath());
        errorResponseDto.setTimestamp(Instant.now());

        return ResponseEntity.unprocessableEntity().body(errorResponseDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> illegalArgumentException(IllegalArgumentException e, HttpServletRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponseDto.setPath(request.getContextPath());
        errorResponseDto.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(UsuarioExternoException.class)
    public ResponseEntity<ErrorResponseDto> usuarioExterno(ResourceNotFoundException e, HttpServletRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setStatusCode(HttpStatus.CONFLICT.value());
        errorResponseDto.setPath(request.getContextPath());
        errorResponseDto.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponseDto.setPath(request.getContextPath());
        errorResponseDto.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ErrorResponseDto> resourceAlreadyExists(ResourceAlreadyExists e, HttpServletRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.CONFLICT.value());
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setPath(request.getContextPath());
        errorResponseDto.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }

    @ExceptionHandler(ImagesNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> imagesDoesNotExists(ImagesNotFoundException e, HttpServletRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setPath(request.getContextPath());
        errorResponseDto.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(UnableToGenerateQRCode.class)
    public ResponseEntity<ErrorResponseDto> unableToGenerateQRCode(UnableToGenerateQRCode e, HttpServletRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setPath(request.getContextPath());
        errorResponseDto.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    @ExceptionHandler(SendEmailException.class)
    public ResponseEntity<ErrorResponseDto> sendEmailException(SendEmailException e, HttpServletRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.NO_CONTENT.value());
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setPath(request.getContextPath());
        errorResponseDto.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponseDto);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorResponseDto> userUnauthorized(UnauthorizedUserException e, HttpServletRequest request){

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setPath(request.getContextPath());
        errorResponseDto.setTimestamp(Instant.now());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> argumentsNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){

        ValidationError errorResponse = new ValidationError();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getContextPath());
        errorResponse.setTimestamp(Instant.now());

        for (FieldError fieldError: e.getBindingResult().getFieldErrors()){
            errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }

}

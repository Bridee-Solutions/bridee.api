package com.bridee.api.exception;

import com.bridee.api.dto.response.ErrorResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestEntityException.class)
    public ResponseEntity<ErrorResponseDto> badRequest(BadRequestEntityException e, HttpServletResponse response){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.ok(errorResponseDto);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ErrorResponseDto> unprocessableEntity(UnprocessableEntityException e, HttpServletResponse response){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorResponseDto.setMessage(e.getMessage());
        return ResponseEntity.unprocessableEntity().body(errorResponseDto);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> illegalArgumentException(IllegalArgumentException e, HttpServletResponse response){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(UsuarioExternoException.class)
    public ResponseEntity<ErrorResponseDto> usuarioExterno(ResourceNotFoundException e, HttpServletResponse response){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setStatusCode(HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> resourceNotFound(ResourceNotFoundException e, HttpServletResponse response){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<ErrorResponseDto> resourceAlreadyExists(ResourceAlreadyExists e, HttpServletResponse response){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.CONFLICT.value());
        errorResponseDto.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDto);
    }

    @ExceptionHandler(ImagesNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> imagesDoesNotExists(ImagesNotFoundException e, HttpServletResponse response){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponseDto.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(UnableToGenerateQRCode.class)
    public ResponseEntity<ErrorResponseDto> unableToGenerateQRCode(UnableToGenerateQRCode e, HttpServletResponse response){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponseDto.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    @ExceptionHandler(SendEmailException.class)
    public ResponseEntity<ErrorResponseDto> sendEmailException(SendEmailException e, HttpServletResponse response){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatusCode(HttpStatus.NO_CONTENT.value());
        errorResponseDto.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponseDto);
    }

}

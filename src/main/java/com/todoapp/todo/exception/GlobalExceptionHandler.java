package com.todoapp.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // 1. "Sen uygulama genelinde çalışan bir gözetmensin" diyoruz.
public class GlobalExceptionHandler {

    // 2. "Sadece ResourceNotFoundException tipindeki alarmları dinle" diyoruz.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {

        // 3. İstemciye göndereceğimiz standart JSON formatını hazırlıyoruz.
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage()); // Exception'dan gelen asıl mesajı alıyoruz.
        body.put("status", HttpStatus.NOT_FOUND.value()); // 404

        // 4. Hazırladığımız JSON gövdesini ve 404 durum kodunu içeren bir yanıt tepsisi oluşturup gönderiyoruz.
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Buraya başka hatalar için başka @ExceptionHandler metotları ekleyebiliriz.
    // Örneğin, genel bir hata yakalayıcı:
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Beklenmeyen bir sunucu hatası oluştu.");
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
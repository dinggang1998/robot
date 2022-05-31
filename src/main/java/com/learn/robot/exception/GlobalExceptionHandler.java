package com.learn.robot.exception;

import com.learn.robot.enums.ServiceExceptionEnum;
import com.learn.robot.model.Response;
import com.learn.robot.properities.ResourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.sql.SQLIntegrityConstraintViolationException;


/**
 * 前台项目code返回String类型，中台项目返回int类型
 * 同时前台项目业务异常码使用白名单规则
 * */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ResourceProperties resourceProperties;

    @ExceptionHandler()
    public ResponseEntity<?> handleGlobalException(Exception e) {
        log.error(e.getMessage(), e);
        if (e instanceof ServiceException) {
            if (ArrayUtils.contains(resourceProperties.getExceptionCode(), ((ServiceException) e).getCode())) {
                return ResponseEntity.status(HttpStatus.OK).body(new Response(((ServiceException) e).getCode(), e.getMessage(), null));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(String.valueOf(ServiceExceptionEnum.INTERNAL_SERVER_ERROR.getCode()), ServiceExceptionEnum.INTERNAL_SERVER_ERROR.getMessage(), null));
            }
//            return ResponseEntity.status(HttpStatus.OK).body(new Response(((ServiceException) e).getCode(), e.getMessage(), null));
        } else if (e instanceof SQLIntegrityConstraintViolationException || e instanceof DuplicateKeyException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(String.valueOf(ServiceExceptionEnum.RESOURCE_EXISTS.getCode()), ServiceExceptionEnum.RESOURCE_EXISTS.getMessage(), null));
        } else if (e instanceof MethodArgumentNotValidException) {
            return ResponseEntity.status(HttpStatus.OK).body(new Response(String.valueOf(ServiceExceptionEnum.ILLEGAL_PARAM.getCode()), ServiceExceptionEnum.ILLEGAL_PARAM.getMessage(), null));
        } else if (e instanceof AccessDeniedException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Response(String.valueOf(ServiceExceptionEnum.FORBIDDEN.getCode()), ServiceExceptionEnum.FORBIDDEN.getMessage(), null));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(String.valueOf(ServiceExceptionEnum.INTERNAL_SERVER_ERROR.getCode()), ServiceExceptionEnum.INTERNAL_SERVER_ERROR.getMessage(), null));
        }
    }

}

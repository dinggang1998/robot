package com.learn.robot.exception;


import com.learn.robot.enums.ServiceExceptionEnum;
import com.learn.robot.model.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RobotException {

    public static ServiceException serviceException(ServiceExceptionEnum exceptionEnum) {
        return new ServiceException(String.valueOf(exceptionEnum.getCode()), exceptionEnum.getMessage(),exceptionEnum.getMessageEn());
    }

    public static ServiceException serviceExceptionByResponse(Response response) {
        return new ServiceException(response.getCode(), response.getMessage(),response.getMessage());
    }

}

package org.transformer.user;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.transformer.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * 全局异常处理.
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

  private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * 数据校验异常处理.
   * @param exception 异常
   * @return 返回Map,message代表错误信息.
   * @throws Exception 异常
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public Map<String, Object> notValidErrorHandler(MethodArgumentNotValidException exception)
      throws Exception {
    logger.debug("校验错误", exception);
    //按需重新封装需要返回的错误信息  
    List<String> invalidArguments = Lists.newArrayList();
    //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息  
    for (FieldError error : exception.getBindingResult().getFieldErrors()) {
      invalidArguments.add(error.getDefaultMessage());

    }
    Map<String, Object> body = Maps.newHashMap();
    body.put("message", invalidArguments);
    return body;
  }

  /**
   * 业务异常处理.
   * @param exception 异常
   * @return 返回Map,message代表错误信息.
   * @throws Exception 异常
   */
  @ExceptionHandler(value = ServiceException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public Map<String, Object> serviceErrorHandler(ServiceException exception) throws Exception {
    Map<String, Object> body = Maps.newHashMap();
    body.put("message", exception.getMessage());
    return body;
  }
}

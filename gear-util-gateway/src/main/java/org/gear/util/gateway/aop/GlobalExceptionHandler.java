package org.gear.util.gateway.aop;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.gear.common.bean.exception.CodeException;
import org.gear.common.bean.model.Code;
import org.gear.common.bean.model.Result;
import org.gear.util.gateway.GatewayCode;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Result<Void> handler(Exception e) {
		Result<Void> result = Result.error(Code.SYS_ERR);
		if (e instanceof IllegalArgumentException) {
			log.warn("Controller 方法参数绑定失败，请注意是否使用 @RequestParam！", e);
			return result;
		} else {
			result.appendDesc(e.getMessage());
			log.warn("系统错误！", e);
			return result;
		}
	}

	@ResponseBody
	@ExceptionHandler({ CodeException.class })
	public Result<Void> bizExceptionHandler(CodeException e) {
		return Result.error(e.code());
	}

	@ResponseBody
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Result<Void> httpRequestMethodNotSupportedHandler(HttpRequestMethodNotSupportedException ex) {
		return Result.error(GatewayCode.UNSUPPORT_HTTP_METHOD);
	}

	@ResponseBody
	@ExceptionHandler({ HttpMessageNotReadableException.class, MissingServletRequestParameterException.class })
	public Result<Void> httpMessageNotReadableException(Exception ex) {
		Result<Void> response = Result.error(Code.PARAM_ERR);
		response.appendDesc(ex.toString());
		return response;
	}

	@ResponseBody
	@ExceptionHandler(BindException.class)
	public Result<Void> bindExceptionHandler(BindException ex) {
		return _validatorError(ex.getBindingResult());
	}

	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	public Result<Void> constraintViolationExceptionHandler(ConstraintViolationException ex) {
		Result<Void> response = Result.error(Code.PARAM_ERR);
		Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
		StringBuilder reason = new StringBuilder("");
		for (ConstraintViolation<?> constraintViolation : set) {
			Path path = constraintViolation.getPropertyPath();
			if (path instanceof PathImpl) 
				reason.append(((PathImpl) path).getLeafNode().getName()).append("-").append(constraintViolation.getMessage()).append(";");
		}
		reason.deleteCharAt(reason.length() - 1);
		response.appendDesc(reason.toString());
		return response;
	}

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		return _validatorError(ex.getBindingResult());
	}

	/**
	 * 上传文件超过上限
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public Result<Void> uploadSizeExceededHandler(MaxUploadSizeExceededException ex) {
		Result<Void> response = Result.error(GatewayCode.UPLOAD_SIZE_EXCEEDED);
		response.appendDesc(String.valueOf(ex.getMaxUploadSize()));
		return response;
	}

	@ResponseBody
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public Result<Void> unsupportedMediaTypeHandler(HttpMediaTypeNotSupportedException ex) {
		Result<Void> response = Result.error(GatewayCode.UNSUPPORT_CONTENT_TYPE);
		response.appendDesc(ex.getContentType().toString());
		return response;
	} 

	private Result<Void> _validatorError(BindingResult bindingResult) {
		List<FieldError> errors = bindingResult.getFieldErrors();
		StringBuilder reason = new StringBuilder();
		for (FieldError error : errors)
			reason.append(error.getField()).append("-").append(error.getDefaultMessage()).append(";");
		reason.deleteCharAt(reason.length() - 1);
		Result<Void> response = Result.error(Code.PARAM_ERR);
		response.appendDesc(reason.toString());
		return response;
	}
}

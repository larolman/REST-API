package com.valmeida.begin.api.exceptionhandler;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.valmeida.begin.core.validation.ValidacaoException;
import com.valmeida.begin.domain.exception.EntidadeEmUsoException;
import com.valmeida.begin.domain.exception.EntidadeNaoEncontradaException;
import com.valmeida.begin.domain.exception.NegocioException;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final String MSG_ERRO_USUARIO_FINAL = "Ocorreu um erro inesperado no sistema. Tente novamente e se o "
														+ "problema persistir, entre em contato com o administrador do sistema.";
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, ex.getMessage()).build();
				
		return handleExceptionInternal(ex, problem, new HttpHeaders()
				, status, request);
	}
	
	
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problem problem = createProblemBuilder(status, ProblemType.NEGOCIO, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders()
				, status, request);

	}
	
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.CONFLICT;
		
		Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders()
				, status, request);

	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		String detail = String.format("O parâmetro de URL %s recebeu um valor %s, que é um tipo inválido."
				+ " Corrija e informe um valor compatível com o tipo %s.", ex.getName(), 
				ex.getValue(), ex.getRequiredType());
		
		Problem problem = createProblemBuilder(status, ProblemType.PARAMETRO_INVALIDO, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
	}
	
	@ExceptionHandler(Exception.class)
	private ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		String detail = MSG_ERRO_USUARIO_FINAL;
		
		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, ProblemType.ERRO_DE_SISTEMA, detail).build();
		
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ValidacaoException.class)
	private ResponseEntity<Object> handleValidacaoException(ValidacaoException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		List<Problem.Field> problemFields = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldErros -> {
					String message = messageSource.getMessage(fieldErros, LocaleContextHolder.getLocale());
					
					return Problem.Field.builder()												
						.name(fieldErros.getField())
						.userMessage(message)
						.build();
						})
				.collect(Collectors.toList());

		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

		Problem problem = createProblemBuilder(status, ProblemType.DADOS_INVALIDOS, detail)
			.fields(problemFields)	
			.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
			
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException)rootCause, headers, status, request);
			
		} else if (rootCause instanceof IgnoredPropertyException) {
			return handleIgnoredPropertyException((IgnoredPropertyException)rootCause, headers, status, request);
		}
		
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe";
		
		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail ).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders()
				, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", ex.getRequestURL());
		
		Problem problem = createProblemBuilder(status, ProblemType.RECURSO_NAO_ENCONTRADO, detail).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
		
	private ResponseEntity<Object> handleIgnoredPropertyException(IgnoredPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		
		String detail = String.format("A propriedade %s não pode ser alterada devido a regra de negócio."
				+ " Por favor remover esta propriedade e executar a requisição novamente", ex.getPropertyName());
		
		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}



	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = joiningPath(ex.getPath());
		
		String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido."
				+ " Corrija e informe um valor com o tipo %s.", path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail ).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		
		List<Problem.Field> problemFields = ex.getBindingResult().getFieldErrors().stream()
												.map(fieldErros -> {
													String message = messageSource.getMessage(fieldErros, LocaleContextHolder.getLocale());
													
													return Problem.Field.builder()												
														.name(fieldErros.getField())
														.userMessage(message)
														.build();
														})
												.collect(Collectors.toList());
		
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		
		Problem problem = createProblemBuilder(status, ProblemType.DADOS_INVALIDOS, detail)
											.fields(problemFields)	
											.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.title((String) body)
					.status(status.value())
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status,
			ProblemType problemType, String detail) {
		
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail)
				.timestamp(LocalDateTime.now())
				.userMessage(MSG_ERRO_USUARIO_FINAL);
	}
	
	private String joiningPath(List<Reference> references) {
		
		return references.stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
	}
}

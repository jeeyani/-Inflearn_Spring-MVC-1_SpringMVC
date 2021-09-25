package hello.springmvc.basic.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestBodyStringController {

	@PostMapping("/request-body-string-v1")
	public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); //UTF_8로 지정
		
		log.info("messageBody={}", messageBody);
		response.getWriter().write("ok");
		
	}
	/*
	 * InputStream , Writer 으로 직접 선언
	 */
	@PostMapping("/request-body-string-v2")
	public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
		
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); //UTF_8로 지정
		
		log.info("messageBody={}", messageBody);
		responseWriter.write("ok");
		
	}
	
	/*
	 * HttpEntity
	 */
	@PostMapping("/request-body-string-v3")
	public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
		
		String messageBody = httpEntity.getBody();
		
		log.info("messageBody={}", messageBody);
		
		return new HttpEntity<>("ok") ;

	}
	
	/*
	 * HttpEntity >> RequestEntity, ResponseEntity
	 */
	/*@PostMapping("/request-body-string-v3")
	public HttpEntity<String> requestBodyStringV3(RequestEntity<String> httpEntity) throws IOException {
		
		String messageBody = httpEntity.getBody();
		
		log.info("messageBody={}", messageBody);
		
		return new ResponseEntity<String>("ok", HttpStatus.CREATED) ;
	
	}*/
	
	/**
	* @RequestBody
	* - 메시지 바디 정보를 직접 조회(@RequestParam X, @ModelAttribute X)
	* - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
	*
	* @ResponseBody
	* - 메시지 바디 정보 직접 반환(view 조회X)
	* - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
	*/
	@ResponseBody
	@PostMapping("/request-body-string-v4")
	public String requestBodyStringV4(@RequestBody String messageBody) {
		log.info("messageBody={}", messageBody);
		return "ok";
	}
	
	
	
}

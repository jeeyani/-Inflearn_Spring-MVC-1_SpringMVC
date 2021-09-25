package hello.springmvc.basic.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import hello.springmvc.HelloData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RequestBodyJsonController {

	private ObjectMapper objectMapper = new ObjectMapper();

	@PostMapping("/request-body-json-v1")
	public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ServletInputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

		log.info("messageBody={}", messageBody);

		HelloData data = objectMapper.readValue(messageBody, HelloData.class);

		log.info("username={}, age={}", data.getUsername(), data.getAge());

		response.getWriter().write("ok");
	}

	/*
	 * @RequestBody , @ResponseBody 사용하기
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v2")
	public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

		log.info("messageBody={}", messageBody);

		HelloData data = objectMapper.readValue(messageBody, HelloData.class);

		log.info("username={}, age={}", data.getUsername(), data.getAge());

		return "ok";
	}

	/*
	 * HelloData 객체 사용하기
	 * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림)
	 * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (contenttype:application/json)
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v3")
	public String requestBodyJsonV3(@RequestBody HelloData data) {
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		return "ok";
	}

	@ResponseBody
	@PostMapping("/request-body-json-v4")
	public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {

		HelloData data = httpEntity.getBody(); //httpEntity에서 바디를 꺼내주는 작업 필수!

		log.info("username={}, age={}", data.getUsername(), data.getAge());
		return "ok";
	}

	
	/**
	 * @RequestBody 생략 불가능(@ModelAttribute 가 적용되어 버림) HttpMessageConverter 사용 ->
	 *              MappingJackson2HttpMessageConverter (contenttype:application/json)
	 *              
	 *
	 * @ResponseBody 적용 - 메시지 바디 정보 직접 반환(view 조회X) - HttpMessageConverter 사용 ->
	 *               MappingJackson2HttpMessageConverter 적용 (Accept:application/json)
	 *               
	 */
	@ResponseBody
	@PostMapping("/request-body-json-v5")
	public HelloData requestBodyJsonV5(@RequestBody HelloData data) { //data 객체 그대로 반환해보기
		log.info("username={}, age={}", data.getUsername(), data.getAge());
		return data;
	}

}

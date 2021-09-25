package hello.springmvc.basic.request;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RequestHeaderController {

	@RequestMapping("/headers")
	public String headers(HttpServletRequest request,
						 	HttpServletResponse response, 
							HttpMethod httpMethod, //HTTP메서드 조회
							Locale locale, 
							@RequestHeader MultiValueMap<String, String> headerMap, //하나의 키에 여러값을 받을 때 사용
							@RequestHeader("host") String host,
							@CookieValue(value = "myCookie", required = false) String cookie) 
	{

		
		log.info("request={}", request);
		log.info("response={}", response);
		log.info("httpMethod={}", httpMethod);
		log.info("locale={}", locale);
		log.info("headerMap={}", headerMap);
		log.info("header host={}", host);
		log.info("myCookie={}", cookie);
		
		return "ok";
	}

}

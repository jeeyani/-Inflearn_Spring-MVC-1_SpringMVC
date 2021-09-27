package hello.springmvc.basic.response;

import hello.springmvc.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ResponseBodyController {

	@GetMapping("/response-body-string-v1")
	public void responseBodyV1(HttpServletResponse response) throws IOException {
		response.getWriter().write("ok");
	}

	/**응답 단순하게 변경
	 * HttpEntity, ResponseEntity(Http Status 추가)
	 * 
	 * @return
	 */
	@GetMapping("/response-body-string-v2")
	public ResponseEntity<String> responseBodyV2() {
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}

	/*
	 * String으로 반환하기(문자처리)
	 */
	@ResponseBody //view 랜더링 안함
	@GetMapping("/response-body-string-v3")
	public String responseBodyV3() {
		return "ok";
	}

	/*
	 * JSON데이터 처리하기
	 */
	@GetMapping("/response-body-json-v1")
	public ResponseEntity<HelloData> responseBodyJsonV1() {
		HelloData helloData = new HelloData();
		helloData.setUsername("userA");
		helloData.setAge(20);
		
		return new ResponseEntity<>(helloData, HttpStatus.OK);
	}
	

	/*
	 * JSON데이터 처리하기 2
	 */
	@ResponseStatus(HttpStatus.OK) //상태코드 변경하기 위한 어노테이션
	@ResponseBody
	@GetMapping("/response-body-json-v2")
	public HelloData responseBodyJsonV2() {
		HelloData helloData = new HelloData();
		helloData.setUsername("userA");
		helloData.setAge(20);
		
		return helloData;
	}

}

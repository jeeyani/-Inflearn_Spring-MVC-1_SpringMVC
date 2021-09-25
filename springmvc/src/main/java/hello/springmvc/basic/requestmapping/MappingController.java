package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MappingController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping({ "/hello-basic", "/hello-go" }) //배열 형식으로 이중 URL 작성도 가능
	//@RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET) //GET방식 지정 가능
	//@GetMapping(value = "/mapping-get-v1")
	public String helloBasic() {
		log.info("helloBasic");

		return "ok";
	}

	/*
	* PathVariable 사용
	* 변수명이 같으면 생략 가능
	* @PathVariable("userId") String userId -> @PathVariable userId
	*/
	@GetMapping("/mapping/{userId}")
	public String mappingPath(@PathVariable("userId") String data) {
		log.info("mappingPath userId={}", data);
		return "ok";
	}

	/**
	 * PathVariable 사용 다중
	 */
	@GetMapping("/mapping/users/{userId}/orders/{orderId}")
	public String mappingPath(@PathVariable String userId, @PathVariable Long orderId) {

		log.info("mappingPath userId={}, orderId={}", userId, orderId);

		return "ok";
	}

	/**
	 * 파라미터로 추가 매핑 params="mode", params="!mode" params="mode=debug"
	 * params="mode!=debug" (! = ) params = {"mode=debug","data=good"}
	 */
	@GetMapping(value = "/mapping-param", params = "mode=debug") //'mode=debug'가 있어야 호출 즉, 파라미터 mode에 대한 값에 대한 정의가 필수
	public String mappingParam() {
		log.info("mappingParam");
		return "ok";
	}

	/**
	 * 특정 헤더로 추가 매핑 headers="mode", headers="!mode" headers="mode=debug"
	 * headers="mode!=debug" (! = )
	 */
	@GetMapping(value = "/mapping-header", headers = "mode=debug") //헤더 안에 값을 넣어주기
	public String mappingHeader() {
		log.info("mappingHeader");
		return "ok";
	}

	/**
	 * Content-Type 헤더 기반 추가 매핑 Media Type consumes="application/json"
	 * consumes="!application/json" consumes="application/*" consumes="*\/*"
	 * MediaType.APPLICATION_JSON_VALUE
	 */
	@PostMapping(value = "/mapping-consume", consumes = "application/json") //헤더의 content-type이 application/json인 경우에 호출
	public String mappingConsumes() {
		log.info("mappingConsumes");
		return "ok";
	}

	/*
	* * Accept 헤더 기반 Media Type
	* produces = "text/html"
	* produces = "!text/html"
	* produces = "text/*"
	* produces = "*\/*"
	*/
	@PostMapping(value = "/mapping-produce", produces = "text/html") //Accept 헤더에  Media Type이 text/html인 경우
	public String mappingProduces() {
		log.info("mappingProduces");
		return "ok";
	}

}

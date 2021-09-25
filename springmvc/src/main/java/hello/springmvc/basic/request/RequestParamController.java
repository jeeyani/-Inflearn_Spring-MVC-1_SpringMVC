package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import hello.springmvc.HelloData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
//@RestController
@Controller
public class RequestParamController {

	/**
	 * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
	 */
	@RequestMapping("/request-param-v1")
	public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		int age = Integer.parseInt(request.getParameter("age"));

		log.info("username={}, age={}", username, age);

		response.getWriter().write("ok");
	}

	@ResponseBody //응답메시지에 바로 넣어버림 >> view 랜더링 진행 하지 않음
	@RequestMapping("/request-param-v2")
	public String requestParamV2(@RequestParam("username") String memberName, @RequestParam("age") int memberAge) {
		log.info("username={}, age={}", memberName, memberAge);
		return "ok";

	}

	@ResponseBody
	@RequestMapping("/request-param-v3")
	public String requestParamV3(@RequestParam String username, @RequestParam int age) {
		log.info("username={}, age={}", username, age);
		return "ok";

	}

	@ResponseBody
	@RequestMapping("/request-param-v4")
	public String requestParamV4(String username, int age) //요청파라미터와 이름이 동일하면 어노테이션도 삭제 가능~!
	{
		log.info("username={}, age={}", username, age);
		return "ok";

	}

	/**
	 * @RequestParam.required /request-param -> username이 없으므로 예외
	 *
	 *                        주의! /request-param?username= -> 빈문자로 통과
	 *
	 *                        주의! /request-param int age -> null을 int에 입력하는 것은 불가능,
	 *                        따라서 Integer 변경해야 함(또는 다음에 나오는 defaultValue 사용)
	 */
	@ResponseBody
	@RequestMapping("/request-param-required")
	public String requestParamRequired(@RequestParam(required = true) String username, //required = true 무조건 들어와야하는 값
			@RequestParam(required = false) Integer age) {
		log.info("username={}, age={}", username, age);
		return "ok";
	}

	/**
	 * @RequestParam - defaultValue 사용
	 *
	 *               참고: defaultValue는 빈 문자의 경우에도 적용 /request-param?username=
	 */
	@ResponseBody
	@RequestMapping("/request-param-default")
	public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
			@RequestParam(required = false, defaultValue = "-1") int age) {
		log.info("username={}, age={}", username, age);
		return "ok";
	}

	/**
	 * @RequestParam Map, MultiValueMap Map(key=value) MultiValueMap(key=[value1,
	 *               value2, ...] ex) (key=userIds, value=[id1, id2])
	 */
	@ResponseBody
	@RequestMapping("/request-param-map")
	public String requestParamMap(@RequestParam Map<String, Object> paramMap) {

		log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
		return "ok";

	}

	//@ModelAttribute 미 사용
	/*@ResponseBody
	@RequestMapping("/model-attribute-v1")
	public String modelAttributeV1_ex(@RequestParam String username, @RequestParam int age) {
		HelloData hd = new HelloData();
		hd.setUsername(username);
		hd.setAge(age);
		
		log.info("username={}, age={}", hd.getUsername(), hd.getAge());
		return "ok";
	}*/
	
	/*
	* @ModelAttribute 사용
	* 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨, 뒤에 model을 설명할 때
	자세히 설명
	*/
	@ResponseBody
	@RequestMapping("/model-attribute-v1")
	public String modelAttributeV1(@ModelAttribute HelloData helloData) {
		
		log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
		return "ok";
	}
	
	/**
	* @ModelAttribute 생략 가능
	* String, int 같은 단순 타입 = @RequestParam
	* argument resolver 로 지정해둔 타입 외 = @ModelAttribute
	*/
	@ResponseBody
	@RequestMapping("/model-attribute-v2")
	public String modelAttributeV2(HelloData helloData) {
		
		log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
		return "ok";
		
	}

}

package chatbot.api.common.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import chatbot.api.common.domain.OrderDto;

@Service
public class RequestToModuleServiceImpl {

	@Autowired
	private RestTemplate restTemplate;
	
	public OrderDto request(String orderUrl, MultiValueMap<String, Object> map) {
		UriComponentsBuilder builder =UriComponentsBuilder.fromHttpUrl(orderUrl);
		
		/*HttpHeaders httpHeaders=new HttpHeaders();
		httpHeaders.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);*/
		return restTemplate.postForObject(builder.toUriString(), map, OrderDto.class);
	}
}

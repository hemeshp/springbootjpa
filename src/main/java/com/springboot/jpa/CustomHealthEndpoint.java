package com.springboot.jpa;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "custom-health")
public class CustomHealthEndpoint {

	@ReadOperation
	public Map<String, Object> health() {
		Map<String, Object> details = new LinkedHashMap<String, Object>();
		details.put("CustomHealthStatus", "Everything looks good !!");
		return details;
	}

}

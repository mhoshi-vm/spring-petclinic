package org.springframework.samples.petclinic.system;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

//https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache-annotations-cacheable-default-key
@Component("CustomKeyGenerator")
public class CustomKeyGenerator implements KeyGenerator {

	@Override
	public Object generate(Object o, Method method, Object... params) {
		return String.format("%s_%s_%s", o.getClass().getSimpleName(), method.getName(),
				StringUtils.arrayToDelimitedString(params, "_"));
	}

}

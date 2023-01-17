package in.nareshit.raghu.config;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/*@Bean
	public Docket createDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("in.nareshit.raghu.rest"))
				.paths(PathSelectors.regex("/rest.*"))
				.build(); 
	}   */
	
	@Bean
	public Docket createDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(basePackage("in.nareshit.raghu.rest"))
				.paths(regex("/rest.*"))
				.build()
				.apiInfo(apiInfo()); 
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo("MY PRODUCT APP",
				"SAMPLE",
				"4.3G",
				"https://nareshit.in/",
				new Contact("SANU","https://nareshit.in/","Sanu@gmail.com"),
				"NIT Ltd",
				"https://nareshit.in/",
				Collections.emptyList());
	}
}

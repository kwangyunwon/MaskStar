package com.mask8.backend;
import static com.google.common.base.Predicates.or;
	import static springfox.documentation.builders.PathSelectors.regex;

	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;

	import com.google.common.base.Predicate;

	import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
	import springfox.documentation.service.Contact;
	import springfox.documentation.spi.DocumentationType;
	import springfox.documentation.spring.web.plugins.Docket;
	import springfox.documentation.swagger2.annotations.EnableSwagger2;

	@Configuration
	@EnableSwagger2
	public class SwaggerConfig {
		@Bean
		public Docket postsApi() {
			return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
					.apiInfo(apiInfo()).select().paths(PathSelectors.any()).build();
		}

		private Predicate<String> postPaths() {
			return or(regex("/board/posts.*"), regex("/board.*"));
		}

		private ApiInfo apiInfo() {
			return new ApiInfoBuilder().title("maskstar API")
				.description("maskstar API Reference for Data Scientists Developers")
				.termsOfServiceUrl("https://github.com/EncoreFinal2Team/backend")
				.contact(
					new Contact("mask7", "https://github.com/EncoreFinal2Team/backend",
					"onlinemaskinfra@gmail.com"))
				.version("1.0")
				.build();
		}
	}
package br.com.zitrus.desafio.config;

import java.util.Date;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .pathMapping("/")
        .forCodeGeneration(true)
        .genericModelSubstitutes(ResponseEntity.class)
        .ignoredParameterTypes(Pageable.class)
        .ignoredParameterTypes(java.sql.Date.class)
        .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
        .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
        .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
        .useDefaultResponseMessages(false);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Estoque Zitrus")
        .description("API para consultar o Estoque do desafio da Zitrus")
        .version("1.0.0")
        .build();
  }
}

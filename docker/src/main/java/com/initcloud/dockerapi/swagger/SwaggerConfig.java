//package com.initcloud.dockerapi.swagger;
//
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
////@Configuration
////@EnableSwagger2
////@EnableAsync
////@EnableWebMvc
//public class SwaggerConfig {// extends WebMvcConfigurationSupport {
//
//    private static final String API_NAME = "Init Cloud Rocket";
//    private static final String API_VERSION = "0.1";
//    private static final String API_DESCRIPTION = "Rocket Scan API Description";
//
//    //@Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.ant("/**"))
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title(API_NAME)
//                .version(API_VERSION)
//                .description(API_DESCRIPTION)
//                .build();
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//}
//package com.moye.config;
//
//import com.moye.interceptor.JwtTokenAdminInterceptor;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
////import springfox.documentation.builders.ApiInfoBuilder;
////import springfox.documentation.builders.PathSelectors;
////import springfox.documentation.builders.RequestHandlerSelectors;
////import springfox.documentation.service.ApiInfo;
////import springfox.documentation.spi.DocumentationType;
////import springfox.documentation.spring.web.plugins.Docket;
//
///**
// * 配置类，注册web层相关组件
// */
//@Configuration
//@Slf4j
//public class WebMvcConfigurationCopy extends WebMvcConfigurationSupport {
//
//    @Autowired
//    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
//
//    /**
//     * 注册自定义拦截器
//     *
//     * @param registry
//     */
//    protected void addInterceptors(InterceptorRegistry registry) {
//        log.info("开始注册自定义拦截器...");
//        registry.addInterceptor(jwtTokenAdminInterceptor)
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/admin/employee/login");
//    }
//
//    /**
//     * 通过knife4j生成接口文档
//     * @return
//     */
////    @Bean
////    public Docket docket() {
////        log.info("开始生成接口文档...");
////        ApiInfo apiInfo = new ApiInfoBuilder()
////                .title("外卖项目接口文档")
////                .version("2.0")
////                .description("外卖项目接口文档")
////                .build();
////        Docket docket = new Docket(DocumentationType.SWAGGER_2)
////                .apiInfo(apiInfo)
////                .select()
////                .apis(RequestHandlerSelectors.basePackage("com.moye.controller"))
////                .paths(PathSelectors.any())
////                .build();
////        return docket;
////    }
//
//    /**
//     * 通过knife4j生成接口文档
//     * @return
//     */
//    @Bean
//    public OpenAPI publicApi(Environment environment) {
//        return new OpenAPI()
//                //.servers(serverList())
//                .info(new Info()
//                        .title("外卖项目接口文档")
//                        //.extensions(Map.of("x-audience", "external-partner", "x-application-id", "APP-12345"))
//                        .description("外卖项目接口文档")
//                        .version("2.0")
//                );
//        //.addSecurityItem(new SecurityRequirement().addList("bearer-jwt", Arrays.asList("read", "write"))).security(securityList());
//    }
//
//    /**
//     * 设置静态资源映射
//     * @param registry
//     */
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        log.info("设置静态资源映射...");
//        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//}

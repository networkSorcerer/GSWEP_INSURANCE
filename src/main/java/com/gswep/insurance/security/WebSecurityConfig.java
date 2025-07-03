//package com.gswep.insurance.security;
//import com.gswep.insurance.jwt.TokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig implements WebMvcConfigurer {
//    private final TokenProvider tokenProvider;
//    private final JwtAuthenticationEntityPoint jwtAuthenticationEntryPoint;
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    @Configuration
//    public class WebConfig implements WebMvcConfigurer {
//        @Override
//        public void addCorsMappings(CorsRegistry registry){
//            registry.addMapping("/**")
//                    .allowedOrigins("http://localhost:3000")
//                    .allowedMethods("GET","POST","PUT","DELETE")
//                    .allowedHeaders("*")
//                    .allowCredentials(true);
//        }
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        http
//                .httpBasic()
//                .and()
//                .cors()
//                .and()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception").permitAll()
//                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                .antMatchers("/favicon.ico", "/manifest.json").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .apply(new JwtSecurityConfig(tokenProvider));
//
//        return http.build();
//    }
//}

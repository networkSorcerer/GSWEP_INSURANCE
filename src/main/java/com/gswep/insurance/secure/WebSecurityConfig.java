package com.gswep.insurance.secure;



import com.gswep.insurance.user.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity // Spring Security 지원을 가능하게 함
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;  //사용자 정보를 로드하는 서비스
    private final AuthenticationConfiguration authenticationConfiguration;  //인증 구성을 위한 클래스.

    @Bean
    public PasswordEncoder passwordEncoder() {  //비밀번호를 암호화하기 위한 PasswordEncoder를 빈으로 정의
        return new BCryptPasswordEncoder();  //여기서는 BCryptPasswordEncoder를 사용
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true); // 로그인 요청 시 세션/쿠키 사용 시 필요

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5173")  // 허용할 Origin
                    .allowedMethods("GET", "POST", "PUT", "DELETE")  // 허용할 HTTP 메서드
                    .allowedHeaders("*")
                    .allowCredentials(true);  // 쿠키를 허용할 경우 true 설정
        }
    }

    @Bean
    // 인증 관리자를 빈으로 정의
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();  //authenticationConfiguration을 이용하여 AuthenticationManager를 반환
    }

    @Bean
    // JWT 인증 필터를 빈으로 정의
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        // 이 필터는 JWT를 사용하여 인증을 처리하며, 인증 관리자를 설정
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    // JWT 인가(권한 부여) 필터를 빈으로 정의
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        // 이 필터는 JWT를 사용하여 권한 부여를 처리
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable())
                .cors(Customizer.withDefaults());
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 요청 권한 설정: 특정 URL 패턴에 대한 접근 권한을 설정
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // resources 접근 허용 설정
                        .requestMatchers("/", "/index.html", "/static/**", "/css/**", "/js/**", "/images/**", "/assets/**", "/*.ico", "/*.json", "/*.svg").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/contract/**").authenticated()
                        .requestMatchers("/form/**").authenticated() //form_fields
                        .requestMatchers("/answers/**").authenticated()
                        .requestMatchers("/form_fields/**").authenticated()
                        .requestMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated() // 그 외 모든 요청 인증처리
        );



        // 필터 관리: JWT 인증 필터와 권한 부여 필터를 필터 체인에 추가
        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // SecurityFilterChain 객체를 생성하고 반환
        return http.build();
    }
}

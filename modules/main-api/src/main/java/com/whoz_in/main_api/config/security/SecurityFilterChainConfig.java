package com.whoz_in.main_api.config.security;

import com.whoz_in.main_api.config.security.oauth2.CustomOAuth2UserService;
import com.whoz_in.main_api.config.security.oauth2.LoginFailureHandler;
import com.whoz_in.main_api.config.security.oauth2.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityFilterChainConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final ServerAuthenticationFilter serverAuthenticationFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UnknownEndpointFilter unknownEndpointFilter;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    @Order(0)
    public SecurityFilterChain serverToServerFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher(
                "/internal/**"
        );

        commonConfigurations(httpSecurity);
        httpSecurity.logout(AbstractHttpConfigurer::disable);
        //TODO: ip 화이트 리스트
        httpSecurity.addFilterAt(serverAuthenticationFilter, LogoutFilter.class);

        return httpSecurity.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain oauth2FilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher(
                "/login", //시큐리티 기본 로그인 페이지
                "/oauth2/authorization/*", //소셜 로그인 페이지 (OAuth2LoginConfigurer에서 자동 생성)
                "/login/oauth2/code/*"  //redirect uri
        );

        commonConfigurations(httpSecurity);

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(AbstractHttpConfigurer::disable);
        httpSecurity.oauth2Login(oauth2->
                oauth2
                        .clientRegistrationRepository(clientRegistrationRepository)
                        .userInfoEndpoint(config -> config.userService(customOAuth2UserService))
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
        );

        return httpSecurity.build();
    }

    //인증인가 필요 없는 엔드포인트
    @Bean
    @Order(2)
    public SecurityFilterChain noAuthenticationFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher(
                "/api/v1/signup/oauth",
                "/api/v1/signup",
                "/api/v1/badges",
                "/api/v1/badges/whozin",
                "/api/v1/badges/members",
                "/api/v1/badges/members",
                "/api/v1/devices/active",
                "/api/v1/signup/oauth"
        );
        httpSecurity.authorizeHttpRequests(auth-> auth.anyRequest().permitAll());

        commonConfigurations(httpSecurity);

        httpSecurity.logout(AbstractHttpConfigurer::disable);
        httpSecurity.securityContext(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    //인증이 필요한 엔드포인트나 인증 여부에 따라 다른 동작을 하는 엔드포인트
    //로그아웃, 게시글 작성 등
    @Bean
    @Order(3)
    public SecurityFilterChain authenticationFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher(
                "/**"
        );
        httpSecurity.authorizeHttpRequests(auth-> {
            //인증 필요
            auth.requestMatchers(HttpMethod.GET,
                    "/api/v1/device/info-status",
                    "/api/v1/devices",
                    "/api/v1/private-ips",
                    "/api/v1/ssid",
                    "/api/v1/members",
                    "/api/v1/member"
                    //network-api TODO: main-api에서 분리
                    ,"/api/v1/ip"
            ).authenticated();
            auth.requestMatchers(HttpMethod.POST,
                    "/api/v1/device",
                    "/api/v1/device/info"
            ).authenticated();
            auth.requestMatchers(HttpMethod.PATCH,
                    "/api/v1/device/info"
            ).authenticated();
            auth.requestMatchers(HttpMethod.DELETE,
                    "/api/v1/device"
            ).authenticated();
            //인증 여부에 따라 다른 동작하는 api
//            auth.requestMatchers(
//            ).permitAll();
        });

        commonConfigurations(httpSecurity);
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //쿠키를 받기 위한 설정
        httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource));
        //서버가 처리할 수 있는 엔드포인트인지 확인하는 필터
        httpSecurity.addFilterBefore(unknownEndpointFilter, DisableEncodeUrlFilter.class);
        //jwt(access token)을 Authentication으로 만들어 등록하는 필터
        httpSecurity.addFilterAt(jwtAuthenticationFilter, LogoutFilter.class);
        //인증 실패 핸들러
        httpSecurity.exceptionHandling(ex-> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        //TODO: 로그아웃 추가

        return httpSecurity.build();
    }

    private void commonConfigurations(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.httpBasic(AbstractHttpConfigurer::disable);
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(config-> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //세션을 생성 및 사용하지 않는다.
        httpSecurity.requestCache(AbstractHttpConfigurer::disable);
    }
}


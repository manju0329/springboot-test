package com.test.spring.config.auth;

import com.test.spring.config.auth.dto.OAuthAttributes;
import com.test.spring.config.auth.dto.SessionUser;
import com.test.spring.domain.user.User;
import com.test.spring.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate =
                new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        //resgistrationId = 현재 로그인 서비스 구분(구글, 네이버..), userNameAttributeName = 키가 되는 필드값(구글 = sub)
        //OAuthAttributes - OAuth2User의 attribute 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        //SessionUser - 세션에 사용자 정보 저장하는 Dto 클래스
        //SessionUser - 세션에 사용자 정보 저장하는 Dto 클래스
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
        attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}

package chatbot.api.common.security.oauth2;

import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.ClientSecretPost;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.id.ClientID;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.OAuth2Error;

import java.io.IOException;
import java.net.URI;

public class SpringWebClientAuthorizationCodeTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {
    private static final String INVALID_TOKEN_RESPONSE_ERROR_CODE = "invalid_token_response";

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        //client registration정보 추출
        ClientRegistration clientRegistration = authorizationGrantRequest.getClientRegistration();

        //provider의 응답으로 부터 authorization code 추출(쿼리 스트링의 code값)
        AuthorizationCode authorizationCode = new AuthorizationCode(
                authorizationGrantRequest
                        .getAuthorizationExchange()
                        .getAuthorizationResponse()
                        .getCode());

        //redirect-uri, token-uri, AuthorizationCodeGrant 생성
        URI redirectUri = toURI(authorizationGrantRequest.getAuthorizationExchange().getAuthorizationRequest().getRedirectUri());
        AuthorizationGrant authorizationCodeGrant = new AuthorizationCodeGrant(authorizationCode, redirectUri);
        URI tokenUri = toURI(clientRegistration.getProviderDetails().getTokenUri());

        //client-id와 client-secret 추출
        ClientID clientId = new ClientID(clientRegistration.getClientId());
        Secret clientSecret = new Secret(clientRegistration.getClientSecret());


        // ClientAuthenticationMethod가 post/basic 인지
        ClientAuthentication clientAuthentication = null;
        if (ClientAuthenticationMethod.POST.equals(clientRegistration.getClientAuthenticationMethod())) {
            clientAuthentication = new ClientSecretPost(clientId, clientSecret);
        } else {
            clientAuthentication = new ClientSecretBasic(clientId, clientSecret);
        }

        //authorization_code를 access_token으로 교환
        TokenResponse tokenResponse = null;
        try {
            TokenRequest tokenRequest = new TokenRequest(tokenUri, clientAuthentication, authorizationCodeGrant);
            HTTPRequest httpRequest = tokenRequest.toHTTPRequest();
            httpRequest.setAccept(MediaType.APPLICATION_JSON_VALUE);
            httpRequest.setConnectTimeout(30000);
            httpRequest.setReadTimeout(30000);
            tokenResponse = TokenResponse.parse(httpRequest.send());

        } catch (ParseException | IOException ex) {
            OAuth2Error oauth2Error = new OAuth2Error(INVALID_TOKEN_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: " + ex.getMessage(), null);
            throw new OAuth2AuthorizationException(oauth2Error, ex);
        }

        //tokenResponse를 accessTokenResponse로 변환
        AccessTokenResponse accessTokenResponse = (AccessTokenResponse) tokenResponse;

        //accessToken의 Type은 bearer인지
        String accessToken = accessTokenResponse.getTokens().getAccessToken().getValue();
        System.out.println(accessToken);
        OAuth2AccessToken.TokenType accessTokenType = null;
        if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(accessTokenResponse.getTokens().getAccessToken().getType().getValue())) {
            accessTokenType = OAuth2AccessToken.TokenType.BEARER;
        }

        //access_token의 유효시간 추출
        long expiresIn = accessTokenResponse.getTokens().getAccessToken().getLifetime();

        // As per spec, in section 5.1 Successful Access Token Response
        // https://tools.ietf.org/html/rfc6749#section-5.1
        // If AccessTokenResponse.scope is empty, then default to the scope
        // originally requested by the client in the Authorization Request
        /*Set<String> scopes;
        if (CollectionUtils.isEmpty(accessTokenResponse.getTokens().getAccessToken().getScope())) {
            scopes = new LinkedHashSet<>(
                    authorizationGrantRequest.getAuthorizationExchange().getAuthorizationRequest().getScopes());
        } else {
            scopes = new LinkedHashSet<>(
                    accessTokenResponse.getTokens().getAccessToken().getScope().toStringList());
        }*/

        String refreshToken = null;
        if (accessTokenResponse.getTokens().getRefreshToken() != null) {
            refreshToken = accessTokenResponse.getTokens().getRefreshToken().getValue();
        }

        //Map<String, Object> additionalParameters = new LinkedHashMap<>(accessTokenResponse.getCustomParameters());

        return OAuth2AccessTokenResponse.withToken(accessToken)
                .tokenType(accessTokenType)
                .expiresIn(expiresIn)
                //.scopes(scopes)
                .refreshToken(refreshToken)
                //.additionalParameters(additionalParameters)
                .build();
    }

    private static URI toURI(String uriStr) {
        try {
            return new URI(uriStr);
        } catch (Exception ex) {
            throw new IllegalArgumentException("An error occurred parsing URI: " + uriStr, ex);
        }
    }
}

package pl.org.opi.pbn.micro.api.demo.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class TokenService {
    private final String baseAuthUri;
    private final String applicationId;
    private final String applicationToken;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private String userToken;

    public TokenService(@Value("${base.auth.uri}") String baseAuthUri, @Value("${application.id}") String applicationId,
                        @Value("${application.token}") String applicationToken, ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.baseAuthUri = baseAuthUri;
        this.applicationId = applicationId;
        this.applicationToken = applicationToken;
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

//    Metoda służy do uzyskania tokena użytkownika za pomocą uzyskanego wcześniej tokena jednorazowego. W tym celu wysyłane jest żądanie POST z nagłówkami:
//    (1) o nazwie „X-App-Id” i wartości zmiennej applicationId
//    (2) o nazwie „X-App-Token” i wartości zmiennej applicationToken,
//    a także ciałem typu json o wartości: {"oneTimeToken": oneTimeToken}, gdzie zmienna oneTimeToken oznacza uzyskany wcześniej token jednorazowy.
//    Wartość applicationId (X-App-Id) podana jest w pliku application.properties pod kluczem application.id. Odpowiada ona identyfikatorowi aplikacji użytkownika, podanemu przy rejestracji roli Menadżer aplikacji w PBN.
//    Wartość applicationToken (X-App-Token) podana jest w pliku application.properties pod kluczem application.token. Odpowiada ona tokenowi aplikacji użytkownika, uzyskanemu przy rejestracji roli Menadżer aplikacji w PBN.
//    Wartość baseAuthUrl podana jest w pliku application.properties pod kluczem base.auth.uri. Wartość tą trzeba dostosować, np. dla środowiska alpha należy podać https://pbn-micro-alpha.opi.org.pl/auth
//    Odpowiedź na żądanie zawiera ciało typu json, w którym pod kluczem X-User-Token zawarty jest token użytkownika. Zostaje on zapisany joko pole niniejszego serwisu, w celu późniejszego wykorzystania.
//    W przypadku wystąpienia błędu proszę o sprawdzenie poprawności parametrów w pliku application.properties (application.id, application.token, base.auth.uri).
    public void getUserToken(String oneTimeToken) throws JsonProcessingException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("X-App-Id", applicationId);
        requestHeaders.add("X-App-Token", applicationToken);
        Map<String, String> requestBody = Map.of("oneTimeToken", oneTimeToken);
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/pbn/api/user/token", baseAuthUri)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(requestBody, requestHeaders, HttpMethod.POST, uri);
        Type type = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class);

        log.info("Sending request for user token: uri {}, headers {}, body {}", uri, requestHeaders, objectMapper.writer().writeValueAsString(requestBody));
        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(requestEntity, ParameterizedTypeReference.forType(type));
        log.info("Received response: body {}", objectMapper.writer().writeValueAsString(responseEntity.getBody()));
        userToken = Optional.ofNullable(responseEntity.getBody()).map(body -> body.get("X-User-Token")).orElseThrow();
    }

//    Metoda dostarcza innym serwisom obiekt reprezentujący nagłówki dodawane do każdego żądania wysyłanego do API PBN.
//    Nagłówki są następujące:
//    (1) o nazwie „X-App-Id” i wartości zmiennej applicationId
//    (2) o nazwie „X-App-Token” i wartości zmiennej applicationToken,
//    Wartość applicationId (X-App-Id) podana jest w pliku application.properties pod kluczem application.id. Odpowiada ona identyfikatorowi aplikacji użytkownika, podanemu przy rejestracji roli Menadżer aplikacji w PBN.
//    Wartość applicationToken (X-App-Token) podana jest w pliku application.properties pod kluczem application.token. Odpowiada ona tokenowi aplikacji użytkownika, uzyskanemu w wyniku rejestracji roli Menadżer aplikacji w PBN.
    public HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-App-Id", applicationId);
        httpHeaders.add("X-App-Token", applicationToken);
        return httpHeaders;
    }

//    Metoda dostarcza innym serwisom obiekt reprezentujący nagłówki dodawane do każdego żądania wysyłanego do API PBN, które wymaga dodatkowo X-User-Token.
//    Nagłówki są następujące:
//    (1) o nazwie „X-App-Id” i wartości zmiennej applicationId
//    (2) o nazwie „X-App-Token” i wartości zmiennej applicationToken,
//    (2) o nazwie „X-User-Token” i wartości zmiennej userToken.
//    Wartość applicationId (X-App-Id) podana jest w pliku application.properties pod kluczem application.id. Odpowiada ona identyfikatorowi aplikacji użytkownika, podanemu przy rejestracji roli Menadżer aplikacji w PBN.
//    Wartość applicationToken (X-App-Token) podana jest w pliku application.properties pod kluczem application.token. Odpowiada ona tokenowi aplikacji użytkownika, uzyskanemu w wyniku rejestracji roli Menadżer aplikacji w PBN.
//    Wartość userToken (X-User-Token) została uzyskana za pomocą metody getUserToken (powyżej), a następnie zapisana jako pole niniejszego serwisu.
    public HttpHeaders getHeadersWithUserToken() {
        HttpHeaders httpHeaders = getHeaders();
        httpHeaders.add("X-User-Token", userToken);
        return httpHeaders;
    }

    public boolean hasToken() {
        return userToken != null;
    }
}

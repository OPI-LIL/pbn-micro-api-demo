package pl.org.opi.pbn.micro.api.demo.institution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.org.opi.pbn.micro.api.demo.token.TokenService;

import java.net.URI;
import java.util.Optional;

// Serwis zawiera metody wykonujące żądania REST do poszczególnych końcówek API PBN z grupy institution-controller (wyłączenie żądania typu GET).
// We wszystkich przypadkach typu: String ... = environment.getProperty("..."), tworzona jest zmienna o nazwie zgodnej z nazwą parametru ścieżki
// lub parameteru zapytania, podanymi w dokumentacji API PBN, przy czym arggumentem jest klucz z pliku application.properties, a zmienna uzyskuje wartość
// zgodną z wartością przypisaną do tego klucza w tym pliku. Tak utworzone zmienne wykorzystywane są do utworzenia właściwego adresu URI, zgodnego
// z dokumentacją API PBN. Przy utworzeniu adresu wykorzystywana jest także zmienna baseApiUri, której wartość podana jest w pliku application.properties
// pod kluczem base.api.uri. Wartość tą trzeba dostosować, np. dla środowiska alpha należy podać https://pbn-micro-alpha.opi.org.pl/api
// Do każdego żądania dodawane są nagłówki, pobierane z serwisu klasy TokenService za pomocą metody getHeaders, przy której podane szczegółowe informacje o tych nagłówkach.
// W każdym przypadku odpowiedż z końcówki API PBN przykazywana jest do właściwego kontrolera i wyświetlana na stronie aplikacji demo.
@Service
@Slf4j
public class InstitutionService {
    private final String baseApiUri;
    private final TokenService tokenService;
    private final RestTemplate restTemplate;
    private final Environment environment;

    public InstitutionService(@Value("${base.api.uri}") String baseApiUri, TokenService tokenService, RestTemplate restTemplate, Environment environment) {
        this.baseApiUri = baseApiUri;
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/institutions/page
    public ResponseEntity<String> getPagePyStatus() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String page = environment.getProperty("institution.page");
        String size = environment.getProperty("institution.size");
        String status = environment.getProperty("institution.status");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/institutions/page?page=%s&size=%s&status=%s", baseApiUri, page, size, status)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for page of institutions: uri {}, headers {}", uri, requestHeaders);
        log.info("Query params: page = {}, size = {}, status = {}", page, size, status);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/institutions/polon/page
    public ResponseEntity<String> getPagePolon() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String page = environment.getProperty("institution.page");
        String size = environment.getProperty("institution.size");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/institutions/polon/page?page=%s&size=%s", baseApiUri, page, size)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for page of POLON institutions: uri {}, headers {}", uri, requestHeaders);
        log.info("Query params: page = {}, size = {}", page, size);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/institutions/polon/uid/{uid}
    public ResponseEntity<String> getMetadataByPolonUid() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String uid = environment.getProperty("institution.polon.uid");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/institutions/polon/uid/%s", baseApiUri, uid)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for metadata of institution by POLON UID: uri {}, headers {}", uri, requestHeaders);
        log.info("Params: {uid} = {}", uid);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/institutions/polon/{id}
    public ResponseEntity<String> getMetadataByPolonId() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        Integer id = Optional.ofNullable(environment.getProperty("institution.polon.id")).map(Integer::parseInt).orElseThrow();
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/institutions/polon/%s", baseApiUri, id)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for metadata of institution by POLON ID: uri {}, headers {}", uri, requestHeaders);
        log.info("Params: {id} = {}", id);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/institutions/version/{version}
    public ResponseEntity<String> getMetadataByVersion() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String version = environment.getProperty("institution.version");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/institutions/version/%s", baseApiUri, version)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for metadata of institution by version: uri {}, headers {}", uri, requestHeaders);
        log.info("Params: {version} = {}", version);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/institutions/{id}
    public ResponseEntity<String> getExtendedMetadataById() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String id = environment.getProperty("institution.id");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/institutions/%s", baseApiUri, id)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for extended metadata of institution by id: uri {}, headers {}", uri, requestHeaders);
        log.info("Params: {id} = {}", id);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/institutions/{id}/metadata
    public ResponseEntity<String> getMetadataById() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String id = environment.getProperty("institution.id");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/institutions/%s/metadata", baseApiUri, id)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for metadata of institution by id: uri {}, headers {}", uri, requestHeaders);
        log.info("Params: {id} = {}", id);
        return restTemplate.exchange(requestEntity, String.class);
    }
}

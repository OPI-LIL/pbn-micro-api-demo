package pl.org.opi.pbn.micro.api.demo.publishers;

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

// Serwis zawiera metody wykonujące żądania REST do poszczególnych końcówek API PBN z grupy publishers-controller (wyłączenie żądania typu GET).
// We wszystkich przypadkach typu: String ... = environment.getProperty("..."), tworzona jest zmienna o nazwie zgodnej z nazwą parametru ścieżki
// lub parameteru zapytania, podanymi w dokumentacji API PBN, przy czym arggumentem jest klucz z pliku application.properties, a zmienna uzyskuje wartość
// zgodną z wartością przypisaną do tego klucza w tym pliku. Tak utworzone zmienne wykorzystywane są do utworzenia właściwego adresu URI, zgodnego
// z dokumentacją API PBN. Przy utworzeniu adresu wykorzystywana jest także zmienna baseApiUri, której wartość podana jest w pliku application.properties
// pod kluczem base.api.uri. Wartość tą trzeba dostosować, np. dla środowiska alpha należy podać https://pbn-micro-alpha.opi.org.pl/api
// Do każdego żądania dodawane są nagłówki, pobierane z serwisu klasy TokenService za pomocą metody getHeaders, przy której podane szczegółowe informacje o tych nagłówkach.
// W każdym przypadku odpowiedż z końcówki API PBN przykazywana jest do właściwego kontrolera i wyświetlana na stronie aplikacji demo.
@Service
@Slf4j
public class PublishersService {
    private final String baseApiUri;
    private final TokenService tokenService;
    private final RestTemplate restTemplate;
    private final Environment environment;

    public PublishersService(@Value("${base.api.uri}") String baseApiUri, TokenService tokenService, RestTemplate restTemplate, Environment environment) {
        this.baseApiUri = baseApiUri;
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/publishers/{publisherId}
    public ResponseEntity<String> getExtendedMetadataById() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String publisherId = environment.getProperty("publishers.publisherId");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publishers/%s", baseApiUri, publisherId)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for extended metadata of publisher by id: uri {}, headers {}", uri, requestHeaders);
        log.info("Params: {publisherId} = {}", publisherId);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/publishers/{publisherId}/metadata
    public ResponseEntity<String> getMetadataById() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String publisherId = environment.getProperty("publishers.publisherId");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publishers/%s/metadata", baseApiUri, publisherId)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for metadata of publisher by id: uri {}, headers {}", uri, requestHeaders);
        log.info("Params: {publisherId} = {}", publisherId);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/publishers/mnisw/page
    public ResponseEntity<String> getPageMnisw() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String page = environment.getProperty("publishers.page");
        String size = environment.getProperty("publishers.size");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publishers/mnisw/page?page=%s&size=%s", baseApiUri, page, size)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for page of MNiSW publishers: uri {}, headers {}", uri, requestHeaders);
        log.info("Query params: page = {}, size = {}", page, size);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/publishers/mnisw/page/yearlist
    public ResponseEntity<String> getPageMniswYearList() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String page = environment.getProperty("publishers.page");
        String size = environment.getProperty("publishers.size");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publishers/mnisw/page/yearlist?page=%s&size=%s", baseApiUri, page, size)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for page of MNiSW publishers with points: uri {}, headers {}", uri, requestHeaders);
        log.info("Query params: page = {}, size = {}", page, size);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/publishers/page
    public ResponseEntity<String> getPagePyStatus() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String page = environment.getProperty("publishers.page");
        String size = environment.getProperty("publishers.size");
        String status = environment.getProperty("publishers.status");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publishers/page?page=%s&size=%s&status=%s", baseApiUri, page, size, status)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for page of publishers: uri {}, headers {}", uri, requestHeaders);
        log.info("Query params: page = {}, size = {}, status = {}", page, size, status);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/publishers/version/{version}
    public ResponseEntity<String> getMetadataByVersion() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String version = environment.getProperty("publishers.version");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publishers/version/%s", baseApiUri, version)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for metadata of publisher by version: uri {}, headers {}", uri, requestHeaders);
        log.info("Params: {version} = {}", version);
        return restTemplate.exchange(requestEntity, String.class);
    }
}

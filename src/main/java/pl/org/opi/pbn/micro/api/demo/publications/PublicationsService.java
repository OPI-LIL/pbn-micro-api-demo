package pl.org.opi.pbn.micro.api.demo.publications;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.org.opi.pbn.micro.api.demo.token.TokenService;

import java.net.URI;

// Serwis zawiera metody wykonujące żądania REST do poszczególnych końcówek API PBN z grupy publications-controller (żądania typu GET i POST).
// We wszystkich przypadkach typu: String ... = environment.getProperty("..."), tworzona jest zmienna o nazwie zgodnej z nazwą parametru ścieżki
// lub parameteru zapytania, podanymi w dokumentacji API PBN, przy czym argumentem jest klucz z pliku application.properties, a zmienna uzyskuje wartość
// zgodną z wartością przypisaną do tego klucza w tym pliku. Tak utworzone zmienne wykorzystywane są do utworzenia właściwego adresu URI, zgodnego
// z dokumentacją API PBN. Przy utworzeniu adresu wykorzystywana jest także zmienna baseApiUri, której wartość podana jest w pliku application.properties
// pod kluczem base.api.uri. Wartość tą trzeba dostosować, np. dla środowiska alpha należy podać https://pbn-micro-alpha.opi.org.pl/api
// Do każdego żądania dodawane są nagłówki, pobierane z serwisu klasy TokenService za pomocą metody getHeaders, przy której podane szczegółowe informacje o tych nagłówkach.
// W przypadku żądań POST dołączane jest ciało typu json, wprowadzone przez użytkownika na stronie aplikacji demo.
// W każdym przypadku odpowiedż z końcówki API PBN przykazywana jest do właściwego kontrolera i wyświetlana na stronie aplikacji demo.
@Service
@Slf4j
public class PublicationsService {
    private final String baseApiUri;
    private final TokenService tokenService;
    private final RestTemplate restTemplate;
    private final Environment environment;

    public PublicationsService(@Value("${base.api.uri}") String baseApiUri, TokenService tokenService, RestTemplate restTemplate, Environment environment) {
        this.baseApiUri = baseApiUri;
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/publications/{publicationId}/metadata
    public ResponseEntity<String> getMetadataById() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String publicationId = environment.getProperty("publications.publicationId");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publications/%s/metadata", baseApiUri, publicationId)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for metadata of publication by id: uri {}, headers {}", uri, requestHeaders);
        log.info("Params: {publicationId} = {}", publicationId);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/publications/doi
    public ResponseEntity<String> getMetadataByDoi() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String doi = environment.getProperty("publications.doi");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publications/doi?doi=%s", baseApiUri, doi)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for metadata of publication by doi: uri {}, headers {}", uri, requestHeaders);
        log.info("Query params: doi = {}", doi);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/publications/id/{id}
    public ResponseEntity<String> getExtendedMetadataById() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String id = environment.getProperty("publications.id");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publications/id/%s", baseApiUri, id)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for extended metadata of publication by id: uri {}, headers {}", uri, requestHeaders);
        log.info("Params: {id} = {}", id);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/publications/page
    public ResponseEntity<String> getPagePyStatus() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String page = environment.getProperty("publications.page");
        String size = environment.getProperty("publications.size");
        String status = environment.getProperty("publications.status");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publications/page?page=%s&size=%s&status=%s", baseApiUri, page, size, status)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for page of publications: uri {}, headers {}", uri, requestHeaders);
        log.info("Query params: page = {}, size = {}, status = {}", page, size, status);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/publications/version/{version}
    public ResponseEntity<String> getMetadataByVersion() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        String version = environment.getProperty("publications.version");
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publications/version/%s", baseApiUri, version)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for metadata of publication by version: uri {}, headers {}", uri, requestHeaders);
        log.info("Params: {version} = {}", version);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania POST na końcówkę /v1/publications. Argumentem metody jest ciąg znaków w formacie json,
//    wprowadzony przez użytkownika na stronie aplikacji demo. Przykładowy json zwarty jest w pliku single-article.json,
//    jednakże w zależności od środowiska, na którym będzie wykonana metoda, dane zawarte w tym pliku mogą wymagać modyfikacji,
//    np. w zakresie identyfikatorów podanych obiektów.
    public ResponseEntity<String> addOrEditSinglePublication(String publication) {
        HttpHeaders requestHeaders = tokenService.getHeadersWithUserToken();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publications", baseApiUri)).build().toUri();
        RequestEntity<String> requestEntity = new RequestEntity<>(publication, requestHeaders, HttpMethod.POST, uri);
        log.info("Sending POST request to add or edit publication: uri {}, headers {}", uri, requestHeaders);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania POST na końcówkę /v1/publications/import. Argumentem metody jest ciąg znaków w formacie json,
//    wprowadzony przez użytkownika na stronie aplikacji demo. Przykładowy json zwarty jest w pliku multiple-book-and-chapter.json,
//    jednakże w zależności od środowiska, na którym będzie wykonana metoda, dane zawarte w tym pliku mogą wymagać modyfikacji,
//    np. w zakresie identyfikatorów podanych obiektów.
    public ResponseEntity<String> addOrEditMultiplePublications(String importPublicationsData) {
        HttpHeaders requestHeaders = tokenService.getHeadersWithUserToken();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/publications/import", baseApiUri)).build().toUri();
        RequestEntity<String> requestEntity = new RequestEntity<>(importPublicationsData, requestHeaders, HttpMethod.POST, uri);
        log.info("Sending POST request to add or edit multiple publications: uri {}, headers {}", uri, requestHeaders);
        return restTemplate.exchange(requestEntity, String.class);
    }
}

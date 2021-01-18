package pl.org.opi.pbn.micro.api.demo.dictionary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.org.opi.pbn.micro.api.demo.token.TokenService;

import java.net.URI;

// Serwis zawiera metody wykonujące żądania REST do poszczególnych końcówek API PBN z grupy dictionary-controller (wyłączenie żądania typu GET).
// We wszystkich przypadkach typu: String ... = environment.getProperty("..."), tworzona jest zmienna o nazwie zgodnej z nazwą parametru ścieżki
// lub parameteru zapytania, podanymi w dokumentacji API PBN, przy czym argumentem jest klucz z pliku application.properties, a zmienna uzyskuje wartość
// zgodną z wartością przypisaną do tego klucza w tym pliku. Tak utworzone zmienne wykorzystywane są do utworzenia właściwego adresu URI, zgodnego
// z dokumentacją API PBN. Przy utworzeniu adresu wykorzystywana jest także zmienna baseApiUri, której wartość podana jest w pliku application.properties
// pod kluczem base.api.uri. Wartość tą trzeba dostosować, np. dla środowiska alpha należy podać https://pbn-micro-alpha.opi.org.pl/api
// Do każdego żądania dodawane są nagłówki, pobierane z serwisu klasy TokenService za pomocą metody getHeaders, przy której podane szczegółowe informacje o tych nagłówkach.
// W każdym przypadku odpowiedż z końcówki API PBN przykazywana jest do właściwego kontrolera i wyświetlana na stronie aplikacji demo.
@Service
@Slf4j
public class DictionaryService {
    private final String baseApiUri;
    private final TokenService tokenService;
    private final RestTemplate restTemplate;

    public DictionaryService(@Value("${base.api.uri}") String baseApiUri, TokenService tokenService, RestTemplate restTemplate) {
        this.baseApiUri = baseApiUri;
        this.tokenService = tokenService;
        this.restTemplate = restTemplate;
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/dictionary/countries
    public ResponseEntity<String> getCountries() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/dictionary/countries", baseApiUri)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for countries: uri {}, headers {}", uri, requestHeaders);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/dictionary/disciplines
    public ResponseEntity<String> getDisciplines() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/dictionary/disciplines", baseApiUri)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for disciplines: uri {}, headers {}", uri, requestHeaders);
        return restTemplate.exchange(requestEntity, String.class);
    }

//    Metoda służy do wykonania żądania GET na końcówkę /v1/dictionary/languages
    public ResponseEntity<String> getLanguages() {
        HttpHeaders requestHeaders = tokenService.getHeaders();
        URI uri = UriComponentsBuilder.fromUriString(String.format("%s/v1/dictionary/languages", baseApiUri)).build().toUri();
        RequestEntity<?> requestEntity = new RequestEntity<>(null, requestHeaders, HttpMethod.GET, uri);
        log.info("Sending GET request for languages: uri {}, headers {}", uri, requestHeaders);
        return restTemplate.exchange(requestEntity, String.class);
    }
}

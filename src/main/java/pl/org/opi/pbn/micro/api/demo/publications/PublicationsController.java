package pl.org.opi.pbn.micro.api.demo.publications;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

// Niniejszy kontroler służy do obsługi żądań z niniejszej aplikacji demo. Przekazuje żądanie do serwisu, który zajmuje się
// komunikacją z API PBN, a odpowiedź uzyskaną z serwisu przekazuje do wyświetlenia na stronie aplikacji demo.
// Kontroler pośredniczy przy obsłudze API PBN z grupy publications-controller (żądania typu GET i POST).
// W przypadku, gdy wynikiem żądania POST jest odpowiedź o statusie http 400 "Bad Request", co najprawdopodobniej oznacza,
// że wysłany json nie przeszedł walidacji - odpowiedź uzyskana z API PBN jest przekształcana w taki sposób,
// aby ciało tej odpowiedzi było odebrane przez przeglądarkę i wyświetlone na stronie aplikacji demo.
// Konieczność takiego przekształcenia jest spowodowana specyfiką używanego frameworka.
@RestController
@RequestMapping("/publications")
public class PublicationsController {
    private final PublicationsService publicationsService;
    private final ObjectMapper objectMapper;

    public PublicationsController(PublicationsService publicationsService, ObjectMapper objectMapper) {
        this.publicationsService = publicationsService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/metadata-by-id")
    public ResponseEntity<String> getMetadataById() {
        return publicationsService.getMetadataById();
    }

    @GetMapping("/metadata-by-doi")
    public ResponseEntity<String> getMetadataByDoi() {
        return publicationsService.getMetadataByDoi();
    }

    @GetMapping("/extended-metadata-by-id")
    public ResponseEntity<String> getExtendedMetadataById() {
        return publicationsService.getExtendedMetadataById();
    }

    @GetMapping("/page-by-status")
    public ResponseEntity<String> getPagePyStatus() {
        return publicationsService.getPagePyStatus();
    }

    @GetMapping("/metadata-by-version")
    public ResponseEntity<String> getMetadataByVersion() {
        return publicationsService.getMetadataByVersion();
    }

    @PostMapping("/add-or-edit-single-publication")
    public ResponseEntity<?> addOrEditSinglePublication(@RequestBody String json) throws IOException {
        ResponseEntity<String> responseEntity = publicationsService.addOrEditSinglePublication(json);
        return responseEntity.getStatusCode() != HttpStatus.BAD_REQUEST || responseEntity.getBody() == null ? responseEntity
                : ResponseEntity.status(responseEntity.getStatusCode()).body(objectMapper.readValue(responseEntity.getBody(), Object.class));
    }

    @PostMapping("/add-or-edit-multiple-publications")
    public ResponseEntity<?> addOrEditMultiplePublications(@RequestBody String json) throws IOException {
        ResponseEntity<String> responseEntity = publicationsService.addOrEditMultiplePublications(json);
        return responseEntity.getStatusCode() != HttpStatus.BAD_REQUEST || responseEntity.getBody() == null ? responseEntity
                : ResponseEntity.status(responseEntity.getStatusCode()).body(objectMapper.readValue(responseEntity.getBody(), Object.class));
    }
}

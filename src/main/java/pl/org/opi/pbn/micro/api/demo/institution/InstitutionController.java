package pl.org.opi.pbn.micro.api.demo.institution;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Niniejszy kontroler służy do obsługi żądań z niniejszej aplikacji demo. Przekazuje żądanie do serwisu, który zajmuje się
// komunikacją z API PBN, a odpowiedź uzyskaną z serwisu przekazuje do wyświetlenia na stronie aplikacji demo.
// Kontroler pośredniczy przy obsłudze API PBN z grupy institution-controller (wyłączenie żądania typu GET)
@RestController
@RequestMapping("/institution")
public class InstitutionController {
    private final InstitutionService institutionService;

    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping("/page-by-status")
    public ResponseEntity<String> getPagePyStatus() {
        return institutionService.getPagePyStatus();
    }

    @GetMapping("/page-polon")
    public ResponseEntity<String> getPagePolon() {
        return institutionService.getPagePolon();
    }

    @GetMapping("/metadata-by-polon-uid")
    public ResponseEntity<String> getMetadataByPolonUid() {
        return institutionService.getMetadataByPolonUid();
    }

    @GetMapping("/metadata-by-polon-id")
    public ResponseEntity<String> getMetadataByPolonId() {
        return institutionService.getMetadataByPolonId();
    }

    @GetMapping("/metadata-by-version")
    public ResponseEntity<String> getMetadataByVersion() {
        return institutionService.getMetadataByVersion();
    }

    @GetMapping("/extended-metadata-by-id")
    public ResponseEntity<String> getExtendedMetadataById() {
        return institutionService.getExtendedMetadataById();
    }

    @GetMapping("/metadata-by-id")
    public ResponseEntity<String> getMetadataById() {
        return institutionService.getMetadataById();
    }
}

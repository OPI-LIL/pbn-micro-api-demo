package pl.org.opi.pbn.micro.api.demo.conferences;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Niniejszy kontroler służy do obsługi żądań z niniejszej aplikacji demo. Przekazuje żądanie do serwisu, który zajmuje się
// komunikacją z API PBN, a odpowiedź uzyskaną z serwisu przekazuje do wyświetlenia na stronie aplikacji demo.
// Kontroler pośredniczy przy obsłudze API PBN z grupy conferences-controller (wyłączenie żądania typu GET)
@RestController
@RequestMapping("/conferences")
public class ConferencesController {
    private final ConferencesService conferencesService;

    public ConferencesController(ConferencesService conferencesService) {
        this.conferencesService = conferencesService;
    }

    @GetMapping("/page-mnisw")
    public ResponseEntity<String> getPageMnisw() {
        return conferencesService.getPageMnisw();
    }

    @GetMapping("/page-by-status")
    public ResponseEntity<String> getPagePyStatus() {
        return conferencesService.getPagePyStatus();
    }

    @GetMapping("/metadata-by-version")
    public ResponseEntity<String> getMetadataByVersion() {
        return conferencesService.getMetadataByVersion();
    }

    @GetMapping("/extended-metadata-by-id")
    public ResponseEntity<String> getExtendedMetadataById() {
        return conferencesService.getExtendedMetadataById();
    }

    @GetMapping("/editions")
    public ResponseEntity<String> getEditions() {
        return conferencesService.getEditions();
    }

    @GetMapping("/metadata-by-id")
    public ResponseEntity<String> getMetadataById() {
        return conferencesService.getMetadataById();
    }
}

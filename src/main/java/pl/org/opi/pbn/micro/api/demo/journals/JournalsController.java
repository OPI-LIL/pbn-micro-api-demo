package pl.org.opi.pbn.micro.api.demo.journals;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Niniejszy kontroler służy do obsługi żądań z niniejszej aplikacji demo. Przekazuje żądanie do serwisu, który zajmuje się
// komunikacją z API PBN, a odpowiedź uzyskaną z serwisu przekazuje do wyświetlenia na stronie aplikacji demo.
// Kontroler pośredniczy przy obsłudze API PBN z grupy journals-controller (wyłączenie żądania typu GET)
@RestController
@RequestMapping("/journals")
public class JournalsController {
    private final JournalsService journalsService;

    public JournalsController(JournalsService journalsService) {
        this.journalsService = journalsService;
    }

    @GetMapping("/page-mnisw")
    public ResponseEntity<String> getPageMnisw() {
        return journalsService.getPageMnisw();
    }

    @GetMapping("/page-by-status")
    public ResponseEntity<String> getPagePyStatus() {
        return journalsService.getPagePyStatus();
    }

    @GetMapping("/metadata-by-version")
    public ResponseEntity<String> getMetadataByVersion() {
        return journalsService.getMetadataByVersion();
    }

    @GetMapping("/extended-metadata-by-id")
    public ResponseEntity<String> getExtendedMetadataById() {
        return journalsService.getExtendedMetadataById();
    }

    @GetMapping("/metadata-by-id")
    public ResponseEntity<String> getMetadataById() {
        return journalsService.getMetadataById();
    }
}

package pl.org.opi.pbn.micro.api.demo.publishers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Niniejszy kontroler służy do obsługi żądań z niniejszej aplikacji demo. Przekazuje żądanie do serwisu, który zajmuje się
// komunikacją z API PBN, a odpowiedź uzyskaną z serwisu przekazuje do wyświetlenia na stronie aplikacji demo.
// Kontroler pośredniczy przy obsłudze API PBN z grupy publishers-controller (wyłączenie żądania typu GET)
@RestController
@RequestMapping("/publishers")
public class PublishersController {
    private final PublishersService publishersService;

    public PublishersController(PublishersService publishersService) {
        this.publishersService = publishersService;
    }

    @GetMapping("/extended-metadata-by-id")
    public ResponseEntity<String> getExtendedMetadataById() {
        return publishersService.getExtendedMetadataById();
    }

    @GetMapping("/metadata-by-id")
    public ResponseEntity<String> getMetadataById() {
        return publishersService.getMetadataById();
    }

    @GetMapping("/page-mnisw")
    public ResponseEntity<String> getPageMnisw() {
        return publishersService.getPageMnisw();
    }

    @GetMapping("/page-mnisw-yearlist")
    public ResponseEntity<String> getPageMniswYearList() {
        return publishersService.getPageMniswYearList();
    }

    @GetMapping("/page-by-status")
    public ResponseEntity<String> getPagePyStatus() {
        return publishersService.getPagePyStatus();
    }

    @GetMapping("/metadata-by-version")
    public ResponseEntity<String> getMetadataByVersion() {
        return publishersService.getMetadataByVersion();
    }
}

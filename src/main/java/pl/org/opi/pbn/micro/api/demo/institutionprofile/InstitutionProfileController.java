package pl.org.opi.pbn.micro.api.demo.institutionprofile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Niniejszy kontroler służy do obsługi żądań z niniejszej aplikacji demo. Przekazuje żądanie do serwisu, który zajmuje się
// komunikacją z API PBN, a odpowiedź uzyskaną z serwisu przekazuje do wyświetlenia na stronie aplikacji demo.
// Kontroler pośredniczy przy obsłudze API PBN z grupy institution-profile-controller (wyłączenie żądania typu GET)
@RestController
@RequestMapping("/institution-profile")
public class InstitutionProfileController {
    private final InstitutionProfileService institutionProfileService;

    public InstitutionProfileController(InstitutionProfileService institutionProfileService) {
        this.institutionProfileService = institutionProfileService;
    }

    @GetMapping("/publications-page")
    public ResponseEntity<String> getPublicationsPage() {
        return institutionProfileService.getPublicationsPage();
    }

    @GetMapping("/statements-page")
    public ResponseEntity<String> getStatementsPage() {
        return institutionProfileService.getStatementsPage();
    }
}

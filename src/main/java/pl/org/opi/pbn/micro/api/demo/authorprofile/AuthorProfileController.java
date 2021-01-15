package pl.org.opi.pbn.micro.api.demo.authorprofile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Niniejszy kontroler służy do obsługi żądań z niniejszej aplikacji demo. Przekazuje żądanie do serwisu, który zajmuje się
// komunikacją z API PBN, a odpowiedź uzyskaną z serwisu przekazuje do wyświetlenia na stronie aplikacji demo.
// Kontroler pośredniczy przy obsłudze API PBN z grupy author-profile-controller (wyłączenie żądania typu GET)
@RestController
@RequestMapping("/author-profile")
public class AuthorProfileController {
    private final AuthorProfileService authorProfileService;

    public AuthorProfileController(AuthorProfileService authorProfileService) {
        this.authorProfileService = authorProfileService;
    }

    @GetMapping("/profile-by-id")
    public ResponseEntity<String> getProfileById() {
        return authorProfileService.getProfileById();
    }
}

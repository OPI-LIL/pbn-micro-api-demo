package pl.org.opi.pbn.micro.api.demo.person;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Niniejszy kontroler służy do obsługi żądań z niniejszej aplikacji demo. Przekazuje żądanie do serwisu, który zajmuje się
// komunikacją z API PBN, a odpowiedź uzyskaną z serwisu przekazuje do wyświetlenia na stronie aplikacji demo.
// Kontroler pośredniczy przy obsłudze API PBN z grupy person-controller (wyłączenie żądania typu GET)
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/extended-metadata-by-id")
    public ResponseEntity<String> getExtendedMetadataById() {
        return personService.getExtendedMetadataById();
    }

    @GetMapping("/employees-by-institution-id")
    public ResponseEntity<String> getEmployeesByInstitutionId() {
        return personService.getEmployeesByInstitutionId();
    }

    @GetMapping("/metadata-by-natural-id")
    public ResponseEntity<String> getMetadataNaturalById() {
        return personService.getMetadataNaturalById();
    }

    @GetMapping("/page-by-status")
    public ResponseEntity<String> getPagePyStatus() {
        return personService.getPagePyStatus();
    }

    @GetMapping("/metadata-by-polon-uid")
    public ResponseEntity<String> getMetadataByPolonUid() {
        return personService.getMetadataByPolonUid();
    }

    @GetMapping("/metadata-by-version")
    public ResponseEntity<String> getMetadataByVersion() {
        return personService.getMetadataByVersion();
    }
}

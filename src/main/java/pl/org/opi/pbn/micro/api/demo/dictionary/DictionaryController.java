package pl.org.opi.pbn.micro.api.demo.dictionary;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Niniejszy kontroler służy do obsługi żądań z niniejszej aplikacji demo. Przekazuje żądanie do serwisu, który zajmuje się
// komunikacją z API PBN, a odpowiedź uzyskaną z serwisu przekazuje do wyświetlenia na stronie aplikacji demo.
// Kontroler pośredniczy przy obsłudze API PBN z grupy dictionary-controller (wyłączenie żądania typu GET)
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    private final DictionaryService dictionaryService;

    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/countries")
    public ResponseEntity<String> getCountries() {
        return dictionaryService.getCountries();
    }

    @GetMapping("/disciplines")
    public ResponseEntity<String> getDisciplines() {
        return dictionaryService.getDisciplines();
    }

    @GetMapping("/languages")
    public ResponseEntity<String> getLanguages() {
        return dictionaryService.getLanguages();
    }
}

package pl.org.opi.pbn.micro.api.demo.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.org.opi.pbn.micro.api.demo.token.TokenService;

@Controller
@Slf4j
public class HomeController {
    private final String baseAuthUrl;
    private final String applicationId;
    private final TokenService tokenService;

    public HomeController(@Value("${base.auth.uri}") String baseAuthUrl, @Value("${application.id}") String applicationId,
                          TokenService tokenService) {
        this.baseAuthUrl = baseAuthUrl;
        this.applicationId = applicationId;
        this.tokenService = tokenService;
    }

//    Końcówka służy do dostarczenia danych na stronę aplikacji demo:
//    – adresu do uzyskania tokena jednorazowego (Get token)
//    - informacji, czy uzyskano token użytkownika (jeżeli tak, wyświetlane są elementy służące do wykonywania poszczególnych zapytań).
//    Po użyciu linka „Get token” następuje przekierowanie na adres służący do uzyskania jednorazowego tokena "oneTimeTokenUrl".
//    Wymagane jest, aby w tej samej przeglądarce użytkownik zalogowany był do aplikacji PBN. Z tego adresu nastąpi przekierowanie
//    na adres zwrotny aplikacji użytkownika, podany przy rejestracji roli Menadżer aplikacji w PBN.
//    Wartość baseAuthUrl podana jest w pliku application.properties pod kluczem base.auth.uri. Wartość tą trzeba dostosować,
//    np. dla środowiska alpha należy podać https://pbn-micro-alpha.opi.org.pl/auth
//    Wartość applicationId podana jest w pliku application.properties pod kluczem application.id. Odpowiada ona identyfikatorowi
//    aplikacji użytkownika, podanemu przy rejestracji roli Menadżer aplikacji w PBN (X-App-Id).
    @GetMapping("/")
    public String startPage(Model model) {
        model.addAttribute("hasToken", tokenService.hasToken());
        model.addAttribute("oneTimeTokenUrl", String.format("%s/pbn/api/registration/user/token/%s", baseAuthUrl, applicationId));
        return "home";
    }

//    Końcówka znajduje się pod adresem podanym przy rejestracji roli Menadżer aplikacji w PBN. Rolą końcówki jest pozyskanie
//    tokenu użytkownika na podstawie jednorazowego kodu z adresu url (zapytanie, parametr „ott”) przekazanego do obsługi
//    do serwisu klasy TokenService, w którym opisano dalsze kroki.
//    Przykładowe zapytanie do końcówki to: "localhost:8899/one-time-token?ott=c69df69c-f1da-4404-b3ea-4031b7399c45"
    @GetMapping("/one-time-token")
    public String oneTimeTokenCallback(@RequestParam("ott") String oneTimeToken) throws JsonProcessingException {
        log.info("Received one time token {}", oneTimeToken);
        tokenService.getUserToken(oneTimeToken);
        return "redirect:";
    }
}

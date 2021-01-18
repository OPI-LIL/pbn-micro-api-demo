# PBN API DEMO
Prosta aplikacja demonstrująca sposób integracji z API PBN.

## Technologie
- Java 11
- Spring Boot 2.0.1
- Maven 4.0.0

## Uruchomienie
W celu skorzystania z aplikacji należy:
- dostosować do własnych potrzeb dane w pliku application.properties;
- zbudować archiwum jar za pomocą poniższego polecenia (archiwum zostanie wygenerowanie w folderze {główny folder projektu}/target):
```console
mvn clean install
```
- uruchomić aplikację za pomocą polecenia:
```console
java -jar -Dspring.config.location={ścieżka do folderu z plikiem application.properties}/application.properties {ścieżka do folderu z plikiem jar}/pbn-micro-api-demo.jar
```

Możliwe jest także uruchomienie aplikacji z wykorzystaniem środowiska programistycznego.

Po uruchomieniu aplikcja będzie dostępna pod adresem [http://localhost:8899/](http://localhost:8899/),
chyba że w pliku application.properties wprowadzone zostaną inne wartości dla kluczy
server.servlet.context-path i server.port.

## Jak zacząć

1. Dostosowujemy plik application.properties, między innymi ustawiamy wartość pól:
- base.auth.uri - adres autoryzacji w systemie PBN np. dla środowiska alpha należy podać https://pbn-micro-alpha.opi.org.pl/auth
- base.api.uri - adres API w systemie PBN np. dla środowiska alpha należy podać https://pbn-micro-alpha.opi.org.pl/api
- application.id - identyfikator aplikacji użytkownika
- application.token - token aplikacji użytkownika, uzyskany przy rejestracji roli Menadżer aplikacji w PBN
2. Wchodzimy na adres, pod którym dostępna jest aplikacja (domyślnie  [http://localhost:8899/](http://localhost:8899/))
3. Logujemy się w tej samej przeglądarce do systemu PBN, z któym chcemy się integrować
4. Przechodzimy pod link na stronie oznaczony "Pobierz token użytkownika".
5. Zostajemy przeniesieni na adres zwrotny aplikacji instytucji, w ramach której wystawienia tokenu żądano. Do adresu doklejony zostanie jednorazowy kod, który służy do pozyskania właściwego tokenu uwierzytelniającego (tokenu użytkownika)
6. Przechodzimy pod adres końcówki, która jest odpowiedzialna za pozyskanie tokenu użytkownika. Do adresu doklejamy pozyskany jednorazowy kod. Przykładowe zapytanie dla domyślnych ustawień: [http://localhost:8899/one-time-token?ott=c69df69c-f1da-4404-b3ea-4031b7399c45](http://localhost:8899/one-time-token?ott=)
 UWAGA ta operacja wygeneruje nowy token użytkownika, stary token przestanie być aktywny
7. Zostajemy przeniesieni na stronę, na której możemy przetestować końcówki API PBN

## Informacje o aplikacji
Wszystkie niezbędne informacje podano w komentarzach do poszczególnych klas i metod.
Struktura projektu:
```
.
|-- java\pl\org\opi\pbn\micro\api\demo
|   |-- authorprofile - pliki obsługi author-profile-controller 
|   |-- conferences - pliki obsługi conferences-controller
|   |-- dictionary - pliki obsługi dictionary-controller
|   |-- home - pliki obsługi strony głównej
|   |-- institution - pliki obsługi institution-controller
|   |-- institutionprofile - pliki obsługi institution-profile-controller
|   |-- journals - pliki obsługi journals-controller
|   |-- person - pliki obsługi person-controller
|   |-- publications - pliki obsługi publications-controller
|   |-- publishers - pliki obsługi publishers-controller
|   |-- token - pliki obsługi pozyskania tokenu
`-- resources
    |-- data
    |   |-- multiple-book-and-chapter.json - przykładowy argument dla /v1/publications
    |   |-- single-article.json - przykładowy argument dla /v1/publications/import
    |-- templates
    `-- application.properties
```

## Dokumentacja API PBN
- [Centrum pomocy](https://pbn.nauka.gov.pl/centrum-pomocy/baza-wiedzy-kategoria/masowe-interfejsy-wymiany-danych/)
- [Opis końcówek (środowisko testowe)](https://pbn-micro-alpha.opi.org.pl/api/)
- [Opis końcówek (środowisko produkcyjne)](https://pbn.nauka.gov.pl/api/)

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

## Informacje o aplikacji
Wszystkie niezbędne informacje podano w komentarzach do poszczególnych klas i metod.

## Dokumentacja API PBN
- [Strony pomocy (środowisko testowe)](https://pbn-micro-alpha.opi.org.pl/centrum-pomocy/baza-wiedzy-kategoria/masowe-interfejsy-wymiany-danych/)
- [Opis końcówek (środowisko testowe)](https://pbn-micro-alpha.opi.org.pl/api/)
- [Strony pomocy (środowisko produkcyjne)](https://pbn.nauka.gov.pl/centrum-pomocy/baza-wiedzy-kategoria/masowe-interfejsy-wymiany-danych/)
- [Opis końcówek (środowisko produkcyjne)](https://pbn.nauka.gov.pl/api/)

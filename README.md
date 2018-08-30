# ZTI

## Demo live
Serwer: https://ztiserver.eu-gb.mybluemix.net
Klient: https://ztifront.herokuapp.com/


## Instrukcja uruchomieniowa lokalnie

 ####Baza danych
Należy wskazać w pliku konfiguracyjnym serwera (server/src/main/resources/application.yml)
adres i dane logowania do bazy DB2.

 ####Serwer
Jest to aplikacja Spring boot.
Aby ją uruchomić należy zainstalować zależności: ``mvn install``, a następnie uruchomić
korzystając z polecenia: ``mvn spring-boot:run``.
W ten sposób uruchomiony zostanie serwer na localnym hoście

 ####Klient
Jest to aplikacja React.js.
Należy w pliku front/src/constants/index.js podać pod polem ``API_BASE_URL`` adres serwera oraz pod polem ``SERVICE_TOKEN``
token serwisu.
Aby uruchomić aplikację należy zainstalować zależności: ``npm install`` i uruchomic: ``npm run start``.
W ten sposób uruchomiony zostanie serwer node.js operujący jako klient aplikacji na localnym hoście



## Zdalnie wdrożone rozwiązanie

 ####Baza danych
Baza danych DB2 została wdrożona w chmurze bluemix

 ####Serwer
Serwer został zdeployowany przy pomocy ``java_buildpack`` w chmurze bluemix. Wykorzystano do tego ``fat jar`` i CLI IBM cloud

 ####Klient
Aplikacja klienta została zdeployowana w chmurze Heroku przy pomocy ``create-react-app-buildpack``.



 ##Dodatkowe informacje
 W katalogu doc/uml znajdują się szczegółowe diagramy klas częsci klienta i serwerowej.
 W katalogu doc/javadoc znajduje się dokumentacja kodu częsci serwerowej.
 W pliku doc/doc.pdf znajduje się dokumentacja projektu
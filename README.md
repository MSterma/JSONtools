# JSON tools
![example workflow](https://github.com/MSterma/JSONtools/actions/workflows/ci.yml/badge.svg)

> **Opis:**
> Aplikacja dla programistów, którzy potrzebują przeformatować lub filtrować struktury danych zapisane w formacie JSON a także porównać ze soba struktury. JSON tools pozwala zarówno na zminifikowanie niezminifikowanej reprezentacji JSON, a także na operację odwrotną (z dodaniem wszelkich odstępów i nowych linii). Aplikacja będzie dostępna poprzez GUI, a także jako zdalne API, dzieki czemu można ją zintegrować z istniejącymi narzędziami.
![diagram_uml]([http://url/to/img.png](https://github.com/MSterma/JSONtools/blob/rest-api/JSONTools_UML.png))
---

## Product Backlog

| Element rejestru produktu | Ważność | Pracochłonność | Business Value (BV) | Kryteria akceptacji |
| :--- | :--- | :--- | :--- | :--- |
| Jako twórca zewnętrznego oprogramowania, mogę korzystać z dostępnych funkcji zdalnie poprzez REST, aby móc zintegrować narzędzie z innymi aplikacjami. | 20 | 5 | 1 | o Wejście w formacie JSON zawierające strukturę do transformacji<br>o Wejście transformowane do postaci modelu obiektowego <br>o Wyjście w formacie JSON<br>o REST API umożliwia uruchomienie dowolnej aktualnie zaimplementowanej funkcji. |
| Jako programista mogę uzyskać zminifikowaną strukturę w formacie JSON na podstawie pełnego zapisu w formacie JSON, aby zmniejszyć rozmiar danych. | 12 | 2 | 1 | o Kształt danych nie ulega zmianie<br>o Zapis nie zawiera niepotrzebnych spacji<br>o Zapis mieści się w jednej linii<br>o Zgłoszenie błędu dla niepoprawnego JSON-a |
| Jako programista mogę uzyskać pełną strukturę w formacie JSON ze <br>zminifikowanego zapisu w formacie JSON, aby polepszyć czytelność danych. | 12 | 2 | 1 | o Kształt danych nie ulega zmianie<br>o Zapis zawiera prawidłowe wcięcia<br>o Zgłoszenie błędu dla niepoprawnego JSON-a |
| Jako programista mogę uzyskać strukturę w formacie JSON zawierającą tylko określone własności, aby uprościć strukturę | 10 | 3 | 1 | o Kształt danych nie ulega zmianie<br>o Z obiektów JSON usuwane są wszystkie własności poza tymi przekazanymi na wejściu<br>o Zgłoszenie błędu dla niepoprawnego JSON-a |
| Jako programista mogę uzyskać strukturę w formacie JSON nie zawierającą określonych własności, aby uprościć strukturę | 10 | 3 | 1 | o Kształt danych nie ulega zmianie<br>o Obiekty JSON posiadają tylko wybrane własności (wszystkie inne są usuwane)<br>o Zgłoszenie błędu dla niepoprawnego JSON-a |
| Jako programista mogę porównać dwa teksty (np.<br>reprezentujące strukturę danych w formatach JSON) oczekując, że aplikacja pokaże linijki, w których występuje różnica. | 9 | 4 | 1 | o Prawidłowo podawane linie z różnicami<br>o Dla obu pustych tekstów nie są znajdowane różnice<br>o Dla jednego pustego tekstu cały tekst zaznaczany jest jako różnica |
| Jako użytkownik mogę skorzystać z aplikacji za pomocą interfejsu użytkownika | | | | Do późniejszej negocjacji |

---

## Sprint Backlog #1

**Cel Sprintu:**

| Element rejestru produktu | Zadania | Pracochłonność |
| :--- | :--- | :--- |
| Jako twórca zewnętrznego oprogramowania, mogę korzystać z dostępnych funkcji zdalnie poprzez REST, aby móc zintegrować narzędzie z innymi aplikacjami. | | 5 |
| | Model danych | 2 |
| | Dodanie poszczególnych kontrolerów i tras | 2 |
| | Weryfikacja poprawności | 1 |
| Jako programista mogę uzyskać zminifikowaną strukturę w formacie JSON na podstawie pełnego zapisu w formacie JSON, aby zmniejszyć rozmiar danych. | | 2 |
| | Zaimplementowanie algorytmu | 1 |
| | Weryfikacja poprawności danych wejściowych | 1 |
| Jako programista mogę uzyskać pełną strukturę w formacie JSON ze zminifikowanego zapisu w formacie JSON, aby polepszyć czytelność danych. | | 2 |
| | Zaimplementowanie algorytmu | 1 |
| | Weryfikacja poprawności danych wejściowych | 1 |
| Jako programista mogę uzyskać strukturę w formacie JSON zawierającą tylko określone własności, aby uprościć strukturę | | 3 |
| | Zaimplementowanie algorytmu | 2 |
| | Weryfikacja poprawności danych wejściowych | 1 |
| Jako programista mogę porównać dwa teksty (np.<br>reprezentujące strukturę danych w formatach JSON) oczekując, że aplikacja pokaże linijki, w których występuje różnica. | | 4 |
| | Model zwracanej struktury | 1 |
| | Zaimplementowanie algorytmu | 2 |
| | Weryfikacja poprawności danych wejściowych | 1 |
| Aplikacja znajduję się w systemie kontroli wersji GitHub | | 2 |
| | Utworzenie repozytorium na GitHubie oraz dodanie członków projektu | 1 |
| | Konfiguracja mechanizmu Ciągłej integracji Github actions | 1 |
| Aplikacja posiada diagram klas UML | | 1 |
| | Zaprojektowanie diagramu UML | 1 |
| Aplikacja posiada dokumentacje kodu | | 1 |
| | Dodanie generowania dokumentacji JavaDoc dla najważniejszych klas | 1 |
| Do prowadzenia projektu wykorzystywane jest narzędzie zarządzania | | 2 |
| | Utworzenie zadań w sekcji Git Hub issues| 1 |
| | Pilnowanie żeby lista zadań i ich stan były zgodne ze stanem rzeczywistym | 1 |
| Wykorzystanie narzędzia s4j do logowania i debugowania | | 1 |
| | konfiguracja biblioteki s4j | 1 |

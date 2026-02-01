# Opis projektu
Aplikacja służąca do sortowania zbiorów danych różnymi algorytmami. Ma pomóc użytkownikowi w ocenie, która metoda może się najlepiej sprawdzić w konkretnych przypadkach oraz, oczywiście, w uporządkowaniu danych. Dane liczbowe są sortowane standardowo, natomiast tekstowe - leksykograficznie. Należy zaimplementować co najmniej 6 różnych metod sortowania. Aplikacja będzie dostępna poprzez GUI, a także jako zdalne API, dzięki czemu można zintegrować z istniejącymi narzędziami.

**Definition of Done:** [Google Spreadsheets](https://docs.google.com/spreadsheets/d/e/2PACX-1vTn6j3M8pmGEzrsQk8mXse7lVHUdhYWkfxbkQiYI23rBtwM4N3bWw0qtupW-gesfCkcYasnZ-eEXl-F/pubhtml#gid=0)

# Przykład użycia Linux
```
curl --request POST \
  --url http://127.0.0.1:8080/api/sort/integers \
  --header 'content-type: application/json' \
  --data '{"algorithms": ["merge"],"data": [7,3,9,1,5]}'
```

```
curl --request POST \
  --url http://127.0.0.1:8080/api/sort/strings \
  --header 'content-type: application/json' \
  --data '{"algorithms": ["merge"],"data": ["test3","test","test2"]}'
```

# Przykład użycia Windows
Polecenie klienta:
```
curl -X POST http://localhost:8082/api/sort/integers -H "Content-Type: application/json" -d "{\"algorithms\":[\"merge\",\"bubble\"],\"data\":[7,3,9,1,5]}"
```
Odpowiedź serwera:
```
{"algorithmResults":[
{"algorithm":"Merge Sort","executionTimeNs":5800.0,"sortedData":[1,3,5,7,9]},
{"algorithm":"Bubble Sort","executionTimeNs":17700.0,"sortedData":[1,3,5,7,9]}
]}
```



# Skład grupy
 1. Jakub Broda - 160147
 2. Michał Bróździński - 160084
 3. Paweł Wichowski - 160211
 4. Dawid Pasler - 160212

# UML
<img width="1555" height="1419" alt="image" src="https://github.com/user-attachments/assets/bcfbd0ff-bb8f-4758-87fb-98b7932af4d1" />


## Cache’owanie wyników sortowania

### Cel
Celem cache’owania jest ograniczenie liczby ponownych obliczeń dla identycznych zapytań sortujących.  
Jeżeli użytkownik wysyła to samo zapytanie (te same dane wejściowe oraz te same algorytmy), wynik sortowania jest zwracany z cache zamiast być liczony ponownie.

Rozwiązanie poprawia:
- czas odpowiedzi API,
- zużycie CPU,
- wydajność aplikacji przy powtarzalnych zapytaniach.

### Zastosowana technologia
Cache został zaimplementowany przy użyciu:
- **H2 Database** – lekka, wbudowana baza danych w trybie plikowym,
- **Spring Data JPA** – warstwa dostępu do danych.

Rozwiązanie nie wymaga dodatkowej infrastruktury (np. Dockera ani zewnętrznego serwera cache).

### Zakres cache’owania
Cache’owana jest **cała odpowiedź API** (`SortResponse`) dla endpointów:
- `/api/sort/integers`
- `/api/sort/strings`

Każdy wpis cache odpowiada jednemu, konkretnemu zapytaniu sortującemu.

### Klucz cache
Klucz cache jest wyliczany deterministycznie na podstawie:
- typu sortowania (`integers` / `strings`),
- listy algorytmów sortowania,
- danych wejściowych.

Proces tworzenia klucza:
1. Normalizacja danych (np. nazwy algorytmów → lowercase).
2. Utworzenie kanonicznego obiektu JSON z danych zapytania.
3. Obliczenie skrótu SHA-256 z tego JSON-a.
4. Uzyskany hash stanowi unikalny `cache_key`.

Dzięki temu identyczne zapytania zawsze mapują się na ten sam wpis cache.

### Struktura danych cache
W bazie H2 przechowywana jest tabela `CACHE_ENTRY`, zawierająca:
- `cache_key` – unikalny klucz cache,
- `request_json` – zapytanie zapisane w formacie JSON,
- `response_json` – odpowiedź API w formacie JSON,
- `created_at` – czas utworzenia wpisu,
- `expires_at` – czas wygaśnięcia wpisu (TTL).

Na kolumnę `cache_key` nałożony jest **unikalny indeks**, zapobiegający duplikatom.

### Algorytm działania
Dla każdego zapytania sortującego:
1. Generowany jest klucz cache (`cache_key`).
2. Aplikacja sprawdza, czy wpis istnieje i nie jest przeterminowany.
3. **Cache HIT** – odpowiedź jest deserializowana z JSON i zwracana bez wykonywania sortowania.
4. **Cache MISS** – sortowanie jest wykonywane, wynik zapisywany w cache i zwracany do klienta.

### TTL (Time To Live)
Czas życia wpisów cache jest konfigurowalny w pliku `application.properties`:
- `cache.ttl-seconds=86400`

Po przekroczeniu czasu `expires_at` wpis cache jest traktowany jako nieważny.

### Informacja o użyciu cache
W odpowiedzi HTTP dodawany jest nagłówek:
- `X-Cache: HIT`
- `X-Cache: MISS`


Pozwala to jednoznacznie stwierdzić, czy wynik pochodził z cache, czy został obliczony na nowo.

### Uzasadnienie wyboru rozwiązania
Zastosowanie bazy H2 jako cache:
- upraszcza konfigurację projektu,
- umożliwia szybkie uruchomienie aplikacji lokalnie,
- jest wystarczające dla prostego cache’owania wyników obliczeń.

Architektura umożliwia w przyszłości zastąpienie H2 innym mechanizmem cache (np. Redis) bez zmian w warstwie kontrolerów.

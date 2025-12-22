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

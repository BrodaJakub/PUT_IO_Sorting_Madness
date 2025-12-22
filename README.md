# Opis projektu
Aplikacja służąca do sortowania zbiorów danych różnymi algorytmami. Ma pomóc użytkownikowi w ocenie, która metoda może się najlepiej sprawdzić w konkretnych przypadkach oraz, oczywiście, w uporządkowaniu danych. Dane liczbowe są sortowane standardowo, natomiast tekstowe - leksykograficznie. Należy zaimplementować co najmniej 6 różnych metod sortowania. Aplikacja będzie dostępna poprzez GUI, a także jako zdalne API, dzięki czemu można zintegrować z istniejącymi narzędziami.

**Definition of Done:** [Google Spreadsheets](https://docs.google.com/spreadsheets/d/e/2PACX-1vTn6j3M8pmGEzrsQk8mXse7lVHUdhYWkfxbkQiYI23rBtwM4N3bWw0qtupW-gesfCkcYasnZ-eEXl-F/pubhtml#gid=0)

# Przykład użycia
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

# Skład grupy
 1. Jakub Broda - 160147
 2. Michał Bróździński - 160084
 3. Paweł Wichowski - 160211
 4. Dawid Pasler - 160212

# UML
<img width="1184" height="664" alt="image" src="https://github.com/user-attachments/assets/232023b7-db14-4fe7-911e-cb1823c1e395" />

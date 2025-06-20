# Search Engine API (с поддержкой опечаток)
Это RESTful API на Spring Boot для поиска c поддержкой исправления опечаток, 
реализованной с помощью BK-дерева и расстояния Левенштейна. 
Наименования хранятся в базе данных MongoDB.

## Возможности
* Добавление товаров через `POST /products`
* Поиск с поддержкой неточных совпадений `GET /search?query=...&maxDistance=...`
* Хранение данных в MongoDB
* Тесты на JUnit + MockMvc
* CI через GitHub Actions

## Зависимости
* Java 23+
* MongoDB

## Установка и запуск
1. Скопируйте проект:
```bash
git clone https://github.com/lunyamq/search-engine.git
cd search_engine
```
2. Настройте `application.properties`:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/algo
```
3. Соберите и запустите проект:
```bash
./gradlew test
./gradlew bootJar
java -jar build/libs/search_engine-1.0.jar
```

## Алгоритмы
### Инвертированный индекс (Index)  
  Для быстрого поиска по словам в названиях товаров используется 
  инвертированный индекс — структура данных, которая сопоставляет каждому слову список объектов, 
  где оно встречается.
### Поиск с опечатками (BKTree)
  Для поддержки поиска с ошибками и опечатками реализовано BK-дерево (Burkhard-Keller Tree). 
  Это дерево хранит слова, а при поиске быстро находит все слова на заданном расстоянии 
  по алгоритму Левенштейна.
### Расстояние Левенштейна (Levenshtein)
  Это классический алгоритм вычисления минимального количества операций (вставка, удаление, замена) 
  для преобразования одного слова в другое.
  
## Примеры API
По умолчанию работает на http://localhost:8080
### Добавление товара
```http
POST /products
Content-Type: application/json

{
  "id": 998,
  "name": "Электрогитара",
  "price": 4599.5
}
```
### Поиск товара
```http
GET /search?query=электрагетара&maxDistance=2
```
Пример ответа:
```json
[
  {
    "correctedWord": "электрогитара",
    "productId": 998,
    "productName": "Электрогитара",
    "price": 4599.5
  }
]
```
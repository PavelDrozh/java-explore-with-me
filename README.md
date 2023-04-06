# java-explore-with-me
Template repository for ExploreWithMe project.

# Pull Request 
https://github.com/PavelDrozh/java-explore-with-me/pull/5

# Feature: Location Processing API
Я ввел понятие локатор для разделения Локации проведения события 
и обобщенного места в котором могут проходить события (назвал из Локаторами)

# Admin:

GET http://localhost:8080/admin/location?name="asdasd"&from=0&size=10
Получить все Локаторы можно ограничиться поиском по имени

GET http://localhost:8080/admin/location/{locatorId}
Получить Локатор по id c событиями в радиусе Локатора

GET http://localhost:8080/admin/location/locator?lat=10&lon=10
Получить Локатор по локации (находит Локатор, если по локации попасть в радиус который он охватывает) c событиями в радиусе Локатора

POST http://localhost:8080/admin/location
Создать Локатор

PATCH http://localhost:8080/admin/location/{locatorId}
Обновить Локатор

DELETE http://localhost:8080/admin/location/{locatorId}
Удалить Локатор

# Private

GET http://localhost:8080/users/{userId}/location?name="asdasd"&from=0&size=10
Получить все Локаторы можно ограничиться поиском по имени

GET http://localhost:8080/users/{userId}/location/{locatorId}
Получить Локатор по id c событиями Пользователя в радиусе Локатора

GET http://localhost:8080/users/{userId}/location/locator?lat=10&lon=10
Получить Локатор по локации (находит Локатор, если по локации попасть в радиус который он охватывает) 
c событиями Пользователя в радиусе Локатора

# Public

GET http://localhost:8080/location?name="asdasd"&from=0&size=10
Получить все Локаторы можно ограничиться поиском по имени

GET http://localhost:8080/location/{locatorId}
Получить Локатор по id c Опубликованными событиями в радиусе Локатора

GET http://localhost:8080/users/{userId}/location/locator?lat=10&lon=10
Получить Локатор по локации (находит Локатор, если по локации попасть в радиус который он охватывает)
c Опубликованными событиями в радиусе Локатора
# java-explore-with-me
Template repository for ExploreWithMe project.

# Pull Request 
https://github.com/PavelDrozh/java-explore-with-me/pull/5

# Feature: Location Processing API
Я ввел понятие EventArea для разделения Локации проведения события 
и обобщенного места в котором могут проходить события.

# Admin:

GET http://localhost:8080/admin/eventArea?name="asdasd"&from=0&size=10
Получить все EventArea можно ограничиться поиском по имени

GET http://localhost:8080/admin/eventArea/{eventAreaId}
Получить EventArea по id c событиями в радиусе EventArea

GET http://localhost:8080/admin/eventArea/location?pointLat=10&pointLon=10
Получить EventArea по локации (находит EventArea, если по локации попасть в радиус который он охватывает) 
c событиями в радиусе EventArea

POST http://localhost:8080/admin/eventArea
Создать EventArea

PATCH http://localhost:8080/admin/eventArea/{eventAreaId}
Обновить EventArea

DELETE http://localhost:8080/admin/eventArea/{eventAreaId}
Удалить EventArea

# Private

GET http://localhost:8080/users/{userId}/eventArea?name="asdasd"&from=0&size=10
Получить все EventArea можно ограничиться поиском по имени

GET http://localhost:8080/users/{userId}/eventArea/{eventAreaId}
Получить EventArea по id c событиями Пользователя в радиусе EventArea

GET http://localhost:8080/users/{userId}/eventArea/location?pointLat=10&pointLon=10
Получить EventArea по локации (находит EventArea, если по локации попасть в радиус который она охватывает) 
c событиями Пользователя в радиусе EventArea

# Public

GET http://localhost:8080/eventArea?name="asdasd"&from=0&size=10
Получить все EventArea можно ограничиться поиском по имени

GET http://localhost:8080/eventArea/{eventAreaId}
Получить EventArea по id c Опубликованными событиями в радиусе EventArea

GET http://localhost:8080/eventArea/location?pointLat=10&pointLon=10
Получить EventArea по локации (находит EventArea, если по локации попасть в радиус который он охватывает)
c Опубликованными событиями в радиусе EventArea
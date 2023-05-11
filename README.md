# java-filmorate

## ER-диаграма базы данных

![diagram] (https://github.com/alisilakg/java-filmorate/blob/add-database/src/main/resources/BD.png)

## Примеры запросов:

* ### Вывод списка фильмов:
SELECT *

FROM films;

* ### Поиск фильма с ID = 1:
SELECT *

FROM films

WHERE film_id = 1;

* ### Получение списка из 10 популярных фильмов:
SELECT *

FROM films AS f

LEFT JOIN likes AS l ON f.film_id = l.film_id

GROUP BY l.film_id

ORDER BY COUNT(l.film_id) DESC

LIMIT 10;

* ### Вывод списка пользователей:
SELECT *

FROM users;

* ### Поиск пользователя с ID = 1:
SELECT *

FROM users

WHERE user_id = 1;

* ### Получение списка всех друзей пользователя с ID = 1:
SELECT *

FROM friendship AS f

WHERE f.status_id = 2

LEFT JOIN users AS u ON f.friend_id = u.user_id;

> статус дружбы: 1 - неподтверждённая, 2 - подтверждённая

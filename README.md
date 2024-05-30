#Тестовое задание для РЦР «Екатеринбург»:
**RESTful веб-сервис 
С помощью ваших любимых технологий необходимо реализовать RESTful веб-сервис - онлайн-библиотека.
В онлайн-библиотеке имеется множество различных книг, каждая книга существует в одном экземпляре. 
Читатель может выполнить поиск книг в библиотеке и заказать до 3-х книг сроком до 14 дней.
 Читатель обязан вернуть книги в библиотеку, при этом если читатель вернул книгу по истечению 14 дней со дня заказа книги, то это считается нарушением правил пользования библиотеки. После того, как читатель 2 и более раза нарушил правила пользования библиотеки, он больше не может заказать книги в библиотеке.  
Пояснение:
Задание реализовано с использованием технологий фреймворка Spring (boot, data, web, security).
В качестве базы данных использована СУБД MySql.
Ограничение по существованию книг в одном экземпляре реализовано путем аннотирования класса «Book» @Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title", "author"})). Это означает, что в таблице не может быть двух записей с одинаковыми значениями в этих двух столбцах.
Возможности поиска и заказа определены в интерфейсах соответствующих сервисов и имплементированы в конкретные сервисные классы, в которых заложена логика ограничений.
При нарушении требования у объектов класса Reader изменяется счетчик нарушений, показатели которого учитываются при совершении операций заказа книг.
Объектная составляющая веб-сервиса: 
Book – модель книги.
Order -модель представляющая заказ.
User( Reader, Librarian) -пользователи системы. Читатель и библиотекарь.
Библиотекарь обладает возможностью создания новых пользователей, книг и просмотра общей статистики.
Читатель может осуществлять поиск интересующих книг.  Осуществлять заказ интересующих книг.
Возврат книги может осуществить библиотекарь.
Разграничение к соответствующим запросам реализовано с использованием технологии Spring security  и использованием JWT-токенов для аутентификации пользователей.
При первоначальном запуске создается системный пользователь(login: librarianAdmin, password: 123), которого можно использовать для первоначального наполнения БД.
В корне проекта находится файл Request.http, в котором приведены примеры запросов, которые последовательно демонстрируют возможности взаимодействия  с веб-сервисов ( создание пользователей, книг и соответствующих манипуляций с ними).
Порядок подготовки перед запуском сервиса:
1.	Осуществить копирование репозитория
2.	Создать базу данных в СУБД Mysql
3.	Внести изменения в файл application.properties указа свои параметры подключения:
spring.datasource.url=jdbc:mysql://localhost:3306/Название_вашей_БД?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Yekaterinburg

spring.datasource.username=указать_пользователя_вашей_БД

spring.datasource.password=указать_пароль_пользовтеля_вашей_БД
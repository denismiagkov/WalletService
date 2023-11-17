# Wallet-Service

Скрипты миграции, конфигурационные файлы, а также файл docker-compose.yml расположены в директории /resources.

*Чтобы развернуть базу данных в контейнере:*
1. Скопируйте проект на локальную машину;
2. Через проводник перейдите в директорию /resources ;
3. Откройте терминал из директории /resources ;
4. В терминале введите команду: sudo docker-compose up .
5. Подключитесь к базе данных автоматически из IDE путем запуска приложения, либо из tool (DBeaver, PgAdmin) 
с использованием параметров "URL", "USER_NAME", "USER_PASSWORD" в конфигурационном файле application.yml 


 *Для подключения к графическому интерфейсу SwaggerUI (SpringDoc):*
1. Запустите приложение;
2. Перейдите в браузере по ссылке http://localhost:8080/swagger-ui/index.html#/ 

### Техническое задание

**Задание 1**

*Wallet-Service*

Необходимо создать сервис, который управляет кредитными/дебетовыми транзакциями от имени игроков.

*Описание*

Денежный счет содержит текущий баланс игрока. Баланс можно изменить, зарегистрировав транзакции на счете, либо дебетовые транзакции (удаление средств), либо кредитные транзакции (добавление средств). Создайте реализацию, которая соответствует описанным ниже требованиям и ограничениям.

*Требования*

* данные хранятся в памяти приложений
* приложение должно быть консольным (никаих спрингов, взаимодействий с БД и тд, только java-core и collections)
* регистрация игрока
* авторизация игрока
* Текущий баланс игрока
* Дебет/снятие средств для каждого игрока Дебетовая транзакция будет успешной только в том случае, если на счету достаточно средств (баланс - сумма дебета >= 0). - Вызывающая сторона предоставит идентификатор транзакции, который должен быть уникальным для всех транзакций. Если идентификатор транзакции не уникален, операция должна завершиться ошибкой.
* Кредит на игрока. Вызывающая сторона предоставит идентификатор транзакции, который должен быть уникальным для всех транзакций. Если идентификатор транзакции не уникален, операция должна завершиться ошибкой.
* Просмотр истории пополнения/снятия средств игроком
* Аудит действий игрока (авторизация, завершение работы, пополнения, снятия и тд)

*Нефункциональные требования*

Unit-тестирование

**Задание 2**

Необходимо обновить сервис, который вы разработали в первом задании согласно следующим требованиям и ограничениям

*Требования:*

* Репозитории теперь должны писать ВСЕ сущности в БД PostgreSQL
* Идентификаторы при сохранении в БД должны выдаваться через sequence
* DDL-скрипты на создание таблиц и скрипты на предзаполнение таблиц должны выполняться только инструментом миграции Liquibase
* Скрипты миграции Luiqbase должны быть написаны только в нотации XML
* Скриптов миграции должно быть несколько. Как минимум один на создание всех таблиц, другой - не предзаполнение данными
* Служебные таблицы должны быть в отдельной схеме
* Таблицы сущностей хранить в схеме public запрещено
* В тестах необходимо использовать test-containers
* В приложении должен быть docker-compose.yml, в котором должны быть прописаны инструкции для развертывания postgre в докере. Логин, пароль и база должны быть отличными от тех, что прописаны в образе по-умолчанию. Приложение должно работать с БД, развернутой в докере с указанными параметрами.
* Приложение должно поддерживать конфиг-файлы. Всё, что относится к подключению БД, а также к миграциям, должно быть сконфигурировано через конфиг-файл

**Задание 3**

Необходимо обновить сервис, который вы разработали в первом задании согласно следующим требованиям и ограничениям

*Требования:*

* Все взаимодействие должно теперь осуществляться через отправку HTTP запросов
* Сервлеты должны принимать JSON и отдавать также JSON
* Использовать понятное именование эндпоинтов
* Возвращать разные статус-коды
* Добавить DTO (если ранее не было заложено)
* Для маппинга сущностей в дто использовать MapStruct
* Реализовать валидацию входящих ДТО
* Аудит переделать на аспекты
* Также реализовать на аспектах выполнение любого метода (с замером времени выполнения)
* Сервлеты должны быть покрыты тестами
* Метод логина должен выдавать JWT, остальные методы должны быть авторизационными и валидировать JWT

**Задание 4**

Необходимо обновить сервис, который вы разработали в первом задании согласно следующим требованиям и ограничениям.

*Требования:*

* Java-конфигурация приложения
* Кастомные конфигурационные файлы заменить на application.yml
* Удалить сервлеты, реализовать Rest-контроллеры (Spring MVC)
* Swagger + Swagger UI
* Аспекты переписать на Spring AOP
* Внедрение зависимостей ТОЛЬКО через конструктор
* Удалить всю логику создания сервисов, репозиториев и тд. Приложение должно полностью управляться спрингом
* Добавить тесты на контроллеры (WebMVC)

**Задание 5**


Необходимо обновить сервис, который вы разработали в первом задании согласно следующим требованиям и ограничениям

*Требования:*

* Spring Boot 3.2.0 с использованием необходимых стартеров
* Обновить тесты
* Аспекты аудита и логирования вынести в стартер, сделать отдельным модулем. Один стартер должен автоматически подключаться, второй через аннотацию @EnableXXX
* Swagger -> SpringDoc

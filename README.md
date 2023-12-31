## Практическая задача 1.1.4 Java pre-project. Практическая задача 1.1.2. Работа с базой данных c помощью JDBC.
### Описание задачи:

Необходимо ознакомиться с заготовкой и доработать приложение, взаимодействующее с базой данных и оперирующее пользователями (класс `User`), проверив его методы с использованием написанных заранее JUnit тестов. По завершении работы все тесты должны быть успешно пройдены. Разрешается ознакомиться с реализацией тестов.

Для запуска тестов необходимо найти соответствующий класс в папке `test` (как показано в предыдущей лекции) и, щелкнув правой кнопкой мыши, выбрать "Run 'Имя класса'".

Класс `UserHibernateDaoImpl` в рамках данной задачи не затрагивается и остается пустым.

#### User

`User` представляет из себя сущность с полями:

- Long id
- String name
- String lastName
- Byte age

Архитектура приложения создана с опорой на паттерн проектирования MVC (частично, поскольку это не WEB приложение).

Ознакомиться с паттерном можно [здесь](ссылка на паттерн).

#### Требования к классам приложения

- Классы dao/service должны реализовывать соответствующие интерфейсы.
- Класс dao должен иметь конструктор пустой/по умолчанию.
- Все поля должны быть private.
- `Service` переиспользует методы `dao`.
- Обработка всех исключений, связанных с работой с базой данных, должна находиться в `dao`.
- Класс `Util` должен содержать логику настройки соединения с базой данных.

#### Необходимые операции

1. Создание таблицы для `User` - не должно приводить к исключению, если такая таблица уже существует.
2. Удаление таблицы `User` - не должно приводить к исключению, если таблицы не существует.
3. Очистка содержания таблицы.
4. Добавление `User` в таблицу.
5. Удаление `User` из таблицы (по id).
6. Получение всех `User` из таблицы.

#### Алгоритм работы приложения

В методе `main` класса `Main` должны происходить следующие операции:

1. Создание таблицы `User`.
2. Добавление 4 `User` в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль (`User с именем – name добавлен в базу данных`).
3. Получение всех `User` из базы и вывод в консоль (должен быть переопределен метод `toString` в классе `User`).
4. Очистка таблицы `User`.
5. Удаление таблицы.

## Практическая задача 1.1.5 Java pre-project. Практическая задача 1.1.3. Работа с базой данных c помощью Hibernate.
### Описание задачи:

В прошлой задаче мы познакомились с Maven и JDBC, доработали приложение, взаимодействующее с базой данных.

На этот раз обратим внимание на класс `UserHibernateDaoImpl`, который реализует тот же интерефейс, что и `UserDaoJdbcImpl`.

В рамках этой задачи необходимо реализовать взаимодействие с базой данных с помощью Hibernate и дописать методы в классе `UserHibernateDaoImpl`, проверить свои методы заранее написанными JUnit тестами из заготовки.

### Требования к классам приложения:

* `UserHibernateDaoImpl` должен реализовывать интерефейс `UserDao`
* В класс `Util` должна быть добавлена конфигурация для Hibernate ( рядом с JDBC), без использования xml.
* Service на этот раз использует реализацию dao через Hibernate
* Методы создания и удаления таблицы пользователей в классе `UserHibernateDaoImpl` должны быть реализованы с помощью SQL.
 
Алгоритм приложения и операции не меняются в сравнении с предыдущим заданием.


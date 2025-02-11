```markdown
,--.         ,---.        ,--.  ,--.        ,--.
|  |,--,--, /  .-' ,---.  |  '--'  |,--.,--.|  |-.
|  ||      \|  `-,| .-. | |  .--.  ||  ||  || .-. '
|  ||  ||  ||  .-'' '-' ' |  |  |  |'  ''  '| `-' |
`--'`--''--'`--'   `---'  `--'  `--' `----'  `---'
```
# [Join the community on Discord](https://discord.gg/KnREfP4zz8)

---

# InfoHub

* ## [Перейти к русской версии](#русская-версия)
  * ### [Установка](#установка)
  * ### [Технологический стек](#технологический-стек)
  * ### [Возможности](#возможности)
  * ### [Использование](#использование)
  * ### [Вклад в проект](#вклад-в-проект)

* ## [Go to the English version](#english-version)
  * ### [Installation](#installation)
  * ### [Technology Stack](#technology-stack)
  * ### [Features](#features)
  * ### [Usage](#usage)
  * ### [Contributing](#contributing)


## Русская версия
___
**InfoHub** — это веб-платформа, где пользователи могут создавать, управлять и делиться своими статьями. Наша цель —
предоставить пространство для совместного написания статей, где каждый может публиковать свои знания, идеи или истории.
Платформа также включает систему рейтинга, которая помогает выделить качественный контент.
___
## Возможности

- **Создание статей**: Пользователи могут легко создавать и публиковать свои статьи.
- **Пользовательские профили**: У каждого пользователя есть настраиваемый профиль для отображения статей.
- **Теги и категории**: Статьи можно отмечать тегами и размещать в категориях для удобной навигации.
- **Взаимодействие с сообществом**: Пользователи могут оценивать, комментировать и делиться статьями.
- **Система рейтинга**: Статьи ранжируются по оценкам пользователей, чтобы выделить лучшие материалы.
- **OAuth2 вход через Google**: Безопасный вход и регистрация через Google OAuth2.
- **Адаптивный дизайн**: Платформа оптимизирована для работы как на компьютерах, так и на мобильных устройствах.
- **Поиск и фильтры**: Расширенный поиск позволяет находить статьи по ключевым словам, тегам и категориям.
___
## Технологический стек

- **Java**: 21
- **Spring Boot**: 3.3
- **Spring Security**: 6.3 (с OAuth2 через Google)
- **База данных**: PostgreSQL
- **Сборка**: Gradle Kotlin DSL
___

## Начало работы

### Необходимые компоненты

Для локального запуска проекта вам потребуется:

- Java 21
- Gradle 8.x (с Kotlin DSL)
- PostgreSQL
- Docker

### Установка

1. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/EchoMinds/InfoHub.git
   ```

2. Откройте терминал и перейдите в директорию проекта:

   ```bash
   cd InfoHub
   ```

3. Создайте файл `application.yaml` на основе файла `application.yaml.original`, добавив свои данные
   для подключения Google Oauth2:

   ```yaml
   spring:
     security:
       oauth2:
         client:
           registration:
             google:
               client-id: ваш_client_id
               client-secret: ваш_client_secret
   ```

4. Запустите докер на вашем компьютере и соберите проект с помощью Gradle:

```bash
./gradlew clean build
```

5. С помощью докера создайте контейнеры, для этого впишите в терминал:

   ```bash
   docker-compose up
   ```

---

## Использование

После запуска приложения откройте следующий адрес:

```
http://localhost:8080/swagger-ui/index.html#/
```

Вы увидите документацию к Api созданную автоматически, благодаря технологии OpenApi3.
___

## Вклад в проект

Мы рады любому вкладу!
Если вы хотите внести вклад в проект, пожалуйста, прочтите [CONTRIBUTING.md](CONTRIBUTING.md).
Делайте форк репозитория, создавайте новую ветку и отправляйте pull request. Убедитесь, что ваш код соответствует нашим
стандартам.
___

## Лицензия

Проект лицензирован Apache лицензией.
___
## English Version

**InfoHub** is a web platform where users can create, manage, and share their articles. Our goal is to provide a space
for collaborative article writing, where everyone can publish their knowledge, ideas, or stories. The platform also
includes a rating system that helps highlight quality content.
___

## Features

- **Article creation**: Users can easily create and publish their articles.
- **User profiles**: Each user has a customizable profile to showcase their articles.
- **Tags and categories**: Articles can be tagged and placed into categories for easy navigation.
- **Community interaction**: Users can rate, comment, and share articles.
- **Rating system**: Articles are ranked based on user ratings to highlight the best content.
- **OAuth2 login via Google**: Secure login and registration through Google OAuth2.
- **Responsive design**: The platform is optimized for both desktop and mobile devices.
- **Search and filters**: Advanced search allows you to find articles by keywords, tags, and categories.
___

## Technology Stack

- **Java**: 21
- **Spring Boot**: 3.3
- **Spring Security**: 6.3 (with OAuth2 via Google)
- **Database**: PostgreSQL
- **Build**: Gradle Kotlin DSL
___

## Getting Started

### Requirements

To run the project locally, you will need:

- Java 21
- Gradle 8.x (with Kotlin DSL)
- PostgreSQL
- Docker

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/EchoMinds/InfoHub.git
   ```

2. Open the terminal and navigate to the project directory:

   ```bash
   cd InfoHub
   ```

3. Create an `application.yaml` file based on `application.yaml.original`, adding your Google OAuth2 credentials:

   ```yaml
   spring:
     security:
       oauth2:
         client:
           registration:
             google:
               client-id: your_client_id
               client-secret: your_client_secret
   ```

4. Start Docker on your machine and build the project using Gradle:

   ```bash
   ./gradlew clean build
   ```

5. Use Docker to create the containers by running the following in the terminal:

   ```bash
   docker-compose up
   ```

---

## Usage

After the application is running, open the following address:

```
http://localhost:8080/swagger-ui/index.html#/
```

You will see the API documentation, automatically generated with OpenApi3.
___

## Contributing

We welcome any contributions!  
If you wish to contribute to the project, please read [CONTRIBUTING.md](CONTRIBUTING.md).  
Fork the repository, create a new branch, and submit a pull request. Make sure your code follows our standards.
___

## License

The project is licensed under the Apache License.


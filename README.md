# TextChatApp

**TextChatApp** – это веб-приложение для общения, предназначенное для текстовых чатов и управления сообществами.

## Описание проекта

TextChatApp позволяет пользователям:

- Регистрироваться и авторизоваться в системе
- Общаться в личных и групповых чатах
- Создавать серверы и комнаты для общения
- Настраивать аккаунт и управлять приватностью
- Управлять участниками серверов и предоставлять административные права

## Используемый стек технологий

- **Backend**: Spring Boot, Hibernate, MySQL
- **Frontend**: Vue.js, TypeScript, Pinia, Tailwind
- **Тестирование**:
- **CI/CD**: GitHub Actions

## Структура проекта

Проект разделен на два основных модуля:

1. **Backend** - серверная часть, предоставляющая REST API для работы с данными пользователей, чатами и серверной логикой.
2. **Frontend** - клиентская часть, реализующая пользовательский интерфейс.

## Запуск проекта

### 1. Клонирование репозитория

Клонируйте репозиторий с GitHub:

```
git clone https://github.com/YOUR_ORGANIZATION/TextChatApp.git
cd TextChatApp
```

## 2. Настройка Backend

Перейдите в папку backend:

```
cd backend
```

Настройте файл `application.properties` в каталоге `src/main/resources`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/textchatapp
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

Запустите серверную часть:

```
mvn spring-boot:run
```

## 3. Настройка Frontend

Перейдите в папку frontend:

```
cd frontend
```

Установите зависимости:

```
npm install
```

Запустите сервер разработки:

```
npm run serve
```

## 4. Тестирование

Backend-тесты

В папке `backend` выполните команду:

```
mvn test
```

Frontend-тесты

В папке `frontend` выполните команду:

```
npm run test
```

## 5. CI/CD

## Документация
[Руководство пользователя](https://github.com/TextChatApp/TextChatApp/blob/main/docs/user_guide.pdf) – см. docs/user_guide, включает инструкции по работе с интерфейсом. <br/>
[Презентация проекта](https://github.com/TextChatApp/TextChatApp/blob/main/docs/presentation.pdf) – доступна в docs/presentation.

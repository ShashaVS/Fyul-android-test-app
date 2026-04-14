# FYUL Test Task - Product List App

A simple Android application that fetches and displays a list of products from the [DummyJSON API](https://dummyjson.com/products).

## Tech Stack & Architecture

The project is built using following Android development practices:

- **Clean Architecture**: Divided into app, domain, data modules.
- **UI Framework**: Jetpack Compose for a declarative UI.
- **Dependency Injection**: Hilt for dependency management.
- **Networking**: Retrofit & OkHttp for API calls.
- **JSON Parsing**: Kotlinx Serialization.
- **Image Loading**: Coil for Compose.
- **Asynchronous Work**: Kotlin Coroutines and Flow.
- **Testing**: JUnit 4, MockK, and MockWebServer for unit tests.

## Project Structure

```
├── app             # UI layer (Jetpack Compose, ViewModels, Hilt)
├── data            # Data layer (Retrofit API, DTOs, Repository implementations)
└── domain          # Business logic (Models, Use Cases, Repository interfaces)
```

## Getting Started

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync Gradle.
4. Run the `app` configuration on an emulator or physical device (Min SDK 24).

## Features

- **Product List**: Displays a list of products fetched from the API.
- **Error Handling**: Basic handling for network issues or API errors.
- **Loading State**: Visual feedback while data is being fetched.
- **Responsive UI**: Built with Compose for a smooth user experience.
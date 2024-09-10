# PokeDex Android App
[rec2.webm](https://github.com/user-attachments/assets/5a6ea29d-ba43-4362-b390-7e432faffc20)

<img src="https://github.com/user-attachments/assets/e34758a4-6bdb-456d-a608-6231489e9f2d" width="300" alt="Screenshot_20240909_031850">
<img src="https://github.com/user-attachments/assets/1fbc9c79-05bc-40b0-b20e-bccd2309f235" width="300" alt="Screenshot_20240909_031909">
<img src="https://github.com/user-attachments/assets/3e9576f9-2b31-4c3e-8568-5976b1129f2a" width="300" alt="Screenshot_20240909_031920">


This is a toy project implementing a PokeDex using Android Jetpack Compose. The app follows Clean Architecture principles with MVVM (Model-View-ViewModel) pattern.

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                           Presentation Layer                    │
│  ┌─────────────────┐    ┌──────────────┐    ┌────────────────┐  │
│  │     Compose     │◄───│   ViewModel  │◄───│    UseCase     │  │
│  │    UI Components│    │              │    │                │  │
│  └─────────────────┘    └──────────────┘    └────────────────┘  │
│                                                   ▲             │
└───────────────────────────────────────────────────┼─────────────┘
                                                    │
┌───────────────────────────────────────────────────┼─────────────┐
│                            Domain Layer           │             │
│  ┌──────────────────┐    ┌───────────────────┐    │             │
│  │   Domain model   │    │    Repositories   │◄───┘             │
│  │                  │    │    (Interfaces)   │                  │
│  └──────────────────┘    └───────────────────┘                  │
│                                   ▲                             │
└───────────────────────────────────┼─────────────────────────────┘
                                    │
┌───────────────────────────────────┼─────────────────────────────┐
│                            Data Layer                           │
│  ┌──────────────────┐    ┌───────────────────┐                  │
│  │  Remote Data     │    │   Repositories    │                  │
│  │  Source (API)    │───►│  (Implementations)│                  │
│  └──────────────────┘    └───────────────────┘                  │
└─────────────────────────────────────────────────────────────────┘
```

## Tech Stack

- **Jetpack Compose**: Modern Android UI toolkit
- **Kotlin**: Programming language
- **Coroutines**: For asynchronous programming
- **Flow**: For reactive programming
- **Hilt**: Dependency injection
- **Retrofit**: HTTP client for API calls
- **Gson**: JSON parsing
- **Coil**: Image loading library
- **Android Architecture Components**: ViewModel

## Project Structure

The project is divided into three main layers:

1. **Presentation Layer**: Contains UI components (Composables) and ViewModels.
2. **Domain Layer**: Contains business logic, use cases, and repository interfaces.
3. **Data Layer**: Implements the repository interfaces and manages data sources.

## Features

- Display a list of Pokémon
- Show detailed information about each Pokémon
- Filter Pokémon list
- Fancy animations

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Build and run the app on an emulator or physical device

## Design resources
Figma: https://www.figma.com/design/3UF026k8MyMRpTeLMOv2CF/Pok%C3%A9dex-(Community)?node-id=314-3&node-type=CANVAS&t=MzsO5qUrLmBzG06H-0

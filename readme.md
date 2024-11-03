# PokeDex Android App
[Screen_recording_20241104_033756.webm](https://github.com/user-attachments/assets/98fd1db4-04fd-4e79-bdea-c790289dd78a)

<img src="https://github.com/user-attachments/assets/22a510f0-7e90-4d54-8cbe-de49070d1ae6" width="300">
<img src="https://github.com/user-attachments/assets/31897a7e-1f8a-4da9-a71e-dec7f700dc2e" width="300">
<img src="https://github.com/user-attachments/assets/2df0a208-3ba1-4509-9fbf-0d18bac7624a" width="300">

This is a toy project implementing a PokeDex using Android Jetpack Compose. The app follows Clean Architecture principles with MVVM (Model-View-ViewModel) pattern.

## Architecture Overview
```
┌─────────────────────────────────────────────────────────────────┐
│                           Presentation Layer                    │
│  ┌─────────────────┐    ┌──────────────┐                        │
│  │     Compose     │◄───│   ViewModel  │───────┐                │
│  │    UI Components│    │              │       │                │
│  └─────────────────┘    └──────────────┘       │                │
│                                                │                │
└────────────────────────────────────────────────│────────────────┘
                                                 │
                                                 │
                                                 │
┌────────────────────────────────────────────────│────────────────┐
│                            Domain Layer        │                │
│  ┌──────────────────┐    ┌───────────────┐    ┌▼─────────────┐  │
│  │   Domain model   │    │  Repositories │    │    UseCase   │  │
│  │                  │◄───│  (Interfaces) │◄───│              │  │
│  └──────────────────┘    └───────────────┘    └──────────────┘  │
│                                 ▲                               │
└─────────────────────────────────│───────────────────────────────┘
                                  │
                                  │
┌─────────────────────────────────│───────────────────────────────┐
│                        Data Layer│                              │
│  ┌──────────────┐    ┌───────────┴───────┐                      │
│  │  Remote Data │    │   Repositories    │                      │
│  │  Source (API)│◄───│  (Implementations)│                      │
│  └──────────────┘    └───────────────────┘                      │
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

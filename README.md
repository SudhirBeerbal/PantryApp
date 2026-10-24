# PantryApp

Mobile App Development 700 - Practical Assignment

## App Description
PantryApp is an Android application that helps users manage a kitchen pantry and see which recipes they can make with the ingredients they currently have on hand.

## Features
- **Pantry management**: add, edit, and delete pantry items (ingredient name, quantity, unit, optional expiry date)
- **Pantry list screen**: RecyclerView displaying all current ingredients from the database
- **Recipe collection**: 20 pre-loaded/seeded recipes stored in the local database on first run
- **Suggested Recipes screen**: strict-matching logic lists only recipes the user can make right now
- **Recipe detail screen**: full ingredient list and preparation method for a selected recipe
- **Settings screen**: toggles for expiring-soon alerts and units preference
- **Feedback when no recipes match**: "No recipes match your pantry yet - add more ingredients"

## Database
SQLite using the Room persistence library, implemented locally on-device. Data persists after the app is closed and reopened (full CRUD).

## Setup / Run Instructions
1. Install Android Studio (see below).
2. Open Android Studio and choose **Open an existing Android Studio project**.
3. Select the `PantryApp` folder.
4. Wait for Gradle sync to finish.
5. Connect an Android device or start an emulator.
6. Click **Run** (green play button) to install and launch the app.

## Testing the Strict Matching
Add these pantry items to see recipes appear on the Suggested Recipes screen:
- Ham and Cheese Sandwich: `bread`, `ham`, `cheese`
- Pancakes: `flour`, `eggs`, `milk`, `sugar`
- Avocado Toast: `bread`, `avocado`, `lemon`, `salt`

Ingredient names must match exactly (case-insensitive) for a recipe to be suggested.

## Requirements
- minSdk 21
- targetSdk 34
- Java 8
- Android Gradle Plugin 8.1.0

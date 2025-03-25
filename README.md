# COMP-2430 Mobile Computing Project

## Project Overview

This repository contains the project developed for the **COMP-2430 Mobile Computing Technologies** course. The project evolved through three phases:

1. **Business App**: Initially, we created a business-focused application that showcased company information and provided basic functionality.
2. **Business Card App with Sensor Info**: The business app was then transformed into a digital business card that not only displayed contact information but also leveraged device sensors to show real-time data.
3. **Sensor-Controlled Dice Roller App**: In the final phase, the app was converted into a dice roller application where sensor input (such as light sensor data) controlled the dice roll.

## Features

### Phase 1: Business App

- Displays business information (name, contact, and services).
- User-friendly interface with a responsive design.

### Phase 2: Business Card App with Sensor Info

- Displays a digital business card with personal and professional details.
- Integrates Android sensor APIs to capture and display live sensor data (e.g., accelerometer readings).

### Phase 3: Sensor-Controlled Dice Roller App

- Simulates a dice roll using light sensor input.
- Dice value corresponds to **1 + (integer part of the current light level % 6)**.
- Provides animated dice visualization triggered by changes in light levels.
- Implements custom user-defined variable and function names to avoid errors.

## Technologies Used

- **Android Studio**: Primary development environment.
- **Kotlin**: Main programming language using Jetpack Compose for UI.
- **Android Sensor API**: Used for integrating accelerometer and light sensor data.
- **BackupManager**: Configured to back up and restore user data.

## Installation and Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/forkyouabhi/BussinessCardApp.git
   cd BussinessCardApp
   ```

2. Open the project in **Android Studio**.

3. Build and run the application on an emulator or a physical device.

## How to Use the App

- **Business App**: Navigate through the business information via the main screen.
- **Business Card App**: View your digital business card and real-time sensor data.
- **Dice Roller App**: The dice roll is controlled by light sensor input. As the light level changes, the dice updates based on the formula **1 + (integer part of light level % 6)**.

## Testing Backup Functionality

- Ensure Android's **Backup & Restore** service is enabled on the device.
- Perform a backup using `adb`:
  ```bash
  adb shell bmgr backupnow your.package.name
  ```
- Restore data using:
  ```bash
  adb shell bmgr restore your.package.name
  ```

## Submission Requirements

Ensure the following deliverables are provided for successful submission:

1. **PDF Report**: Include textual explanations, screenshots of modified code segments, and references to line numbers.
2. **Screen Recording**: A 10-20 second video demonstrating the app running on an Android device or emulator.
3. **Presentation Video**: Provide a verbal explanation of the code modifications and sensor integration.
4. **Kotlin Code**: Submit your modified Kotlin code in a `.kt.txt` format.
5. **Project Files**: Ensure the full project is included for reproducibility.

If videos are too large for D2L, upload them to Google Drive and include accessible links in your report. Ensure all links are functional and files can be played in standard browsers.

## Evaluation Policy

- **"All or Nothing"**: Error-free code and fully functional app earn 100% of the points.
- **Minor Errors**: 5% deduction for each minor error (e.g., reusing original variable names).
- **Major Errors**: 15% deduction for each major error (e.g., non-functional dice roll or broken app).

## Project Structure

```
BussinessCardApp/
├── app/
│   ├── src/
│   │    ├── main/
│   │    │    ├── java/com/example/businessapp/
│   │    │    └── res/
│   └── AndroidManifest.xml
└── README.md
```

## Authors

- Your Name
- COMP-2430 Mobile Computing Technologies

## License

This project is licensed under the [MIT License](LICENSE).


# WhatToDo

**WhatToDo** is a multi-functional productivity app designed to simplify daily life by integrating task management, reminders, academic calculators (cgpa calculator, attendance calculator), and time tracking (stopwatch & timer). Built using modern technologies, it ensures seamless usability and secure data management.

---

## Features

- **User Authentication**: Secure login and signup with Google integration.
- **ToDo List**: Organize tasks effectively by adding, updating, and marking tasks as complete.
- **Reminders**: Set and manage alarms for tasks, calls, or SMS with database-backed storage.
- **Notes**: Create and manage colorful notes with secure storage and quick sorting options.
- **Stopwatch & Timer**: Tools for tracking and managing time efficiently.
- **Attendance Tracker**: Record, calculate, and monitor attendance with customizable goals.
- **CGPA & GPA Calculators**: Academic tools to calculate and track grade performance.
<img src="screenshots/30.png" alt="Key Features" height="200" width="300"/>
---

## Screenshots and Overview:

### Splash and Login-SignUp Screen
<img src="screenshots/1.jpg" alt="Splash Screenshot" height="300" width="150"/><img src="screenshots/2.jpg" alt="login-signup Screenshot" height="300" width="150"/><img src="screenshots/4.jpg" alt="SignUp Screenshot" height="300" width="150"/><img src="screenshots/1.jpg" alt="Login Screenshot" height="300" width="150"/><img src="screenshots/5.jpg" alt="Login using google Screenshot" height="300" width="150"/>
Screen appears while entering the app. Allows new users to create an account with email, username, and secure password features. Enables returning users to access their accounts securely.

### Home Screen
<img src="screenshots/6.jpg" alt="Home Screen Screenshot" height="300" width="150"/>
The central hub to navigate through tasks, notes, reminders, stop watch & timer, cgpa calculator, attendance calculator.

### Reminders Screen
<img src="screenshots/7.jpg" alt="Reminders Screenshot" height="300" width="150"/><img src="screenshots/8.jpg" alt="Sort Reminders Screenshot" height="300" width="150"/><img src="screenshots/9.jpg" alt="Add Reminders Screenshot" height="300" width="150"/><img src="screenshots/10.jpg" alt="Modify Reminders Screenshot" height="300" width="150"/>
Displays upcoming reminders with options for easy management. Users can add remiders ot todo list, update or delete that, and also can sort them by date and time.

### Notes Screen
<img src="screenshots/11.jpg" alt="Notes Screenshot" height="300" width="150"/><img src="screenshots/12.jpg" alt="Add notes Screenshot" height="300" width="150"/><img src="screenshots/13.jpg" alt="Update Notes Screenshot" height="300" width="150"/>
Organize notes with options to categorize, sort, and secure data, updates and deletes them.

### CGPA Calculator Screen
<img src="screenshots/21.jpg" alt="CGPA Calculator Screenshot" height="300" width="150"/><img src="screenshots/1.jpg" alt="All CGPA Screenshot" height="300" width="150"/>
Input grades and credits to calculate cumulative performance effortlessly and save previous semester data.

### Attendance Tracker Screen
<img src="screenshots/18.png" alt="Attendance Tracker Screenshot" height="300" width="150"/><img src="screenshots/19.jpg" alt="Attendance Calculate Screenshot" height="300" width="150"/><img src="screenshots/20.png" alt="Percentage Screenshot" height="300" width="150"/>
Set goals and monitor attendance percentages effectively.

### Timer and Stopwatch Screen
<img src="screenshots/14.png" alt="Stopwatch setup Screenshot" height="300" width="150"/><img src="screenshots/15.jpg" alt="Stopwatch Screenshot" height="300" width="150"/><img src="screenshots/16.png" alt="Stopwatch end Screenshot" height="300" width="150"/><img src="screenshots/17.jpg" alt="SignUp Screenshot" height="300" width="150"/>
Manage time with precise countdowns and tracking.

### App Other Options and Navbar
<img src="screenshots/23.jpg" alt="Navbar Screenshot" height="300" width="150"/><img src="screenshots/24.jpg" alt="Share Screenshot" height="300" width="150"/><img src="screenshots/25.png" alt="Privacy Screenshot" height="300" width="150"/><img src="screenshots/26.png" alt="Contact Screenshot" height="300" width="150"/>
<img src="screenshots/27.png" alt="Account Screenshot" height="300" width="150"/><img src="screenshots/28.png" alt="Help Screenshot" height="300" width="150"/><img src="screenshots/29.png" alt="About Screenshot" height="300" width="150"/>
App includes other features in navbar

---

## How App Works

### **Sign-Up**
1. User enters their email and password or signs up with Google.
2. Authentication secures account creation and data personalization.

### **Log-In**
1. User logs in with their credentials or Google account.
2. Personalized tasks, notes, and reminders are retrieved.

### **Task Management**
1. Create tasks with deadlines.
2. Mark tasks as complete or delete them when unnecessary.

### **Reminder Setup**
1. Set time-specific alerts for important tasks or events.
2. Notifications are triggered based on user settings.

### **Academic Calculators**
1. Input grades and credits for CGPA or GPA calculations.
2. Results are stored for future reference and analysis.

---

## UI Testing
The app has been rigorously tested to ensure a smooth user experience:
1. All major screens have been tested for responsiveness and design accuracy.
2. Interactive elements such as buttons, forms, and toggles have been validated for functionality.
3. Dark mode and accessibility features were verified for compatibility.

---

## Unit Testing
To ensure the app's reliability and accuracy:
1. Core functionalities such as reminders, calculators, and authentication have been unit tested.
2. Mock data and Firebase emulators were used to simulate real-world scenarios.
3. Edge cases and error-handling mechanisms were validated to maintain robustness.

---

## Design Patterns
The application employs modern design patterns to ensure scalability and maintainability:
- **Singleton**: For managing shared resources such as authentication and settings.
- **Factory**: For creating instances of calculators and reminders.
- **Strategy**: Implements various reminder types.
- **Flyweight**: Efficiently manages note storage and access.
- **MVC**: Organizes modules like the attendance tracker.

---

## Prerequisites

1. **Firebase Integration**: Configure Firebase Authentication and Firestore.
2. **Android Setup**: Latest Android Studio and JDK.
3. **Gradle Dependencies**: All required dependencies are managed through Gradle.
4. **Device Compatibility**: Android 5.0 or higher.

---

## Dependencies

- **Firebase**: Authentication, Firestore database.
- **Kotlin DSL**: Modern build script management.
- **Material Design**: UI components for a sleek and modern app.
- **Gradle**: Dependency management.

---

## License

This project is licensed under the MIT License. For details, see the `LICENSE` file.

---

Collaborated by:
- **Asif Akbar (2007106)**
- **Shaeer Musarrat Swapnil (2007116)**
- **Shayka Islam Shipra (2007110)**

# SOEN6011 Summer 2024 Project - Exponential Function Calculator

**Version: 1.0.0**

## Overview

The **Exponential Function Calculator** is a Java-based desktop application designed to compute exponential functions given values for a, b, and x. It provides a user-friendly interface for entering various mathematical expressions, including constants like **pi** and **e**, and functions such as square roots and roots of any degree. This project adheres to JavaFX standards for creating a responsive and accessible GUI.

## Features

- **Mathematical Constants**: Supports constants like **pi** and **e**.
- **Fractional Input**: Parse and calculate expressions involving fractions.
- **Square Roots and Roots**: Compute square roots and **n-th** roots of numbers.
- **Error Handling**: Robust handling of input errors with descriptive messages.
- **Semantic Versioning**: Version management using Semantic Versioning **(1.0.0)**.
- **Accessible GUI**: Designed for accessibility with Java Accessibility API.
- **JUnit Testing**: Comprehensive unit tests with JUnit to ensure correctness.
- **Styling and Code Quality**: Utilizes **PMD** for static code analysis and adherence to coding standards.
- **Maven Build System**: Utilizes Maven for project management and building.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Apache Maven (for building the project)
- JavaFX: Used for the graphical user interface
- JUnit 5: Used for unit testing.

## Installation

To install and run the application, follow these steps:

1. **Clone the Repository**
   ```bash
   git clone https://github.com/SanjayBhargavP/SOEN6011.git
   cd exponential-function

## Build the Project

   ```bash
   mvn clean package
   ````

## Run the Application
Execute the application using the following command:

   ```bash
   java --module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml -jar target/exponential-function-1.0.0.jar
   ```   
## Usage
Enter Values: Input real numbers, fractions, or expressions in the fields for 𝑎, 𝑏 and 𝑥.
Calculate: Click "Calculate Exponential" to compute the result.
Help: Use the "Help" button for guidance on input formats.

## Project Structure

- **src/main/java**: Contains the main application code.
   - **ExponentialFunctionApp.java**: Main class with the GUI logic.

- **src/test/java**: Contains JUnit test cases.
   - **ExponentialFunctionAppTest.java**: Unit tests for the application.

- **pom.xml**: Maven project configuration file.
# Hospital Booking app
## Project Overview

This project consists of 4 labs that I have been working on throughout the semester. The aim of this project is to develop a secure and robust healthcare management application. 
The app includes functionality for user authentication and validation, managing patient and doctor records, and scheduling appointments between the two parties.
Key features such as SHA-256 password hashing and salting is implemented to ensure data integrity and confidentiality, as well as Advanced Encryption Standard (AES) for storing sensitive patient details.

## Features

1. User Authentication
+ Secure login and registration system.
+ User credentials stored securely using SHA-256 password hashing in the SQLite database.

2. Manage Patients
+ Create, update, view, and delete patient records.
+ Store patient details such as first name, last name, age, and gender to the database.
+ AES encryption is used to store sensitive information such as gender.

3. Manage Doctors
+ Create, update, view, and delete doctor records.
+ Store doctor details such as first name, last name, and specialty (General practitioner, Paediatrician, etc).

4. Appointment Management
+ Schedule appointments between doctors and patient.
+ View and manage appointment details including date, time, and reason for booking (Checkup, surgery, etc).
+ AES encryption is used to encrypt sensitive data such as appointment reasons.
+ Doctor availability, users cannot book an appointment with a doctor within 15 minutes of an existing appointment.

## Lab Development

This project was developed iteratively across 4 labs, with a focus on security and modular design.

### Lab 1 (User Stories and Epics)

Lab 1 focuses on understanding the functional and non-functional requirements of the app through user stories and evil user stories.
The aim was to ensure that the app met the needs of its users including the patients, doctors, and admins, as well as addressing potential misuse cases in the form of evil user stories.
User stories were developed to outline potential scenarios within the app such as appointment booking and record updating, meanwhile evil user stories anticipated security vulnerabilities such as password brute forcing and SQL injections.

### Lab 3 (User Authentication)

Lab 3 focuses on user authentication, An SQLite database was created to store user details. By the end of lab 3 the database schema only included tables for the users, with the implementation of doctor, patient, and appointment tables kept in mind for the later labs.
Login and registration screens were developed, enabling user authentication. To ensure application security, SHA-256 password hashing was implemented, and input validation to protect against potential SQL attacks.

### Lab 4 (Patient Management)

Lab 4 introduced the functionalities for managing patient records in the application. This enabled users to create, view, update and delete patient data while maintaining database integrity.
The patient table was introduced to the database schema which consisted of patient ID, patient name, and patient gender. Sensitive information such as gender was encrypted to the database using AES, and decrypted using 'get' methods in the DBHelper.
Toast messages were implemented to provide real-time feedback for successful or failed operations. Dynamic testing was implemented to test the CRUD functionalities on my personal android device, covering edge cases such as empty inputs.

### Lab 5 (Doctor Management and Booking Management)

Lab 5 further builds on CRUD functionalities but for the doctor database table, as well as the implementation of a booking system between doctor and patient. This lab allows users to manage doctor records and link them with existing patients in the database through secure appointment scheduling.
Users are able to create, view, update and delete doctor data. 

The appointment management system was also implemented in lab 5, which bridged the functionality of patient and doctor records. This subsystem enables users to create, view, update, and delete appointments while ensuring sensitive data such as appointment reasons are encrypted for security.
Users are able to select doctors and patients through spinners. These spinners are populated from the database tables for patients and doctors.


## Installation

1. Clone the github repository
+ https://github.com/SloshyOsmosis/HospitalBookingLabs

2. Open the project in Android Studio (Android Studio Ladybug 2024.2.1)

3. Run project on a physical android device or emulator.

## Security Features

1. Encryption:
+ Advanced Encryption Standard applied to sensitive data fields, ensuring confidentiality of appointment details.

2. Password Hashing:
+ SHA-256 used for passwords.
+ Hashed passwords are salted.

3. Data Validation:
+ Input validation is implemented to ensure data integrity and to prevent SQL injection.
+ Email validation and password validation (Password must be at least 8 characters long, at least one uppercase letter, one number, and one special character.)

## Future Enhancements

+ If I were to undertake this project again, I would introduced role-based access control. Where doctors are able to login and have access to all of the application features, whereas patient logins are only able to make doctor bookings.
+ In the future I would also implement a notification system to remind patients or doctors about upcoming appointments. 
+ Enhanced security measure such as multi factor authentication.
+ Implementation of digital signatures for adding and removing patient, doctor, and appointment details.

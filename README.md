![](https://github.com/safeer2978/Sanvaad/blob/main/Design/Logo/Logo.jpg)
# _Sanvaad_ - Communicate Easy with Friends and Family

![](https://github.com/safeer2978/Sanvaad/blob/main/images/logo.png)

[![MIT license](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/safeer2978/Sunno-backend/blob/master/LICENSE)

Sanvaad is an android application for assisting communication for persons with hearing disability. The app is aimed to allow the user to interact with people and allow other parties to communicate naturally with the user, eliminating the need of sign language.

Sanvaad utilises Google Cloud Speech API and would require a subscription for use. This application is insipred and built around [this project](https://github.com/google/live-transcribe-speech-engine).

A Detailed Project report can be [found here](https://github.com/safeer2978/Sanvaad/blob/main/images/sanvaad%20project%20report.pdf)

## Motivation

Communicating with a person who has hearing, or speech disability can be very cumbersome. It requires both parties to use sign language as a medium. Historically this has worked great but has several flaws, mainly as it assumes the sender and receiver both know the sign language. This makes it necessary for friends of the person to know the language, makes it harder for these individuals to socialize, leaving them completely out of group discussions etc.

In 2020, with almost everyone having a smartphone, this problem can be solved easily through an app. The person can use the app to listen to conversations they are part of, thus not be left out and without everyone learning and speaking in sign language. The app would also feature a text to speech to allow persons with speech disability to express themselves.
Besides persons-of-disabilities, this app can also be used for solving other communication barriers such as accents, which can be helpful to international students, tourists, etc.

## Features:

 - Simple and Intuitave UI resembling a Chat application.
 - Transcribe Speech to text in a message bubble.
 - Assign Contact to message bubble
 - Use Text to Speech with custom message
 - create multiple chats.
 - add contacts
 - add and send common messages 

## Screenshots
![](https://github.com/safeer2978/Sanvaad/blob/main/images/1.jpg)
![](https://github.com/safeer2978/Sanvaad/blob/main/images/2.jpg)
![](https://github.com/safeer2978/Sanvaad/blob/main/images/3.jpg)
![](https://github.com/safeer2978/Sanvaad/blob/main/images/4.jpg)

## Scope/limitations/future work:
The application is limited to the services offered by the third-party Speech APIs. The application does not have any Machine learning services built in. The application is limited to the use of open source libraries for voice functions and Google Cloud Speech API for transcription.
The system doesn't have a dedicated backend services and shall make use of managed database and authentication services, in this case using Firebase Auth and DB.

## How to Use:

You need a subscription to Google Cloud Platform inorder to use this application. You need to [replace the API key here.]().
Besides, this app also relies on Firebase for Authentication and Remote datastore/backup. You may choose to ignore this using [this tutorial.]()

You can use this app as it is or you may choose to imporve on the application using the following details of the system.

## System Design

### Architecture:

![](https://github.com/safeer2978/Sanvaad/blob/main/images/arch.png)

The application makes use of MVVM architecture. We have Mutliple data store here as shown by the context diagram.

### Context Diagram:

![](https://github.com/safeer2978/Sanvaad/blob/main/images/context.png)

The databases are both designed using a relational schema illustrated by the following ER diagrams. 

### ER diagrams:
![](https://github.com/safeer2978/Sanvaad/blob/main/images/er1.png)

![](https://github.com/safeer2978/Sanvaad/blob/main/images/er2.png)
### Use Case Diagram

![](https://github.com/safeer2978/Sanvaad/blob/main/images/usecase.png)

### Class Diagram

![](https://github.com/safeer2978/Sanvaad/blob/main/images/classes.png)

![](https://github.com/safeer2978/Sanvaad/blob/main/images/5.png)


# Counters
An application where you can count things and manage your counters.

![counter list](https://i.imgur.com/oTN1S4r.png)
![examples](https://i.imgur.com/71q3Vmb.png)
![delete counter](https://i.imgur.com/aU7hW2Z.png)

## 🚀 Features
A few of the things you can do with Counters:

* Adds a named counter to a list of counters
* Increment and decrement any of the counters
* Search counters
* Shows a sum of all the counter values
* Shares a list of selected counters
* Deletes a single or a batch of counters
* Includes error handling when API connection fails
* Persists data back to the server
* Available in English and Portuguese languages
* Handles orientation changes
* Splash screen
* First access and second access
* Clean architecture!

## ⚙️ Stack
 * Retrofit 2 & OkHttp3 - API handling and logging
 * Gson - converts the API responses into the application models
 * LiveData & data binding - handle UI changes
 * Coroutines - async functions
 * Swipe refresh layout - swipe down to refresh list of counters
 * Hilt - dependecy injection
 * Android Navigation - navigation management between fragments
 * Material design
 * Shared Preferences - handles first/second access

## 📦 Setup
### Local server

Install and start the server
```
$ npm install
$ npm start
```

### Application
1. Open `Constants.kt` and change `BASE_URL` value to your server's IP address.
2. In Android Studio: select `Run -> Run 'app'` from the menu bar

## 💡 Feedback
Feel free to file an issue. Feature requests are always welcome. 

## 🆕 TODO
 * Missing proper batch deletion error handling (it should indicate if any request fails)
 * Search should open a custom Searchable Activity
 * Increase test coverage

## 🕵🏻‍♂️ Known issues
 * No results layout only shows when the second character is typed
 * Swipe refresh works while Search is activated (it should be disabled to avoid inconsistencies)

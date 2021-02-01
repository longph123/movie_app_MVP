# Movie App


<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
* [Installation](#installation)
* [Contact](#contact)
* [Dependencies](#dependencies)



<!-- ABOUT THE PROJECT -->
## About The Project

A demo Android application that getting JSON responses from The Movie DB APIs and presenting as a movie galleries, with features to showing grid-list movies that sorted by published date with ascending order, then when user touch on any movie, that'll see the details of movie (movie's name, published date, length, vote rate, description, poster, etc. Applied with MVP (Model - View - Controller) design pattern.

### Built With

* [Kotlin](https://www.kotlinlang.org)
* [The MovieDB APIs](https://developers.themoviedb.org/3/getting-started/introduction)

<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

## Installation

1. Get a free The MovieDB developer account at [https://www.themoviedb.org/login](https://www.themoviedb.org/login) and doing follow instructions on their [Introduction](https://developers.themoviedb.org/3/getting-started/introduction) to request new API_KEY for using.
2. Clone the repo
```sh
git clone https://github.com/longph123/movie_app.git
```
3. Enter your API_KEY in `utils/ApiInfos.kt`
```JS
val API_KEY = 'ENTER YOUR API_KEY';
```
4. Build the project and enjoy!

<!-- CONTACT -->
## Contact

* Long Pham - FB: [@neomatrixpro92](https://www.facebook.com/neomatrixpro92) - longph12394@gmail.com
* My Repos: [https://github.com/longph123](https://github.com/longph123)

<!-- DEPENDENCIES -->
## Dependencies
* [Mosby](https://github.com/sockeqwe/mosby)
* [Dagger](https://github.com/google/dagger)
* [Glide](https://github.com/bumptech/glide)
* [Calligraphy](https://github.com/chrisjenx/Calligraphy)
* [Retrofit](https://github.com/square/retrofit)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)

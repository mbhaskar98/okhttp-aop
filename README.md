
# OkHttp AOP

This project demonstrates how can a library intercept okhttp calls. Interception is not limited to the library and can even intercept calls from the parent application.
## Project Setup
* my_app - Parent Application
* aop_enabled_library - Library that registers the AOP aspect
## Project Structure

Project has 2 main components. 
    
    1. someFuncInApplicationThatDoesHTTPRequest -
    This represents any http request initiated by the app.
    2. OkHttpProxyAspect
    The aspect defined in the library that AOP weaver uses.
### Note
The only limitation is that the application also has to include plugin - id("com.ibotta.gradle.aop") and add dependency implementation("org.aspectj:aspectjrt:1.9.22.1")
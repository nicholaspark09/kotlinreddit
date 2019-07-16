## Kotlin Reddit
A basic app done in kotlin to help me practice and keep in touch

### Organization
The app is architected in a fashion that allows for implementation to change
 - The networking is done in a networking module
 - The networking module does not contain dagger so that downstream dependencies
   can use whatever versions of android they want
 - The networking module is accessed and used via interfaces created a relatively
   non-volatile upstream dependency

### Dependency Injection
 - As I'm excluding dagger in the networking module, dependency injection is done
   manually
 - Dependency Injection in the main application is done via dagger because it provides
   a nice easy way to switch out dependencies and control the directed object graph

### Why Kotlin?
 - It's the latest craze? While I prefer Java as it is straightforward and has
   fewer ways to do the same thing, Kotlin has its benefits (null safety, etc)
 - Kotlin is the language en-vogue and Google doesn't want any more lawsuits
 - Everyone wants it


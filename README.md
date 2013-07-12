ringfinger
==========

no. No. NO. You're looking for [octohipster](https://github.com/myfreeweb/octohipster), the new cool REST API microframework.  
This project is dead.  
I reused some code (stuff like pagination) in octohipster.  
I also reused ideas in [RapidMachine for Python](https://github.com/myfreeweb/rapidmachine).  

But this project's implementation mostly sucks.  
I learned a lot though.  
Making "extended Ring handlers" was an extremely stupid idea.  
HTML and REST at the same time wasn't smart too.  
Custom auth is not needed now that there's [friend](https://github.com/cemerick/friend).  
Dammit, it used a single iteration of SHA256.
Use a proper password hashing function like bcrypt, everyone.
Pluggable database adapters were kinda cool, but really not.  
Just use a database library directly.  
The mail library was great.  
[This escaping/transliteration/slugify code should not die.](https://github.com/myfreeweb/ringfinger/blob/master/fastfinger/src/main/clojure/fastfinger/hooks.clj)  

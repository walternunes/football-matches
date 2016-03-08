# **Football Matches application** #
Football Matches is an application that shows the result of the most important football leagues of the Europe.
Leagues: Champions League, Premier League, Primeira Division, Serie A, BundesLiga.

# **Contents and features** #
## Things that is possible to do in the app: ##
* Show all the games (and results) from two days before or two days after the current day
* Possible to select which leagues you want to follow (Premier League, Champions League, Primeira Division, Serie A, BundesLiga)
* Accessibility compatibility  (talkback)
* Widget in Home Screen capable to show the games of the day
* Multi Language support (en, es, pt, it, de)

## Relevant technical information: ##
* Usage of cache logic and content provider
* Usage of MultiSwipeRefreshLayout to update contents
* Usage of RecyclerView, tabLayout, RemoteViewsService
* Support MirrorLayout and Rtl

# **Set up the project** #
In order to build this project is necessary to have an API key. If you don't have one please register and request your own API key here: http://api.football-data.org. Next you will need to add a String named "api_key" into your resources file that could be a new XML file or the String.xml itself. According to the bellow example: <string name="api_key" translatable="false">your_key_here</string>

# **Screens** #
## Portrait layout ##

![f2.png](https://bitbucket.org/repo/pqjbaX/images/718662322-f2.png)

### Additional info ###

* Repo Owner: Walter Nunes Filho
* Email: jnunes.walter@gmail.com
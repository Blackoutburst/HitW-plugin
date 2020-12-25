# Hole in the Wall plugin

## Information this plugin is not affiliated to the Hypixel network this is only a community project !

This plugin is a remake of the game Hole in the Wall in [Hypixel](https://hypixel.net),
this plugin is only made for fun based on community idea. Featuring training componnent, tournament management component and community game mode idea.
Everything is realted to the original Hole in the Wall game !

## Main feature

### Standard wall size
For Qualification and finals stage training
![stdsize](/readme/stdsize.PNG)

### Custom wall size
For fun
![custom](/readme/customsize.PNG)

### Personnal scoreboard displaying various stats
* Stage: the current stage the player is playing
* Play time: for how long or how much time is remaining
* Wall: the current wall the player is playing
* Perfect walls: the number of perfect wall the player created
* Missing block: the number of hole non filled
* Misplaced block: the number of block placedin wrong location
* Choke: the number of bnlokc placed outside the play area
* Fly: if the player can fly or not

![scoreboard](/readme/scoreboard.PNG)

### Different play mode

#### Endless mode [Demo video](https://www.youtube.com/watch?v=nxHt8Q6FgiI&feature=youtu.be)
- Endless time
- Wall start with maximum amount of hole
- Customisable lever delay

#### Classic mode (compatible with blind) [Demo video](https://www.youtube.com/watch?v=Jbnk06_CMEQ&feature=youtu.be)
- Hypixel rules
- 2min stage time
- 0.5s before buildiong the next wall
- 1.5s lever delay
- Number of hole in wall are progresive

#### Time mode [Demo video](https://www.youtube.com/watch?v=yorUDxe1ons&feature=youtu.be)
- Hypixel rule
- 0.5s before buildiong the next wall
- 1.5s lever delay
- Number of hole in wall are progresive
- Stage end when the specified time is reached

#### Score mode [Demo video](https://www.youtube.com/watch?v=GkfVzYmnDlI&feature=youtu.be)
- Hypixel rule
- 0.5s before buildiong the next wall
- 1.5s lever delay
- Number of hole in wall are progresive
- Stage end when the specified score is reached

#### Blind mode (compatible with classic) [Demo video](https://www.youtube.com/watch?v=1zpa_CVBZio&feature=youtu.be)
- Holes become hiden after a certain time you have to build wall from memory
- Endless time
- Wall start with maximum amount of hole
- Customisable lever delay
- Customisable wall memorisation time


### Co-op System
#### Play with up to 4 player on the same wall
Co-op is compatible with all gamemode listed above
![coop](/readme/coop.PNG)

#### The commands for co-op are based on Hypixel party system (since Hole in the Wall player are Main Hypixel player)
![coopCommands](/readme/coopCommands.PNG)

#### All player inside the co-op share the same scoreboard (Except the fly line wich is personnal)
![scoreboardCoop](/readme/scoreboardCoop.PNG)

## Sub feature

#### Customisable lever delay
Using /delay [seconds] [Demo video](https://www.youtube.com/watch?v=xdb4xeRvknI&feature=youtu.be)
![delay](/readme/delay.PNG)

#### Customisable wall memorisation time
Using /memtime [seconds] [Demo video](https://www.youtube.com/watch?v=kTfBBMBosrI&feature=youtu.be)
![memtime](/readme/memtime.PNG)

#### Toggleable fly mode
Using /fly
![fly](/readme/fly.PNG)

#### Toggleable title text
Using /title [Demo video](https://www.youtube.com/watch?v=RFPCmq1mde0&feature=youtu.be)
![title](/readme/title.PNG)

On [Demo video](https://www.youtube.com/watch?v=RFPCmq1mde0&feature=youtu.be)
![titleon](/readme/titleon.PNG)

Off [Demo video](https://youtu.be/RFPCmq1mde0?t=14)
![titleoff](/readme/titleoff.PNG)

### Automatic rank based on Hypixel Hole in the Wall personal best using [Hypixel API](https://github.com/HypixelDev/PublicAPI)
Rank colors are based on the Hole in the Wall community discord rank colors
![rank](/readme/rank.PNG)

#### Customisable wall and glass color
Compatible with all stained clay / glass
![color](/readme/color.PNG)

## Commands
![commands](/readme/commands.PNG)

#### Tournament commands are not listed here since they are only avaible for people with op

There is actually more commands not listed here since they should not really be inside the plugin

## Tournament management system
Player with op status have acces to a special command called /tourney, this command allow to
- Add / remove player 1
- Add / remove player 2
- Add / remove camera man 1
- Add / remove camera man 2
- Add / remove tournament manager (usually himself)
- Display every people involved in the tournament (except spectators)
- Switch stage (qualification, finals)
- Start stage
- End tournament

Other player can do /spectate to spectate the tournament
- If they do the commands before a tournament start, they will be teleported once the tournament start
- If they do the commands while the tournement is running they will be teleported to the tournament

A message is broadcasted when the tournament start en player can hover the message to see who is playing against who ad click the message to spectate

## Special thanks
* Mason for helping me on the community server
* Puffleman, Logvey, dogette, Ueku, Winday and many more for idea and bug repport
* The whole HitW community for being so wholesome and supporting me
* The Hypixel team for creating the original game

# More informations on tournament coming soon

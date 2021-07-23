[![License](https://img.shields.io/github/license/Blackoutburst/HitW-plugin.svg)](LICENSE)
[![Release](https://img.shields.io/github/release/Blackoutburst/HitW-plugin.svg)](https://github.com/Blackoutburst/HitW-plugin/releases)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/b643553f65674d99b3e13395b446de4f)](https://www.codacy.com/gh/Blackoutburst/HitW-plugin/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Blackoutburst/HitW-plugin&amp;utm_campaign=Badge_Grade)

# Hole in the Wall Plugin

## DISCLAIMER
I do not own any permission over the real Hole in the Wall game created by Hypixel, this plugin is only made for the Hole in the Wall community on Hypixel.\
This plugin do not re create the game Hole in the Wall the way it work on Hypixel (by that i mean no pre lobby and a full game with 14 player).\
This plugin is designed as a core plugin for our Hole in the Wall community server so by itself you will probably be unable to do anything without the rest of the server files.

## Features

### Hole in the Wall game

It work exactly like Hypixel with one exception wall are not moving toward the player. (reason: on high level gameplay it's not noticeable and for a new player it's less stressful so they can focus more on playing well instead of rushing).\
![image](https://user-images.githubusercontent.com/30992311/126811386-0068059a-2135-4360-b7dc-720ee3e51720.png)

### Duel
[Implementation footage](https://youtu.be/3O-An6CLEao) **FLASHING LIGHTS**

`/duel player` make you able to duel anyone\
When you use this command, it open this menu where you can select to type of game you want (Qualification on the left or Finals on the right).\
![image](https://user-images.githubusercontent.com/30992311/126811813-ebec681e-4c4e-48c9-b51c-6e49b38d1081.png)

Once you click a game type the opponent will receive a duel request free to him to accept it or not.\
![image](https://user-images.githubusercontent.com/30992311/126811918-1fbb7c7c-31e2-4ad6-807c-653404bbb524.png)

If the opponent accepts your request, you will be teleported to a special area and the game will start (classic game only).\
It's possible to play with all modification available in the configuration menu for that the player sending the duel request must enable them first. (note only right sided walls is player dependant so one player can play left sided while the other one play right sided).

### Party / Coop system
Basically work like Hypixel for the available commands and how it works.\
`/p player` | `/party player` | `/coop player` Send a party invitation to a player.\

Some commands are just not implemented as they don't really serve a purpose on this server.\
![image](https://user-images.githubusercontent.com/30992311/126812538-cb31ab19-842a-436b-9958-16743968fde2.png)

While in a party every player in the party will be able to build the current wall, only the party leader can start a game.\
The game will always take the party leader configuration.\
Song, glass color, fly, title, brush lag are player dependant (wall color depends on the last player who flicked the lever).\
![image](https://user-images.githubusercontent.com/30992311/126812998-b779c1a4-0157-4177-9db7-a261601379d4.png)

### Game area system
This plugin uses a system of game area, so you are free to move around the map and you can only start a game when you are inside a game area leaving a game area make you instantly finish your game but this is optional as seen later.

### Play mode
`/play` start a game with endless time to play you can stop the game either by leaving the game are or using `/l` | `/leave`.\
`/play score [value]` play a game until you reach the specified score.\
`/play time [value]` play a game for a spcified time (in seconds).\
`/play classic` play a game using Hypixel rule set (best way to know what your score would be on Hypixel).

### Configuration menu
This is where you toggle most game modifier or settings, when joining the server you get a nether start and this item is used to open this menu.\
![image](https://user-images.githubusercontent.com/30992311/126807025-2222bd07-c6c1-4ba8-a8e0-b861a15cea94.png)

From left to right and bottom to top:

#### Walls animation
![image](https://user-images.githubusercontent.com/30992311/126807237-ed412499-9e84-45e8-ae4a-6f5f0b967efc.png)

This setting bring back the old walls animation that used to be on Hypixel.

#### Right sided walls
![image](https://user-images.githubusercontent.com/30992311/126807409-14287877-5f00-4057-a603-1f0bb7f96c23.png)

This setting is really special and concern only a few players but it flip the walls to make them right sided, I'm not going to provide explanation on what a right sided or left sided wall is here.

#### Blind mode
![image](https://user-images.githubusercontent.com/30992311/126807616-838c7931-3fb1-4fd9-a166-b7724ef5ed83.png)

As explained in the screenshot you have a customisable amount of time to memorize the wall and then you need to build it from memory.

#### Break mode
![image](https://user-images.githubusercontent.com/30992311/126807760-c2f03c21-4865-4eb8-afc8-ba8e6694cdbb.png)

This game mode basically inverts the usual gameplay, instead of placing block you have to break them.

#### Auto leave
![image](https://user-images.githubusercontent.com/30992311/126807848-6c1167dc-f372-4525-96ab-fed0f100ed38.png)

This setting make you automatically end the game when you step out of your game area, disabling it can be useful for players who move a lot and could potentially end their game by mistake.

#### Title
![image](https://user-images.githubusercontent.com/30992311/126808037-c9dab712-7bc3-4783-bb7e-208fae69683b.png)

If enabled message such as countdown, perfect wall and game end will show as Minecraft title else they will just appear as chat messages.

#### Fly
![image](https://user-images.githubusercontent.com/30992311/126808194-db27e2a6-5a17-4b2d-a861-5273a2a8ac37.png)

Make you able to fly or not if you prefer playing no fly and don't want to fly by mistake.

#### Song
![image](https://user-images.githubusercontent.com/30992311/126808373-078d7ebf-1e3a-4435-8c15-a0a673343a11.png)

Open a sub menu with multiple songs to select from. This is the song you will hear while playing a game, or ou can just chose to not play any song while playing.

#### Glass color
![image](https://user-images.githubusercontent.com/30992311/126808500-1b9f31fe-6523-4af3-b8f5-12cd6f3f4522.png)

Open a sub menu when you can select the glass color you want to play with.

#### Wall color
![image](https://user-images.githubusercontent.com/30992311/126808568-9ed60312-4ef1-4c77-9676-392267146748.png)

Open a sub menu when you can select the wall color you want to play with.

#### Brushing lag
![image](https://user-images.githubusercontent.com/30992311/126808703-28996819-fa8c-4e11-b749-9ba01b08f603.png)

This settings only exist because Hypixel and this server are not hosted in the same country at all so it's used to simulate your Hypixel ping. (it's mainly important for high-level player).

#### Memory time
![image](https://user-images.githubusercontent.com/30992311/126808903-a461e739-db17-442a-9a45-c79f0cbe9597.png)

Use this to change how much time you have to memorize the wall while playing blind mode.

#### Lever delay
![image](https://user-images.githubusercontent.com/30992311/126809004-bb2c683a-ea4f-42fa-9982-0b9fcca2888e.png)

Change how much time the lever is disabled after using it, this settings only affect endless games as other type of game (score, time, classic) use Hypixel lever delay.

### Scoreboard
![image](https://user-images.githubusercontent.com/30992311/126809263-badbe1e0-a144-4277-9b16-7d84bb93fe02.png)

This scoreboard shows current game information such as:
 - The stage you are playing.
 - How long are you playing (or how much time remaining for time and classic).
 - The number of perfect walls you made.
 - The wall you are currently building.
 - You current score.
 - How many holes you didn't filled.
 - How much block you placed that weren't holes.
 - How much time you placed a block outside your play area.

### Wall informations
Everytime you pull the lever you get a chat message looking like this.\
![image](https://user-images.githubusercontent.com/30992311/126809641-5339159e-6da6-46cc-a7c0-155f77eadbc2.png)

It tells you how much score you made and tell you what type of mistake you made if you made at least 1.\
This message also shows how much time you needed to complete the wall.

### Game summary
Show all the information of what happened in the game such as score, time, number of walls and mistakes.\
![image](https://user-images.githubusercontent.com/30992311/126810196-9009fcf2-5a3e-40a9-89ba-17f27f19ac84.png)

### Rank from Hypixel API
When a player join, they get a rank skill by 50+ division (and sub 25+ for the plus color) using their Hypixel Hole in the Wall best score (Qualification or Finals).\
![image](https://user-images.githubusercontent.com/30992311/126810381-065217eb-252b-4deb-84f7-81981677aef9.png)

Here is the rank list, rank, and rank color are based on the Hole in the Wall discord server.\
![image](https://user-images.githubusercontent.com/30992311/126810629-998c79a3-5dfc-452f-8b1e-86912e0e260b.png)

### NPCS
This plugin manages 3 groups of NPC, the hall of fame of every notable Hole in the Wall player.\
Tournament winner NPC.\
Warps NPC used to navigate through different area in the server such as Spawn, Qualification and Finals.\
![image](https://user-images.githubusercontent.com/30992311/126811189-88325c9a-6f33-4ee9-b737-a77ffa53e131.png)

### Tournament system
The tournament system got discontinued and removed when this plugin got reworked due to the lack of tournament in the community, it might come back one day if tournament activity is revived.

## Special Thanks
Flame_Frost_ : for introducing me to this awesome community.\
HammyInTheWall : for creating the community like we know it today.\
MasonMC : for helping me in the early day with this server.

And to all the cool people and friend I've met along the way
 - Simown
 - Puffleman
 - Cosmic
 - Khantrast
 - Winday
 - Fuby
 - Ugy
 - Mars
 - Jaq
 - Dobg
 - Arcxire
 - Lapis
 - Jazmin
 - Glampkoo
 - Bertje
 - Venom
 - Ueku
 - Audero
 - Norrie
 - HMT

And many more thanks you all ❤️

# Shiny Hunting Helper

## Ideas behind Shiny Hunting Helper

I want to create Shiny Helper to help fellow shiny hunters in the *Pokémon* games 
locate and capture coveted shiny *Pokémon*. Shiny *Pokémon* are extremely rare variants
of regular *Pokémon* with different colour patterns that can be found within almost
every *Pokémon* game over the last 20 years. Players that enjoy collecting these rare
variants (like myself) are called shiny hunters in the community. Although only a 
small percentage of players choose this playstyle, the r/ShinyPokemon reddit currently
has over 150,000 members, many of whom this type of application could be very handy for.

Some features I want my tool to have:

- Keeping track of *Pokémon* that the user wants to hunt, is currently hunting, and has already hunted.
- To be able to add/remove *Pokémon* from these lists at will.
- To detail where the optimal location for a *Pokémon* is and what method of hunting is best.
- To provide the odds of encountering a chosen shiny *Pokémon*.

My inspiration to create this tool comes from my personal experience in shiny hunting.
To my knowledge, there does not exist any kind of helper tool specialized for this purpose
for players. Compiling everything into one application will save players (including myself)
the time it takes to search through various websites where the information is usually
spread across many pages. Additionally, it is not uncommon for information on these
websites to not be entirely correct. The goal is for this tool to be genuinely
helpful for players interested in the niche of shiny hunting!

## User Stories

- As a user, I want to be able to add Pokémon to my want to hunt list.
- As a user, I want to be able to delete Pokémon to my want to hunt list.
- As a user, I want to be able to view my want to hunt list.
- As a user, I want to be able to move a Pokémon from my want to hunt list to my currently hunting list.
- As a user, I want to be able to delete a Pokémon from currently hunting list.
- As a user, I want to be able to view my currently hunting list.
- As a user, I want to be able to mark a Pokémon as hunted.
- As a user, I want to be able to see all of my hunted Pokémon.

- As a user, I want to be able to save my want to hunt list to file.
- As a user, I want to be able to save my currently hunting list to file.
- As a user, I want to be able to save my hunted list to file.
- As a user, I want to be able to load my want to hunt list to file.
- As a user, I want to be able to load my currently hunting list to file.
- As a user, I want to be able to load my hunted list to file.

## Phase 4: Task 2

Tue Nov 23 10:52:22 PST 2021
Added a pokemon to the plan to hunt list.

Tue Nov 23 10:52:22 PST 2021
Added a pokemon to the plan to hunt list.

Tue Nov 23 10:52:22 PST 2021
Added a pokemon to the plan to hunt list.

Tue Nov 23 10:52:22 PST 2021
Loaded plan to hunt list.

Tue Nov 23 10:52:22 PST 2021
Added a pokemon to the currently hunting list.

Tue Nov 23 10:52:22 PST 2021
Added a pokemon to the currently hunting list.

Tue Nov 23 10:52:22 PST 2021
Loaded currently hunting list.

Tue Nov 23 10:52:22 PST 2021
Added a pokemon to the hunted list.

Tue Nov 23 10:52:22 PST 2021
Loaded hunted list.

Tue Nov 23 10:52:26 PST 2021
Added a pokemon to the plan to hunt list.

Tue Nov 23 10:52:37 PST 2021
Added a pokemon to the currently hunting list.

Tue Nov 23 10:52:42 PST 2021
Added a pokemon to the hunted list.

Tue Nov 23 10:53:02 PST 2021
Removed a pokemon to the hunted list.

Tue Nov 23 10:53:05 PST 2021
saved plan to hunt list

Tue Nov 23 10:53:05 PST 2021
saved currently hunting list.

Tue Nov 23 10:53:05 PST 2021
saved hunted list.

## Phase 4: Task 3

If I was given more time for this project changes I would make are:
- Extract the majority of the code from the 3 list classes into an abstract class. 
Currently, the 3 list classes are nearly identical in implementation, with the only main 
difference being the name. I would use an abstract class with 3 sub-classes over just one 
list class just in case I want the different lists to have slightly different functionality 
later on.
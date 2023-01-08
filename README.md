# Gradient-Particles

This is my own project that I did for fun and to learn more about real time applications.
I was inspired to make this by the real-life behaviour of ants which leave behind and follow pheromone trails.

Note: Currently working on an improved version of this project that uses vector navigation, is configurable and supports multiple particle behaviours.

This is a simulation of particles chasing each other. 

The screen is divided into cells. Particles initially move randomly in one of eight directions. 
Particles are only able to change direction within a 90-degree arc while moving (3 valid directions). 
As particles move the leave a trail and a signal up to 5 cells behind. 
During movement, particles look at surrounding cells and attempt to move to cells with the strongest signal. This causes particles to follow each other.

![Gradient Particles Animation](https://user-images.githubusercontent.com/79414856/210592653-bf267f20-9ab9-4008-9fb6-511340054e37.gif)


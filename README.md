<h1 align="center">
  <div>
    <img width="80" src="https://raw.githubusercontent.com/itischrisd/itis-PJATK/main/logo.svg" alt="" />
  </div>
  Game of Life
</h1>

Repository that contains final project implementation for PSM (Foundations of Computer Simulations) practical classes, taught by Piotr Trończyk during studies on [PJAIT](https://www.pja.edu.pl/en/).

Project's main goal was to implement [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life), a cellular automaton devised by the British mathematician John Horton Conway in 1970. The game is played on a grid of cells, where each cell can be either alive or dead. The state of the cells evolves over discrete time steps according to a set of rules based on the states of neighboring cells. Java Swing was used to create a simple GUI for the game, allowing users to interact with the simulation.

The following code is distributed under the [GPLv3](./LICENSE).

---

If you need some help, notice any bugs or come up with possible improvements, feel free to reach out to me and/or create a pull request. Keep in mind that some code may not be well optimized, as algorithm implamantation had to follow the variants presented during subject's lectures. Most tree strctures have objective implementation (excluding Binary Heap, which uses classic array) for the sake of more human readable logic. In such cases traversing and node lookup time complexity might not follow the real implemantation, but rather skip over it and assume best-case variants.

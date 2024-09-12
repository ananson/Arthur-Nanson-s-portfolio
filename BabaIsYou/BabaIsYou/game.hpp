#ifndef GAME_HPP
#define GAME_HPP

#include "board.hpp"
/*
 * Ce qui sert de modèle ou les opérations complexes auront lieu.
 */
class Game{

private:
    Board board;
    int currentLevel;

public:
    Game();
    Game(int);
    Board getBoard();
    int getLevel();
    void setLevel(int); // choisir le niveau
    void createBoard(); // création du plateau avant de jouer
    void nextLevel(); // passer au niveau suivant donc appeller la fonction de création de plateau ici
};

#endif // GAME_HPP

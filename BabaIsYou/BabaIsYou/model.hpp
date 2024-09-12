#ifndef MODEL_HPP
#define MODEL_HPP
#include "game.hpp"
#include "observable.h"

/*
 * Sert de design pattern Facade, appelle les méthodes de Game
 */
class Model : Observable{

private:
    Game game;
    std::vector<Observer*> observers;

public:
    Model();
    void nextLevel();
    void restart();
    void addObserver(Observer*);
    void removeObserver(Observer*);
    void notifyObservers();
};

#endif // MODEL_HPP

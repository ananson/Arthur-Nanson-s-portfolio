#ifndef CONTROLLER_HPP
#define CONTROLLER_HPP
#include "view.hpp"
#include "model.hpp"

class Controller{

private:
    View view;
    Model model;

public:
    Controller(Game);
    /**
     * @brief starts the game
     */
    void start();
    /**
     * @brief play the game loop that calls the game's functions
     */
    void play();
};

#endif // CONTROLLER_HPP

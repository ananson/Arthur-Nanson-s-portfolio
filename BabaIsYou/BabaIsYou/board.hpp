#ifndef BOARD_HPP
#define BOARD_HPP
#include "component.h"
#include <iostream>
#include <vector>
#include <sprite.h>
#include <rules.h>
class Board{

private:
    int row;
    int column;
    std::vector<std::vector<std::vector<Component>>> board; // plusieurs choses peuvent etre sur la meme case, donc on en fait un vecteur
public:
    Board(int,int);
    bool checkWin();
    void restart();
    std::vector<Component> getComponentAt(int,int);
    int getRowLength();
    int getColumnLength();

};
#endif // BOARD_HPP

#ifndef VIEW_HPP
#define VIEW_HPP
#include "model.hpp"
#include "observable.h"
#include "observer.h"

class View : Observer{
private:
    Model& subject;
public:
    View(Model&);
    void printBoard();
    void update(char);
};

#endif // VIEW_HPP

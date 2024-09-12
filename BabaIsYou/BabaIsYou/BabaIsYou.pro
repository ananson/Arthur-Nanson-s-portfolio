TEMPLATE = app
CONFIG += console c++17
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += \
        main.cpp

HEADERS += \
    board.hpp \
    component.h \
    controller.hpp \
    game.hpp \
    model.hpp \
    observable.h \
    observer.h \
    sprite.h \
    view.hpp \
    words.h

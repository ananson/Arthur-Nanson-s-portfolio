;Arthur Nanson, matricule 000476431
;Mars 2019
;Programme qui teste les nombres positifs inférieurs à 200 et qui imprime le nombre si c'est un nombre premier

%include "io.inc"
section .text
global CMAIN
CMAIN:
    mov ebp, esp; for correct debugging
    mov ebx, 1
    
START:
    add ebx, 1 ;ajoute 1 à la valeur testée
    mov ecx, 1 ;initialise le diviseur à 1
    cmp ebx, 200 ;rentre dans la boucle si la valeur testée est en dessous de 200, sinon jump à la fin du code
    jle boucle
    jmp End
    
boucle:
    add ecx, 1 ;incrémente le diviseur, pour tester tous les cas possibles
    mov edx, 0 ;remet la valeur du reste de la division entière à 0
    cmp ecx, ebx ;si la valeur testée est égale au diviseur, c'est que la valeur est un nombre premier car aucun diviseur ne donne un reste de 0
    je True
    mov eax, ebx ;rentre la valeur de ebx(valeur testée) dans eax car eax est changé par la division
    idiv ecx ;divise eax(valeur testée) par ecx (diviseur)
    cmp edx, 0 ;regarde si le reste de la division est égal à 0, si oui, c'est que le nombre n'est pas premier et on recommence donc avec le nombre suivant
    je START
    jmp boucle
    
True: ;permet d'imprimer la valeur qui est un nombre premier
    PRINT_UDEC 1, ebx ;imprime la valeur
    NEWLINE
    jmp START ; test de la valeur suivante
    
End:
    xor eax, eax
    ret
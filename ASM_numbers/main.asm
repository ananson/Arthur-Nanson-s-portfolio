;Arthur Nanson, matricule 000476431
;Mars 2019
;Programme qui teste les nombres positifs inf�rieurs � 200 et qui imprime le nombre si c'est un nombre premier

%include "io.inc"
section .text
global CMAIN
CMAIN:
    mov ebp, esp; for correct debugging
    mov ebx, 1
    
START:
    add ebx, 1 ;ajoute 1 � la valeur test�e
    mov ecx, 1 ;initialise le diviseur � 1
    cmp ebx, 200 ;rentre dans la boucle si la valeur test�e est en dessous de 200, sinon jump � la fin du code
    jle boucle
    jmp End
    
boucle:
    add ecx, 1 ;incr�mente le diviseur, pour tester tous les cas possibles
    mov edx, 0 ;remet la valeur du reste de la division enti�re � 0
    cmp ecx, ebx ;si la valeur test�e est �gale au diviseur, c'est que la valeur est un nombre premier car aucun diviseur ne donne un reste de 0
    je True
    mov eax, ebx ;rentre la valeur de ebx(valeur test�e) dans eax car eax est chang� par la division
    idiv ecx ;divise eax(valeur test�e) par ecx (diviseur)
    cmp edx, 0 ;regarde si le reste de la division est �gal � 0, si oui, c'est que le nombre n'est pas premier et on recommence donc avec le nombre suivant
    je START
    jmp boucle
    
True: ;permet d'imprimer la valeur qui est un nombre premier
    PRINT_UDEC 1, ebx ;imprime la valeur
    NEWLINE
    jmp START ; test de la valeur suivante
    
End:
    xor eax, eax
    ret
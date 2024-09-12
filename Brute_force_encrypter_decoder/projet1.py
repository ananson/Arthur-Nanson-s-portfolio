"""
Arthur Nanson, matricule 000476431
Mercredi 6 mars 2019
Programme permettant de trouver une traduction à un message codé et de l'afficher avec un dictionnaire qui sert a coder un texte de la même manière.
Input: Le nom du fichier, qui contient le nombre de lettres à changer, les mots devant se trouver dans le texte traduit ainsi que le texte à décrypter.
Output: La traduction du texte et un dictionnaire utilisé pour coder un texte de la même manière.
"""

import sys

def load_data(fileName):
    """
    Cette fonction sert a ouvrir le fichier donné en argument pour prendre les différentes informations à notre dispostion
    Input: Le nom du fichier
    Output: Les trois informations que nous donne le fichier en input
    """
    with open(fileName) as inputfile: #ouvre le fichier texte
        inputfile=list(inputfile)
        count=inputfile[0].strip()
        try: #vérifie que count soit bien une valeur qui permet au programme de fonctionner
            count=int(count)
        except:
            print("Valeur de count erronée.")
            sys.exit(0)
        word_list=inputfile[1].strip()
        crypted=inputfile[2]
        mon_tuple=(count, word_list, crypted)
    return mon_tuple #retourne les différentes informations sous forme de tuple


def decrypt(data):
    """
    Cette fonction prépare les informations nécéssaires pour décoder le texte et retourne le texte décodé ainsi que le dictionnaire
    Le paramètre data est le résultat de la fonction load_data
    """
    count,words,crypted=data
    words_list=[]
    words_list=words.split() #Fait une liste de tous les mots pour pouvoir loop dedans facilement
    liste_crypted=list(crypted)
    liste_crypted.remove("'") #retire les guillemets de la liste
    liste_crypted.pop()

    res=recursif(count, crypted, words_list) #appel a la fonction récursive qui permet de déchiffrer le message crypté

    return res #retourne res qui est une liste de toutes les traductions possibles avec leur disctionnaire


def check_dico(dico):
    """
    Fonction permettant d'associer les espaces dans le dictionnaire quand la longueur du dictionnaire est count-1 et que le dictionnaire est intervertible
    On reçoit un dictionnaire en paramètre et on en renvoit un autre après l'avoir potentiellement changé
    """
    liste_cles=[]
    liste_cles2=[]
    liste_valeurs=[]

    for i in dico:
        liste_cles.append(i)
        liste_cles2.append(i)
        liste_valeurs.append(dico[i])

    for a in liste_cles2:
        if a in liste_valeurs: #enlève chaque élément qui est une clé et une valeur du dictionnaire
            liste_cles.remove(a)
            liste_valeurs.remove(a)

    if len(liste_cles)==1 and len(liste_valeurs)==1: #si il ne reste qu'un élément dans chaque liste, on les associe dans le dictionnaire, utilisé pour associer les espaces
        dico[liste_valeurs[0]]=liste_cles[0]

    return dico


def traduction(dico,liste_crypted):
    """
    Fonction qui prend un dictionnaire et une liste cryptée en paramètre et qui décrypte cette liste en utilisant le dictionnaire
    Retourne un tuple avec la traduction et le dictionnaire
    """
    decrypted = ""

    for i in liste_crypted:
        if i in dico: #si la lettre est une clé du dictionnaire, on ajoute sa valeur dans le dictionnaire au résultat
            decrypted+=dico[i]
        else:
            decrypted += i

    return (decrypted,dico)


def output_decrypted(param):
    """
    Fonction qui affiche le résulat en prenant une liste en paramètre
    """
    #La longueur de la liste nous indique le nombre de résulats trouvés
    if len(param)==0:
        print("Pas de résultat de traduction")
    elif len(param)==1:
        tuple=param[0][0]
        decrypted,dico=tuple
        dico=inverse_dico(dico)
        print("Traduction trouvée: ", decrypted, ", avec le dictionnaire:", dico)
    else:
        print("Plusieurs solutions trouvées")


def verification(words_list, solution):
    """
    Fonction qui vérifie si la solution trouvée peut être utilisée, c'est à dire que le dictionnaire soit inversible et que tous les mots de words_list soient présents dans la solution
    Prend la liste de mot et un tuple qui contient le dictionnaire et le texte traduit en argument
    """
    res=[]

    reponse, dico=solution
    correct=True

    for j in words_list: #regarde si tous les mots de words_list sont dans le texte traduit
        if j not in reponse:
            correct=False

    if correct:
        liste_cles = []
        liste_cles2 = []
        liste_valeurs = []

        for k in dico:
            liste_cles.append(k)
            liste_cles2.append(k)
            liste_valeurs.append(dico[k])

        for l in liste_cles2:
            if l in liste_valeurs:
                liste_cles.remove(l)
                liste_valeurs.remove(l)

        if len(liste_cles)==0 and len(liste_valeurs)==0: #vérifie que le dictionnaire utilisé peut être inversé
            res.append(solution) #append la solution à la liste si elle est valable, une liste permet d'avoir potentiellement plusieurs solutions

    return res


def recursif(count, crypted, words_list, position=0, dico={}, solution=[]):
    """
    Fonction récursive qui premet de déchiffrer le code et de trouver le dictionnaire qui l'a permit
    Prend en paramètre le nombre de lettres à changer, le texte crypté, les mots devant se trouver dans le texte traduit, la position dans la liste des mots(utile pour la récursivité), le citionnaire et une liste de toutes les solutions
    Retourne une liste avec toutes les solutions trouvées, chaque partie de la liste est un tuple composé du texte traduit et du dictionnaire utilisé
    """
    if len(words_list)==position: #Si cette condition est vérifiée, c'est qu'on est à la fin de la liste de mot et que cette branche de récursivité est donc finie
        return solution

    elif len(dico)==count: #si le nombre de lettres changées est le même que count, on vérifie que la solution soit valable et si elle l'est, on la rajoute à la liste de solutions
        translate=traduction(dico,crypted)
        res=verification(words_list,translate)
        if len(res)>0 and res not in solution:
            solution.append(res)
        return solution

    else:
        mot=words_list[position] #le mot que l'on va traiter est à la position "position", on change cette position à chaque appel récursif

        liste_crypted=list(crypted)
        liste_crypted.remove("'")
        liste_crypted.pop()

        decalage = 0 #variable qui sert a décaler le mot dans la chaîne de texte cryptée
        longueur_crypted=len(liste_crypted)

        dico_temp = dict(dico) #sauvegarde du dictionnaire, que l'on reprendra si la solution testée est fausse

        for my_loop in range(longueur_crypted-len(mot)+1): #loop pour toutes les posibilités de positions du mot dans le message codé

            compteur_compare=decalage
            broke=False

            for i in mot: #loop dans chaque lettre du mot

                if i != liste_crypted[compteur_compare] and liste_crypted[compteur_compare] not in dico:
                    dico[liste_crypted[compteur_compare]]=i #ajoute une clé et une valeur au dictionnaire si i est différent de la lettre dans le message codé
                elif liste_crypted[compteur_compare] in dico:
                    if i != dico[liste_crypted[compteur_compare]]: #une lettre est utilisée deux fois différement, la solution est de toute façon fausse donc on break cette loop
                        dico = dict(dico_temp)
                        broke=True
                        break

                compteur_compare+=1 #passe à la lettre suivante dans le message crypté

                if len(dico)>count: #le dictionnaire est plus grand que le nombre de lettre a changer, la solution est donc fausse

                    dico = dict(dico_temp)
                    broke=True
                    break

            if broke is False:

                if " " in dico and len(dico)==count-1: #regarde si il y a un espace dans e dicitonnaire et l'ajoute si il y en a un en clé mais pas en valeur
                    dico=check_dico(dico)

                if len(dico) == count: #appel récursif pour arriver à la condition d'arrêt et vérifier si la solution est valable
                    recursif(count, crypted, words_list, position, dico)
                    dico = dict(dico_temp)

                elif len(words_list) > 0: #continue les appels récursifs jusqu'à avoir un dictionnaire qui a une longueur égale à count
                    recursif(count,crypted,words_list,position+1,dico) #posotion + 1 pour passer au mot suivant dans la liste de mots

            decalage+=1 #décale le mot dans le texte crypté
    return solution


def inverse_dico(dico):
    """
    Fonction qui inverse les clés et les valeurs d'un dictionnaire pour pouvoir crypter un message à l'aide de ce dernier
    """
    new_dico={}
    for i in dico:
        new_dico[dico[i]] = i
    return new_dico

if len(sys.argv) > 1: #prend les informations données en argument dans le terminal
    fileName=sys.argv[1]
else:
    print("Erreur lors de la lecture du fichier")
    sys.exit(0)

output_decrypted(decrypt(load_data(fileName)))

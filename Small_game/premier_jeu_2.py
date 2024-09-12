import pygame, time, sys, random

pygame.init()

largeur = 1500
hauteur = 900

screen = pygame.display.set_mode((largeur,hauteur))

BLACK = (0,0,0)
YELLOW = (234, 255, 37)
player_size = 40
ennemies_size = 40
bullet_size = 10
WHITE = (255,255,255)
RED = (255,0,0)
ORANGE = (255,199,0)
GREEN = (0,255,0)
GREY = (165,165,165)
DARK_GREY = (70,70,70)
BACKGROUND_COLOR = WHITE

arial = pygame.font.SysFont("Arial", 60)
pokemon_35 = pygame.font.SysFont("Pokemon GB.ttf", 35)

game_over = False

gauche = False
droite = False
up = False

MAX_JUMP = 300
jump = 0
NOMBRE_JMP = 1
jump_left = NOMBRE_JMP

clock = pygame.time.Clock()

GROUND = 800

bullets = []
money = []
ennemies = []
direction = 'd'
Shop_Done = False

LVL = 1
PLAYER_LVL = 1
SPEED = 10
PLAYER_MAX_HP = 10
PLAYER_HP = PLAYER_MAX_HP
PLAYER_ATK = 4
BALLES_PERCANTES = False
ARGENT = 0
compteur_invu = 0
GOLD_MIN = 1
GOLD_MAX = 5
DROP_RATE = 0.4
money_size = 10
INVU_MAX = 30

upgrades = [SPEED, PLAYER_MAX_HP, PLAYER_ATK, GOLD_MIN, GOLD_MAX, DROP_RATE, INVU_MAX, MAX_JUMP, NOMBRE_JMP, BALLES_PERCANTES]
prix_speed = 5
prix_hp_max = 5
prix_atk = 5
prix_gold_min = 7
prix_gold_max = 7
prix_drop = 15
prix_invu = 10
prix_max_jmp = 7
prix_nbr_jmp = 25
prix_perc = 100
prix = [prix_speed, prix_hp_max, prix_atk, prix_gold_min, prix_gold_max, prix_drop, prix_invu, prix_max_jmp,
        prix_nbr_jmp, prix_perc]


nombre_ennemies = 10
ENNEMIES_HP = 10
ENNEMIES_ATK = 4
ENNEMIES_SPEED_MAX = 3

player_pos = [730, 800 - player_size]
LEVEL_DONE = False

def spawn(LVL):

    x = random.randint(0,150-LVL*5)
    if x<1:
        return True
    else:
        return False

def new_ennemies():

    pos = []
    x = random.random()
    if x > 0.5:
        pos.append(0)
    else:
        pos.append(largeur-ennemies_size)
    y = random.randint(0,GROUND-ennemies_size)
    pos.append(y)
    pos.append(ENNEMIES_HP)
    z = random.randint(1,ENNEMIES_SPEED_MAX)
    pos.append(z)
    pos.append(True)
    return pos

def draw_ennmemies(ennemies):

    for i in ennemies:
        if i[2] > 0:
            pygame.draw.rect(screen,BLACK, (i[0]-3, i[1] - 3, (ennemies_size + 6), (ennemies_size+6)))
            pygame.draw.rect(screen, RED, (i[0], i[1], ennemies_size, ennemies_size))
    return

def ennemies_movements(player_pos, ennemies):

    for i in range(len(ennemies)):
        if ennemies[i][2] > 0:
            if player_pos[0] > ennemies[i][0]:
                ennemies[i][0] += ennemies[i][3]

            elif player_pos[0] < ennemies[i][0]:
                ennemies[i][0] -= ennemies[i][3]


            if player_pos[1] > ennemies[i][1]:
                ennemies[i][1] += ennemies[i][3]

            elif player_pos[1] < ennemies[i][1]:
                ennemies[i][1] -= ennemies[i][3]

    return ennemies

def collisions_player(player_pos, ennemies, player_size, ennemies_size):

    x = player_pos[0]
    y = player_pos[1]

    for i in ennemies:
        if i[2] > 0:
            if (i[0] >= x and i[0] < (x+player_size)) or (x >= i[0] and x < (i[0]+ ennemies_size)):
                if (i[1] >= y and i[1] < (y + player_size)) or (y >= i[1] and y < (i[1] + ennemies_size)):
                    return True

    return False

def collisions_bullets(bullets, ennemies, bullet_size, ennemies_size):

    for i in range(len(bullets)):
        if bullets[i][3] == True:
            for j in range(len(ennemies)):
                if (ennemies[j][0] >= bullets[i][0] and ennemies[j][0] < (bullets[i][0] + player_size)) or (bullets[i][0] >= ennemies[j][0] and bullets[i][0] < (ennemies[j][0] + ennemies_size)):
                    if (ennemies[j][1] >= bullets[i][1] and ennemies[j][1] < (bullets[i][1] + bullet_size)) or (bullets[i][1] >= ennemies[j][1] and bullets[i][1] < (ennemies[j][1] + ennemies_size)):
                        if ennemies[j][2] > 0:
                            bullets[i][3] = False
                            ennemies[j][2] -= PLAYER_ATK

    return (bullets, ennemies)

def argent(money):

    for i in range(len(money)):
        if money[i][2] == True:
            pygame.draw.circle(screen, YELLOW, (money[i][0], money[i][1]), money_size, money_size)
            if money[i][1] < GROUND - 20:
                money[i][1] += 10
    return money

def take_money(player_pos, money, player_size, money_size, gold_min, GOLD_MAX):

    x = player_pos[0]
    y = player_pos[1]
    add = 0
    for i in range(len(money)):
        if money[i][2] == True:
            if (money[i][0] >= x and money[i][0] < (x+player_size+10)) or (x >= money[i][0] and x < (money[i][0]+ (money_size)*2)):
                if (money[i][1] >= y and money[i][1] < (y + player_size)) or (y >= money[i][1] and y < (money[i][1] + (money_size)*2)):
                    money[i][2] = False
                    add += random.randint(GOLD_MIN, GOLD_MAX)
    return (money, add)


def health_bars(max_health, current_health):

    pourcentage = max_health/current_health

    if pourcentage <= 2:
        color = GREEN
    elif pourcentage <= 4:
        color = ORANGE
    else:
        color = RED

    pygame.draw.rect(screen, BLACK, (40, 30, (max_health *15)+ 6, 35))
    pygame.draw.rect(screen, WHITE, (43, 33, (max_health*15), 29))
    pygame.draw.rect(screen, color, (43, 33, (current_health * 15), 29))
    label = pokemon_35.render(str(current_health) + " / " + str(max_health), 1, BLACK)
    screen.blit(label, ((29 + max_health*7), (37)))
    return

def death(player_pos, player_size):

    screen.fill(WHITE)
    for i in range(255):
        pygame.draw.rect(screen, (i,i,i), (player_pos[0], player_pos[1], player_size, player_size))
        pygame.display.update()
        for j in range(500000):
            pass
    for k in range(255, 0, -1):
        if k > 183:
            game_over_label = arial.render("YOU DIED", 1, (k,k,k))
        else:
            game_over_label = arial.render("YOU DIED", 1, (183, k, k))
        screen.blit(game_over_label, (largeur - 925, hauteur - 500))
        pygame.display.update()
        for l in range(500000):
            pass
    for m in range(255, 0, -1):
        screen.fill((m,m,m))
        pygame.display.update()
        for n in range(500000):
            pass

def sol():

    pygame.draw.rect(screen, DARK_GREY, (0, GROUND, largeur, (hauteur - GROUND)))

    return

def shop(upgrades, argent, prix, current_health):
    shop_not_done = True
    pos_curseur = [80, 100]
    curseur = 0
    money_pos = [675,35]
    while shop_not_done:
        screen.fill(WHITE)
        pygame.draw.rect(screen, BLACK, (pos_curseur[0], pos_curseur[1], 700, 60), 5)

        vitesse = pokemon_35.render("Vitesse +2 (Vitesse actuelle : " + str(upgrades[0]) + ") : " + str(prix[0]) + " $", 1, BLACK)
        screen.blit(vitesse, (100, 120))

        max_hp = pokemon_35.render("Vie maximum +4 (Vie maximum actuelle : " + str(upgrades[1]) + ") : " + str(prix[1]) + " $", 1, BLACK)
        screen.blit(max_hp, (100, 180))

        atk = pokemon_35.render("Attaque +2 (Attaque actuelle : " + str(upgrades[2]) + ") : " + str(prix[2]) + " $", 1, BLACK)
        screen.blit(atk, (100, 240))

        gold_min = pokemon_35.render("Argent minimum par pièce + 1 (Actuel : " + str(upgrades[3]) + ") : " + str(prix[3]) + " $", 1, BLACK)
        screen.blit(gold_min,(100, 300))

        gold_max = pokemon_35.render("Argent maximum par pièce + 1 (Actuel : " + str(upgrades[4]) + ") : " + str(prix[4]) + " $", 1, BLACK)
        screen.blit(gold_max, (100, 360))

        drop_rate = pokemon_35.render("Chance de trouver une pièce +20% (Actuel : " + str(int(upgrades[5]*100)) + "%) : " + str(prix[5]) + " $", 1, BLACK)
        screen.blit(drop_rate, (100, 420))

        invu_max = pokemon_35.render("Augmente le temps d'invulnérabilité : "+ str(prix[6]) + " $", 1, BLACK)
        screen.blit(invu_max, (100, 480))

        max_jump = pokemon_35.render("Augmente la hauteur maximum du saut : " + str(prix[7]) + " $", 1, BLACK)
        screen.blit(max_jump, (100, 540))

        nbr_jmp = pokemon_35.render("Nombre de sauts + 1 (Actuel : " + str(upgrades[8]) + ") : " + str(prix[8]) + " $", 1, BLACK)
        screen.blit(nbr_jmp, (100, 600))

        perc = pokemon_35.render("Les balles passent à travers les ennemis : " + str(prix[9]) + " $", 1, BLACK)
        screen.blit(perc, (100, 660))

        for event in pygame.event.get():



            if event.type == pygame.QUIT:
                sys.exit()
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_DOWN:
                    curseur += 1
                    pos_curseur[1] += 60
                    if curseur == len(upgrades):
                        curseur = 0
                        pos_curseur = [80, 100]

                elif event.key == pygame.K_UP:
                    curseur -= 1
                    pos_curseur[1] -= 60
                    if curseur == -1:
                        curseur = len(upgrades) - 1
                        pos_curseur = [80, 100 + (60*(len(upgrades) -1))]

                if event.key == pygame.K_g:
                    return upgrades, prix, argent, current_health

                if event.key == pygame.K_SPACE:
                    if argent >= prix[curseur]:
                        argent -= prix[curseur]
                        if curseur == 0:
                            upgrades[0] += 2
                            prix[0] = new_prix(prix[0])
                        elif curseur == 1:
                            upgrades[1] += 4
                            current_health += 4
                            prix[1] = new_prix(prix[1])
                        elif curseur == 2:
                            upgrades[2] += 2
                            prix[2] = new_prix(prix[2])
                        elif curseur == 3:
                            upgrades[3] += 1
                            prix[3] = new_prix(prix[3])
                        elif curseur == 4:
                            upgrades[4] += 1
                            prix[4] = new_prix(prix[4])
                        elif curseur == 5:
                            upgrades[5] += 0.2
                            prix[5] = new_prix(prix[5])
                        elif curseur == 6:
                            upgrades[6] += 6
                            prix[6] = new_prix(prix[6])
                        elif curseur == 7:
                            upgrades[7] += 60
                            prix[7] = new_prix(prix[7])
                        elif curseur == 8:
                            upgrades[8] += 1
                            prix[8] = new_prix(prix[8])
                        elif curseur == 9:
                            upgrades[9] = True
                            prix[9] = new_prix(prix[9])
        health_bars(upgrades[1], current_health)
        pygame.draw.circle(screen, YELLOW, (money_pos[0], money_pos[1]), 25, 25)
        pygame.draw.circle(screen, YELLOW, (money_pos[0]+ 256, money_pos[1]), 25, 25)
        label_money = pokemon_35.render("$",1, BLACK)
        screen.blit(label_money, (money_pos[0]-6, money_pos[1]-12))
        screen.blit(label_money, (money_pos[0] + 250, money_pos[1] - 12))
        label_current_money = arial.render(str(argent), 1, BLACK)
        screen.blit(label_current_money, (money_pos[0] + 100, money_pos[1] - 34))
        pygame.display.update()


def new_prix(prix):
    return prix + (prix//3) + 1

while not game_over:
    jump_left = NOMBRE_JMP
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            sys.exit()
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_LEFT:
                gauche = True
            elif event.key == pygame.K_RIGHT:
                droite = True
            if event.key == pygame.K_UP and jump_left > 0:
                up = True
                jump = player_pos[1] - MAX_JUMP
                jump_left -=1
            if event.key == pygame.K_SPACE:
                bullets.append([player_pos[0], player_pos[1], direction, True])
            if event.key == pygame.K_s and LEVEL_DONE:
                upgrades, prix, ARGENT, PLAYER_HP = shop(upgrades, ARGENT, prix, PLAYER_HP)
                Shop_Done = True
                SPEED = upgrades[0]
                PLAYER_MAX_HP = upgrades[1]
                PLAYER_ATK = upgrades[2]
                GOLD_MIN = upgrades[3]
                GOLD_MAX = upgrades[4]
                DROP_RATE = upgrades[5]
                INVU_MAX = upgrades[6]
                MAX_JUMP = upgrades[7]
                NOMBRE_JMP = upgrades[8]
                BALLES_PERCANTES = upgrades[9]


        if event.type == pygame.KEYUP:
            if event.key == pygame.K_LEFT:
                gauche = False
            if event.key == pygame.K_RIGHT:
                droite = False
            if event.key == pygame.K_UP:
                up = False

    if gauche == True:
        player_pos[0] -= SPEED
        direction = 'g'
    if droite == True:
        player_pos[0] += SPEED
        direction = 'd'
    if player_pos[1] >= jump:
        if up == True:
            player_pos[1] -= SPEED
        elif up == False and player_pos[1] < 800 - player_size:
            player_pos[1] +=10
            if player_pos[1] >= 800 - player_size - 10:
                jump_left = NOMBRE_JMP
    if player_pos[1] == jump:
        player_pos[1] += 10
        up = False


    clock.tick(30)

    screen.fill(WHITE)



    for i in range(len(bullets)):
        if bullets[i][0] >= 0 and bullets[i][0] <= largeur and (bullets[i][3] or BALLES_PERCANTES):
            pygame.draw.rect(screen, BLACK,(bullets[i][0],bullets[i][1], bullet_size, bullet_size))
            if bullets[i][2] == 'g':
                bullets[i][0] -= 20
            elif bullets[i][2] == 'd':
                bullets[i][0] += 20
    bullets, ennemies = collisions_bullets(bullets,ennemies,bullet_size,ennemies_size)
    if len(ennemies) < nombre_ennemies:
        if spawn(LVL):
            ennemies.append(new_ennemies())
    ennemies_presents = False
    if len(ennemies) == 0:
        ennemies.append(new_ennemies())
    for i in ennemies:
        if i[2] > 0 :
            ennemies_presents = True
    if not ennemies_presents and len(ennemies) < nombre_ennemies:
        ennemies.append(new_ennemies())
    ennemies = ennemies_movements(player_pos, ennemies)
    draw_ennmemies(ennemies)

    for i in range(len(ennemies)):
        if ennemies[i][2] <= 0 and ennemies[i][4] == True:
            drop = random.random()
            if DROP_RATE >= drop:
                money.append([ennemies[i][0], ennemies[i][1], True])
            ennemies[i][4] = False
    money, ARGENT_TEMP = take_money(player_pos,money, player_size, money_size, GOLD_MIN, GOLD_MAX)
    ARGENT += ARGENT_TEMP
    money = argent(money)
    if collisions_player(player_pos,ennemies,player_size, ennemies_size) and compteur_invu == 0:
        compteur_invu = INVU_MAX
        PLAYER_HP -= ENNEMIES_ATK
    if PLAYER_HP <= 0:
        death(player_pos, player_size)
        sys.exit()
    if compteur_invu > 0:
        compteur_invu -= 1
        pygame.draw.rect(screen, GREY, (player_pos[0], player_pos[1], player_size, player_size))
    else:
        pygame.draw.rect(screen,BLACK,(player_pos[0], player_pos[1],player_size,player_size))

    label_LVL = pokemon_35.render("NIVEAU : " + str(LVL), 1, BLACK)
    screen.blit(label_LVL, (largeur-250, hauteur - 880))

    ennemies_left = nombre_ennemies
    for i in ennemies:
        if i[2] <= 0:
            ennemies_left -= 1
    label_ENNEMIES = pokemon_35.render("ENNEMIES : " + str(ennemies_left), 1, BLACK)
    screen.blit(label_ENNEMIES, (largeur - 250, hauteur - 835))

    label_MONEY = pokemon_35.render("ARGENT : " + str(ARGENT), 1, BLACK)
    screen.blit(label_MONEY, (largeur - 250, hauteur - 790))

    health_bars(PLAYER_MAX_HP, PLAYER_HP)
    sol()
    if ennemies_left == 0:
        label_CLEARED = arial.render("LEVEL CLEARED", 1, BLACK)
        screen.blit(label_CLEARED, (largeur - 985, hauteur - 580))
        label_SHOP = pokemon_35.render("APPUYEZ SUR S POUR OUVRIR LE MAGASIN", 1, BLACK)
        screen.blit(label_SHOP,(largeur - 1020, hauteur - 500))
        LEVEL_DONE = True
        if Shop_Done == True:
            money = []
            ennemies = []
            LVL += 1
            nombre_ennemies = 8 + LVL*2 + LVL//2
            bullets = []
            ENNEMIES_SPEED_MAX += 1 + LVL//5
            ENNEMIES_ATK += LVL//2
            ENNEMIES_HP += 1 + LVL//2
            LEVEL_DONE = False
            Shop_Done = False
    pygame.display.update()

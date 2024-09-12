"""
Arthur Nanson, 000476431
Nouvelle IA qui joue en fonction du nombre de cases possedees par l'adversaire dans chaque lignes et chaques colonnes
"""

from abc import ABCMeta, abstractmethod
import random
import copy


from PyQt5.QtGui import QMouseEvent

from utils import *
from board import Action, Board
from players import AIPlayer
class TournoiAIPlayer(AIPlayer):
    '''
    Specialised form of AI Player using minimax algorithm.
    '''
    def __init__(self, player, board):
        super().__init__(player, board)

    def play(self, event=None):

        '''
        Play the best action according to the minimax tree.
        :param event: ignored
        '''

        action = self.minimax()[0]
        self.board.act(action, self.player)

        self.new_board = copy.deepcopy(self.board.grid)#sert a copier le board et l'offset apres le coup pour pouvoir les mettre dans les coups deja joues
        self.new_offset = copy.deepcopy(self.board.offset)
        self.board.played_boards.append((self.new_board, self.new_offset))

    def minimax(self, depth=2, maximize=True, penalty=0):
        '''
        Minimax tree exploration.
        :param depth:    maximum depth to explore in the tree
        :param maximize: True if selecting action maximizing score (i.e. if selecting the current player's move)
                         and False otherwise (i.e. if selecting the other player's move)
        :param penalty:  integer representing the time induced penalty
        '''
        if depth == 0:
            score = self.objective_function()
            return (None, score-penalty)
        if maximize:
            best_score = -INF
            player = self.player
        else:
            best_score = +INF
            player = self.other_player
        best_actions = []

        valid_actions = self.board.get_valid_actions(player)
        for action in valid_actions:
            self.board.act(action, player)
            winner = self.board.winner()
            if len(winner) == 0:

                score = self.minimax(depth-1, not maximize, penalty+1)[1]
            else:
                score = WIN-penalty if winner.pop() == self.player else LOSS+penalty
                score -= penalty
            self.board.undo()

            if score > best_score:
                if maximize:
                    best_score = score
                    best_actions = [(action, score)]
            elif score < best_score:
                if not maximize:
                    best_score = score
                    best_actions = [(action, score)]
            else:
                best_actions.append((action, score))
        return random.choice(best_actions)


    def objective_function(self):
        """
        Fonction qui est l'objectif de l'IA, en l'occurence avoir le plus de cases possibles.
        Cette IA veut avoir plus de cases que son adversaire dans toutes les lignes dans un premier temps.
        Après, il veut plus de cases que son adversaire dans toutes les colonnes.
        Le premier critere vaut pour plus que le deuxieme.
        """
        lignes_ennemies = [0,0,0,0]
        score_ligne = 0
        lignes = [0,0,0,0]
        score_colonne = 0
        colonnes_ennemies = [0,0,0,0]
        colonnes = [0,0,0,0]

        for i in range(4):
            for j in self.board.grid[i]:
                if j != 0:
                    if j == self.player:
                        lignes[i] += 1
                    else:
                        lignes_ennemies[i] += 1
            if lignes[i] > lignes_ennemies[i]:#si on a plus de cases on augmente le score, sinon on le diminue
                score_ligne += 0.2
            elif lignes_ennemies[i] > lignes[i]:
                score_ligne -= 0.2

            for k in self.board.grid:
                if k[i] != 0:
                    if k[i] == self.player:
                        colonnes[i] += 1
                    else:
                        colonnes_ennemies[i] += 1
            if colonnes[i] > colonnes_ennemies[i]:#si on a plus de cases dans la colonne on augmente le score, sinon on le diminue
                score_colonne += 0.2
            elif colonnes_ennemies[i] > colonnes[i]:
                score_colonne -= 0.2

        score = 0.7*score_ligne + 0.3*score_colonne#la ligne vaut pour plus que la colonne car on ne prend pas en compte le decalage

        return score


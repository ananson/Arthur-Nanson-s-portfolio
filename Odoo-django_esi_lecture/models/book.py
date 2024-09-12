from odoo import models, fields, api


class Book(models.Model):
    _name = 'esi_lecture.book'
    _description = 'A virtual library'
    
    
    nom = fields.Char(string="Titre", required=True)
    my_description = fields.Text(string="Description")
    couverture = fields.Binary(string = "Couverture")
    parution = fields.Date(string = "Date de parution")
    nombre_pages = fields.Integer(string = "Nombre de pages")
    auteur_id = fields.Many2many('res.partner', ondelete='cascade', string = "Auteur")
    like_number = fields.Integer('Nombre de like', compute='_like_number')
    like_user_id = fields.Many2many('res.users')
    is_liked = fields.Text(compute="_is_book_liked")

    @api.depends('like_number', 'like_user_id')
    def _like_number(self):
        for i in self:
            i.like_number = len(i.like_user_id)
    
    def like_book(self):
        current_user = self.env.user
        if current_user not in self.like_user_id:
            self.write({'like_user_id': [(4, current_user.id)]})
        else:
            self.write({'like_user_id': [(3, current_user.id)]})

    def _is_book_liked(self):
        if self.env.user in self.like_user_id:
            self.is_liked = "Unlike this book"
        else:
            self.is_liked = "Like this book"   

    _sql_constraints = [
       ('parution_check',
         'CHECK(parution < CURRENT_DATE)',
         "La date de publication doit être passée"),

        ('nom_unique',
         'UNIQUE(nom)',
         "Ce titre est déjà dans la bibliothèque"),
        
        ('page_check',
         'CHECK(nombre_pages > 0)',
         "Le nombre de page doit être plus grand que 0")
    ]
    
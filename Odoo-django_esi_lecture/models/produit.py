from odoo import models, fields

class Produit(models.Model):
    _inherit = 'product.template'
    livre = fields.Many2many('esi_lecture.book', string="Livre")


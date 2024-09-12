from odoo import models, fields


class Partner(models.Model):
    _inherit = 'res.partner'
    auteur = fields.Boolean("Auteur", default=False)
    

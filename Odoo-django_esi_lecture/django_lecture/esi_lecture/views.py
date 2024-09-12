import json
from django.shortcuts import render, redirect

# Create your views here.
from .forms import MyForm
import subprocess
import xmlrpc.client
from django.http import JsonResponse
from django.contrib.auth.decorators import login_required

def my_view(request):
    error_message = None
    if request.method == 'POST':
        form = MyForm(request.POST)
        if form.is_valid():
            #call to odoo module
            url = "http://localhost:8069"
            db = "dev01"

            common = xmlrpc.client.ServerProxy('{}/xmlrpc/2/common'.format(url))
            username = form.cleaned_data['username']
            password = form.cleaned_data['password']
            uid = common.authenticate(db, username, password, {})
            if uid:
                request.session['odoo_url'] = url
                request.session['odoo_db'] = db
                request.session['odoo_username'] = username
                request.session['odoo_password'] = password
                request.session['odoo_uid'] = uid
                return redirect('home')
            else:
                error_message = "The credentials you entered do not match."
    else:
        form = MyForm()

    return render(request, 'esi_lecture/form.html', {'form': form, 'error_message': error_message})

def search_books(title, request):
    common = xmlrpc.client.ServerProxy('{}/xmlrpc/2/common'.format(request.session['odoo_url']))
    models = xmlrpc.client.ServerProxy('{}/xmlrpc/2/object'.format(request.session['odoo_url']))
    books = models.execute_kw("dev01", request.session['odoo_uid'], request.session['odoo_password'], 'esi_lecture.book', 'search_read', [[['nom', 'ilike', title]]])
    username = request.session['odoo_username']

    # Retrieve the user ID based on the username
    user = models.execute_kw(
        "dev01", 
        request.session['odoo_uid'], 
        request.session['odoo_password'], 
        'res.users', 
        'search_read', 
        [[['login', '=', username]]],
        {'fields': ['id'], 'limit': 1}
    )

    user_id = user[0]['id'] if user else None  # odoo user id to check if they already liked each books
    request.session['odoo_user_id'] = user_id
    
    for book in books:
        if user_id and user_id in book['like_user_id']:
            book['liked_by_user'] = True
        else:
            book['liked_by_user'] = False

    return books

def home(request):
    results = None
    if request.method == "POST":
        title = request.POST.get('book_search', '')
        results = search_books(title, request)

    return render(request, 'esi_lecture/home.html', {
        'username': request.session['odoo_username'],
        'results': results
    })


def toggle_like(request):
    if request.method == 'POST':
        data = json.loads(request.body)
        book_id = data.get('book_id')
        liked_by_user = data.get('liked_by_user') == 'True'  # Convert string to boolean

        common = xmlrpc.client.ServerProxy('{}/xmlrpc/2/common'.format(request.session['odoo_url']))
        models = xmlrpc.client.ServerProxy('{}/xmlrpc/2/object'.format(request.session['odoo_url']))
        book_data = models.execute_kw(
            "dev01",
            request.session['odoo_uid'],
            request.session['odoo_password'],
            'esi_lecture.book',
            'read',
            [int(book_id)],
            {'fields': ['like_number']}
        )
        current_like_number = book_data[0]['like_number']
        if liked_by_user:
            # Unlike the book
            models.execute_kw(
                "dev01",
                request.session['odoo_uid'],
                request.session['odoo_password'],
                'esi_lecture.book',
                'write',
                [[int(book_id)], {'like_number': current_like_number - 1}]
            )
            models.execute_kw(
                "dev01",
                request.session['odoo_uid'],
                request.session['odoo_password'],
                'esi_lecture.book',
                'write',
                [[int(book_id)], {'like_user_id': [(3, request.session['odoo_user_id'])]}]
            )
        else:
            # Like the book
            models.execute_kw(
                "dev01",
                request.session['odoo_uid'],
                request.session['odoo_password'],
                'esi_lecture.book',
                'write',
                [[int(book_id)], {'like_number': current_like_number + 1}]
            )
            models.execute_kw(
                "dev01",
                request.session['odoo_uid'],
                request.session['odoo_password'],
                'esi_lecture.book',
                'write',
                [[int(book_id)], {'like_user_id': [(4, request.session['odoo_user_id'])]}]
            )

        return JsonResponse({'success': True})

    return JsonResponse({'success': False})

from django.urls import path
from . import views

urlpatterns = [
    path('', views.my_view, name='my_view'),
    path('login/', views.my_view, name='login'),
    path('home/', views.home, name='home'),
    path('toggle_like/', views.toggle_like, name='toggle_like'),
]

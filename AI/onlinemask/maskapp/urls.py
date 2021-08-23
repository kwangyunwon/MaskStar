from django.conf.urls import url, include
from django.urls import path

from . import views

urlpatterns = [
    #path('', views.home, name="home"),
    path('sticker', views.sticker_video, name="sticker"),
    path('record_video', views.record_video, name="record_video"),
    path('record_stop', views.record_stop, name="record_stop"),
    path('stop', views.stop, name="stop"),
    path('audio_save',views.audio_save, name="audio_save"),
    path('img_update',views.img_update, name="img_update"),
    path('endRecord',views.endRecord, name="endRecord"),
    path('record_delete',views.record_delete, name="record_delete"),
    ]
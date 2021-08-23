from django.db import models

# Create your models here.
class Profile(models.Model):
    title = models.CharField(max_length=255)
    audio = models.FileField()
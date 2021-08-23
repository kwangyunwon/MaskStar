from django.shortcuts import render
from django.http.response import JsonResponse
from django.views.decorators import gzip
from django.http import StreamingHttpResponse
from django.core.files.storage import default_storage
from django.views.decorators.csrf import csrf_exempt
from django.core.files.base import ContentFile
from django.conf import settings
import os
import subprocess
import requests
from maskapp.mask import Sticker

# https://blog.miguelgrinberg.com/post/video-streaming-with-flask/page/8
def home(request):
    context = {}
    return render(request, "home.html", context)

def gen(camera):
    while True:
        frame = camera.get_frame()
        yield(b'--frame\r\n'
              b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')

sticker = Sticker()

@gzip.gzip_page
def sticker_video(request):
    try:
        sticker.create()
        return StreamingHttpResponse(gen(sticker), content_type="multipart/x-mixed-replace;boundary=frame")
    except:  # This is bad! replace it with proper handling
        print("에러입니다...")
        pass

@csrf_exempt
def record_video(request):
    try:
        filename = './media/' + request.POST['filename'] + "_video.mp4"
        print(filename)
        sticker.record_start(filename)
        return JsonResponse({'message':'녹화 중'})
    except:
        return JsonResponse({'message':'녹화 실패'})

def record_stop(request):
    try:
        sticker.record_stop()
        return JsonResponse({'message':'녹화 종료'})
    except:
        return JsonResponse({'message':'종료 실패'})

@csrf_exempt
def record_delete(request):
    try:
        flag = request.POST['flag']
        print(flag)
        if(flag=="1"):
            filename = request.POST['filename']
            print(filename)
        else:   
            sticker.record_stop()
            filename = request.POST['filename'] + "_video.mp4"
            print(filename)
        if os.path.isfile(filename):
            os.remove(filename)
        return JsonResponse({'message':'녹화 종료'})
    except:
        return JsonResponse({'message':'종료 실패'})
def stop(request):
    try:
        sticker.update_stop()
        return JsonResponse({'message':'종료'})
    except:
        return JsonResponse({'message':'종료 실패'})

#####
import boto3
from botocore.client import Config

ACCESS_KEY_ID = 'AKIATQU6AIZ5KXL6SG7L' #s3 관련 권한을 가진 IAM계정 정보
ACCESS_SECRET_KEY = 'W/Gkl3y7nVj14Y4qUqtASlBnSANsh3KKAlOn95a2'
BUCKET_NAME = 'maskstar.kr'

s3 = boto3.client('s3')
#####


@csrf_exempt
def audio_save(request):
    filename = request.POST['filename'] + "_audio.wav"
    audio =  request.FILES.get('data')
    path = default_storage.save(filename, ContentFile(audio.read()))

    vfname = 'media/' + request.POST['filename'] + "_video.mp4"
    ofname = 'media/' + filename
    outname = 'media/' + request.POST['filename'] + "_out.mp4"

    cmd = "ffmpeg -i "+ vfname +" -i "+ ofname +" -c:v copy -c:a aac " +  outname
    subprocess.call(cmd, shell=True)

    if os.path.isfile(vfname):
        os.remove(vfname)
    if os.path.isfile(ofname):
        os.remove(ofname)


   # 업로드할 S3 버킷 
    s3 = boto3.resource(
        's3',
        aws_access_key_id=ACCESS_KEY_ID,
        aws_secret_access_key=ACCESS_SECRET_KEY,
        config=Config(signature_version='s3v4')
    )

    data = open(outname, 'rb')

    s3.Bucket(BUCKET_NAME).put_object(
        Key=outname, Body=data, ContentType='video/mp4')

    data.close()
    
    if os.path.isfile(outname):
        os.remove(outname)

    return JsonResponse({'message':'sucess'})

def img_update(request):
    filename = "img/"+ request.GET['filename'] +".png"
    sticker.update_img(filename)

    return JsonResponse({'message':'sucess'})

def endRecord(request):
    sticker.update_stop()
    return JsonResponse({'message':'sucess'})


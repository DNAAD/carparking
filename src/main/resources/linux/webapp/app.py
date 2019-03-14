from flask import Flask
from flask import render_template
from urllib import parse,request
from urllib import error
import socket

def isNetOK(testserver):
    s=socket.socket()
    s.settimeout(3)
    try:
        status = s.connect_ex(testserver)
        if status == 0:
            s.close()
            return True
        else:
            return False
    except Exception as e:
        return False

def isNetChainOK(testserver=('www.baidu.com',443)):
    isOK = isNetOK(testserver)
    return isOK


def isNetUSAOK(testserver=('www.google.com',443)):
    isOK = isNetOK(testserver)
    return isOK

def isNetYouTubeOK(testserver=('www.youtube.com',443)):
    isOK = isNetOK(testserver)
    return isOK



app = Flask(__name__)

@app.route('/')
def index():
    user = {'username': 'Miguel'}

    chinanet = isNetChainOK()
    user['isNetChainOK']=chinanet
    print (chinanet)

    usanet = isNetUSAOK()
    user['isNetUSAOK']=usanet
    print (usanet)


    youtubenet = isNetYouTubeOK()
    user['isNetYouTubeOK']=youtubenet
    print (youtubenet)



    try:
     response = request.urlopen('http://localhost/info')
     html = response.read()
     user['ret']=html
     user['ready']=True
     user['url']="http://192.168.43.67/report/index/STORAGE00000033"
     #print(html)
    except error.URLError as e:
     print(e.reason)
     print(e)
     if e.reason  =="[Errno 111] Connection refused": 
      print("fuck")
     else:
      user['ret']="success"

    return render_template('index.html', title='Home', user=user)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')

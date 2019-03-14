import requests
import urllib.request
import tempfile
import cv2
api_host = 'http://localhost:8003/upload'
headers = {'Content-Type' : 'image/jpeg'}
image_url = 'https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo_top_86d58ae1.png'

img_file = urllib.request.urlopen(image_url)

img_data = requests.get(image_url).content
with open('image_name.jpg', 'wb') as handler:
    handler.write(img_data)




files = {'file': open('image_name.jpg', 'rb')}
requests.post(api_host, files=files)

# requests.post(api_host, files={'upload_file': temp})
# response = requests.post(api_host,files={'upload_file': temp}, data=img_file.read(), headers=headers, verify=False)

cap = cv2.VideoCapture(0)
count = 0
while(1):
    # get a frame
    ret, frame = cap.read()
    # show a frame
    cv2.imshow("capture", frame)
    if count%100 == 0:
        name = "frame%d.jpg" % count
        cv2.imwrite(name, frame)     # save frame as JPEG file
        print('Read a new frame: ',count)
        files = {'file': open(name, 'rb')}
        requests.post(api_host, files=files)

    count += 1



    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
cap.release()
cv2.destroyAllWindows()

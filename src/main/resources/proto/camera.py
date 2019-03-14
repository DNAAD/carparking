from __future__ import print_function

import numpy as np

import pyzbar.pyzbar as pyzbar

import cv2

import grpc_chanel
import test_pb2_grpc, test_pb2

camera = cv2.VideoCapture(0)

# ret=camera.set(3,1280)
# ret=camera.set(4,720)
# res, frame = camera.read()
# y_size = frame.shape[0]
# x_size = frame.shape[1]

# 导入CNN分类模型
# model = load_model('model//weights.h5')

bs = cv2.createBackgroundSubtractorMOG2(detectShadows=True)  # 定义MOG2
history = 20  # MOG2训练使用的帧数
frames = 0  # 当前帧数
counter = 0  # 当前目标id

# cv2.namedWindow("detection", cv2.WINDOW_NORMAL)


# Main
if __name__ == '__main__':

    # Read image
    # im = cv2.imread('qrcode.jpg')
    # grpc_chanel.init()
    cap = cv2.VideoCapture(0)

    # ret=cap.set(3,1280)
    # ret=cap.set(4,720)
    while (1):
        # get a frame
        # ret, frame = cap.read()
        res, frame = camera.read()

        # cv2.imshow("detection", frame)
        # continue
        print("ddd")

        if not res:
            break
        # 使用前20帧训练MOG2
        fg_mask = bs.apply(frame)

        if frames < history:
            frames += 1
            continue

        th = cv2.threshold(fg_mask.copy(), 244, 255, cv2.THRESH_BINARY)[1]
        erodeth = cv2.erode(th, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (3, 3)), iterations=2)
        dilated = cv2.dilate(erodeth, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (8, 3)), iterations=2)
        # 获得目标位置
        image, contours, hier = cv2.findContours(dilated, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

        for c in contours:
            x, y, w, h = cv2.boundingRect(c)
            if cv2.contourArea(c) > 3000:
                # 提取目标
                # img = frame[y: y + h, x: x + w, :]
                # rimg = cv2.resize(img, (64, 64), interpolation=cv2.INTER_CUBIC)
                # image_data = np.array(rimg, dtype='float32')
                # image_data /= 255.
                # roi = np.expand_dims(image_data, axis=0)
                # (x, y, w, h) = cv2.boundingRect(c)
                cv2.rectangle(frame, (x, y), (x + w, y + h), (255, 255, 0), 2)

        cv2.imshow("capture", frame)
        cv2.imshow("dilated", dilated)
        cv2.imshow("erodeth", erodeth)
        cv2.imshow("th", th)
        # show a frame

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
    cap.release()
    cv2.destroyAllWindows()

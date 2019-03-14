import os
import logging
import logging.handlers
import random

import numpy as np
#import skvideo.io
import cv2
import matplotlib.pyplot as plt

#import utils
# without this some strange errors happen
cv2.ocl.setUseOpenCL(False)
random.seed(123)

# ============================================================================
IMAGE_DIR = "./out"
VIDEO_SOURCE = "input.mp4"
SHAPE = (720, 1280)  # HxW
# ============================================================================


def train_bg_subtractor(inst, cap, num=500):
    '''
        BG substractor need process some amount of frames to start giving result
    '''
    print ('Training BG Subtractor...')
    i = 0
    while True :
        
        ret, frame = cap.read()
        cv2.imshow("capture_666", frame)
    #for frame in cap:
        inst.apply(frame, None, 0.001)
        i += 1
        if i >= num:
            break
           # return cap
def filter_mask(img):

    kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (2, 2))

    # Fill any small holes
    closing = cv2.morphologyEx(img, cv2.MORPH_CLOSE, kernel)
    # Remove noise
    opening = cv2.morphologyEx(closing, cv2.MORPH_OPEN, kernel)

    # Dilate to merge adjacent blobs
    dilation = cv2.dilate(opening, kernel, iterations=2)

    # threshold
    th = dilation[dilation < 240] = 0

    return dilation


def main():
    log = logging.getLogger("main")

    # creting MOG bg subtractor with 500 frames in cache
    # and shadow detction
    bg_subtractor = cv2.createBackgroundSubtractorMOG2(
        history=500, detectShadows=True)

    # Set up image source
    # You can use also CV2, for some reason it not working for me
    cap = cv2.VideoCapture(0) #skvideo.io.vreader(VIDEO_SOURCE)

    # skipping 500 frames to train bg subtractor
    train_bg_subtractor(bg_subtractor, cap, num=500)

    frame_number = -1
    while True :
        ret, frame = cap.read()
        #if not frame:
        #    log.error("Frame capture failed, stopping...")
         #   break

        frame_number += 1

        #utils.save_frame(frame, "./out/frame_%04d.png" % frame_number)

        fg_mask = bg_subtractor.apply(frame, None, 0.001)
        filter_frame = filter_mask(fg_mask)
        cv2.imshow("capture",filter_frame )
        #utils.save_frame(frame, "./out/fg_mask_%04d.png" % frame_number)
    #for frame in cap:

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
    cap.release()
    cv2.destroyAllWindows()


   
# ============================================================================

if __name__ == "__main__":
    #log = utils.init_logging()

    if not os.path.exists(IMAGE_DIR):
        #log.debug("Creating image directory `%s`...", IMAGE_DIR)
        os.makedirs(IMAGE_DIR)

    main()

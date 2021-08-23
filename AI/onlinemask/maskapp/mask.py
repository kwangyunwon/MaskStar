import cv2
import dlib
import numpy as np
import threading
import time

class Sticker(object):
    def __init__(self):
        self.scaler = 1.0
        self.detector = dlib.get_frontal_face_detector()
        self.predictor = dlib.shape_predictor('shape_predictor_68_face_landmarks.dat')
        self.update_thread = threading.Thread(target=self.update, args=())
        self.imgfile = 'img/dog.png'
        self.overlay = cv2.imread(self.imgfile, cv2.IMREAD_UNCHANGED)
        self.imgupdate = False

    def create(self):
        self.video = cv2.VideoCapture(0)
        (self.grabbed, self.frame) = self.video.read()
        self.result = self.frame.copy()
        self.update_thread = threading.Thread(target=self.update, args=())
        self.update_check = True
        self.update_thread.start()

    def __del__(self):
        self.update_check = False
        self.video.release()

    def get_frame(self):
        image = self.result
        _, jpeg = cv2.imencode('.jpg', image)
        return jpeg.tobytes()

    def update(self):
        
        face_roi = []
        face_sizes = []

        while self.update_check:
            (self.grabbed, self.frame) = self.video.read()

            if not self.grabbed:
                break
            
            # resize frame
            img = cv2.resize(self.frame, (int(self.frame.shape[1] * self.scaler), int(self.frame.shape[0] * self.scaler)))
            ori = self.frame.copy()
            
            try:
                self.ori_copy = ori.copy()
                
                # find faces
                if len(face_roi) == 0:
                    faces = self.detector(img, 1)
                else:
                    roi_img = img[face_roi[0]:face_roi[1], face_roi[2]:face_roi[3]]
                    # cv2.imshow('roi', roi_img)
                    faces = self.detector(roi_img)

                # no faces
                if len(faces) == 0:
                    self.result = ori
                else:
                    for face in faces:
                        if len(face_roi) == 0:
                            dlib_shape = self.predictor(img, face)
                            shape_2d = np.array([[p.x, p.y] for p in dlib_shape.parts()])
                        else:
                            dlib_shape = self.predictor(roi_img, face)
                            shape_2d = np.array([[p.x + face_roi[2], p.y + face_roi[0]] for p in dlib_shape.parts()])

                        for s in shape_2d:
                            cv2.circle(img, center=tuple(s), radius=1, color=(255, 255, 255), thickness=2, lineType=cv2.LINE_AA)

                        # compute face center
                        center_x, center_y = np.mean(shape_2d, axis=0).astype(np.int)

                        # compute face boundaries
                        min_coords = np.min(shape_2d, axis=0)
                        max_coords = np.max(shape_2d, axis=0)

                        # draw min, max coords
                        cv2.circle(img, center=tuple(min_coords), radius=1, color=(255, 0, 0), thickness=2, lineType=cv2.LINE_AA)
                        cv2.circle(img, center=tuple(max_coords), radius=1, color=(255, 0, 0), thickness=2, lineType=cv2.LINE_AA)

                        # compute face size
                        face_size = max(max_coords - min_coords)
                        face_sizes.append(face_size)
                        if len(face_sizes) > 10:
                            del face_sizes[0]
                        mean_face_size = int(np.mean(face_sizes) * 1.8)

                        # compute face roi
                        face_roi = np.array([int(min_coords[1] - face_size / 2), int(max_coords[1] + face_size / 2), int(min_coords[0] - face_size / 2), int(max_coords[0] + face_size / 2)])
                        face_roi = np.clip(face_roi, 0, 10000)

                        mfs = (mean_face_size, mean_face_size)
                        x = center_x + 8
                        y = center_y - 25

                    
                        if ori.shape[2] == 3:
                            ori = cv2.cvtColor(ori, cv2.COLOR_BGR2BGRA)

                        img_to_overlay_t = cv2.resize(self.overlay, mfs)

                        b, g, r, a = cv2.split(img_to_overlay_t)

                        mask = cv2.medianBlur(a, 5)

                        h, w, _ = img_to_overlay_t.shape
                        roi = ori[int(y-h/2):int(y+h/2), int(x-w/2):int(x+w/2)]

                        img1_bg = cv2.bitwise_and(roi.copy(), roi.copy(), mask=cv2.bitwise_not(mask))
                        img2_fg = cv2.bitwise_and(img_to_overlay_t, img_to_overlay_t, mask=mask)

                        ori[int(y-h/2):int(y+h/2), int(x-w/2):int(x+w/2)] = cv2.add(img1_bg, img2_fg)

                        self.result = cv2.cvtColor(ori, cv2.COLOR_BGRA2BGR)

                        if(self.imgupdate):
                            self.overlay = cv2.imread(self.imgfile, cv2.IMREAD_UNCHANGED)
                            self.imgupdate = False
                    
            except Exception:
                self.result = self.ori_copy
    
    def record_start(self, filename):
        self.video_filename = filename
        self.fps = 6               # fps should be the minimum constant rate at which the camera can
        self.fourcc = "H264"
        self.frameSize = (640,480)
        self.video_writer = cv2.VideoWriter_fourcc(*self.fourcc)
        self.video_out = cv2.VideoWriter(self.video_filename, self.video_writer, self.fps, self.frameSize)
        self.record_Thread = threading.Thread(target=self.record, args=())
        self.check = True
        self.record_Thread.start()

    def record(self):
        while self.check:
            self.video_out.write(self.result)
            time.sleep(0.15)

    def record_stop(self):
        self.check = False
        self.video_out.release()

    def update_stop(self):
        self.update_check = False
        self.video.release()
        cv2.destroyAllWindows()

    def update_img(self, filename):
        self.imgfile = filename
        self.imgupdate = True

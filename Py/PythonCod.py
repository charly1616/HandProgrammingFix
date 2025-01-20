import cv2
import mediapipe as mp
import pyautogui
import time
import sys
import os

mp_drawing = mp.solutions.drawing_utils
mp_hands = mp.solutions.hands

cap = cv2.VideoCapture(0)
hands = mp_hands.Hands()

mouse_control = True
move_interval = 0.1  # Intervalo de tiempo entre movimientos del mouse (segundos)
last_move_time = time.time()
two_fingers_start_time = 0
two_fingers_duration = 2.0  # Duración para considerar dos dedos levantados como clic izquierdo (segundos)

try:
    while True:
        ret, image = cap.read()

        if not ret:
            continue

        image = cv2.cvtColor(cv2.flip(image, 1), cv2.COLOR_BGR2RGB)
        results = hands.process(image)

        if results.multi_hand_landmarks:
            for hand_landmarks in results.multi_hand_landmarks:
                fingers_raised = 0

                finger_tips = [mp_hands.HandLandmark.INDEX_FINGER_TIP, mp_hands.HandLandmark.MIDDLE_FINGER_TIP,
                               mp_hands.HandLandmark.RING_FINGER_TIP, mp_hands.HandLandmark.PINKY_TIP]

                for tip in finger_tips:
                    tip_dist = hand_landmarks.landmark[tip].y - hand_landmarks.landmark[mp_hands.HandLandmark.INDEX_FINGER_PIP].y
                    if tip_dist < 0.05:
                        fingers_raised += 1

                    x, y = int(hand_landmarks.landmark[tip].x * image.shape[1]), int(hand_landmarks.landmark[tip].y * image.shape[0])
                    cv2.circle(image, (x, y), 5, (0, 0, 255), -1)

                cv2.putText(image, f"Dedos Levantados: {fingers_raised}", (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)

                if fingers_raised == 1:
                    if mouse_control and time.time() - last_move_time > move_interval:
                        index_x, index_y = hand_landmarks.landmark[mp_hands.HandLandmark.INDEX_FINGER_TIP].x, hand_landmarks.landmark[mp_hands.HandLandmark.INDEX_FINGER_TIP].y
                        middle_x, middle_y = hand_landmarks.landmark[mp_hands.HandLandmark.MIDDLE_FINGER_TIP].x, hand_landmarks.landmark[mp_hands.HandLandmark.MIDDLE_FINGER_TIP].y

                        screen_width, screen_height = pyautogui.size()
                        center_x = int((index_x + middle_x) * screen_width / 2)
                        center_y = int((index_y + middle_y) * screen_height / 2)

                        current_x, current_y = pyautogui.position()
                        smooth_x = current_x + 0.3 * (center_x - current_x)
                        smooth_y = current_y + 0.3 * (center_y - current_y)

                        pyautogui.moveTo(smooth_x, smooth_y)
                        last_move_time = time.time()

                elif fingers_raised == 3:
                    if mouse_control:
                        if two_fingers_start_time == 0:
                            two_fingers_start_time = time.time()
                        elif time.time() - two_fingers_start_time >= two_fingers_duration:
                            pyautogui.mouseDown()
                            mouse_control = False
                    else:
                        two_fingers_start_time = 0
                elif fingers_raised == 2:
                    mouse_control = True
                    pyautogui.click()
                elif fingers_raised == 4:
                    mouse_control = True
                    pyautogui.scroll(-150)
                elif fingers_raised == 0:
                    mouse_control = True
                    pyautogui.scroll(150)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break
finally:
    print("Liberando recursos...")
    if cap.isOpened():
        cap.release()
    cv2.destroyAllWindows()
    # Finalizar el programa completamente
    sys.exit(0)

import cv2


def captureFrame():
    """
    Test the ports and returns a tuple with the available ports and the ones that are working.
    """
    # GStreamer Pipeline to access the Raspberry Pi camera
    GSTREAMER_PIPELINE = 'nvarguscamerasrc ! video/x-raw(memory:NVMM), width=3280, height=2464, format=(string)NV12, framerate=21/1 ! nvvidconv flip-method=0 ! video/x-raw, width=960, height=616, format=(string)BGRx ! videoconvert ! video/x-raw, format=(string)BGR ! appsink wait-on-eos=false max-buffers=1 drop=True'

    #is_working = True
    #dev_port = 0
    #working_ports = []
    #available_ports = []
    #while is_working:
    camera = cv2.VideoCapture(GSTREAMER_PIPELINE)
    if not camera.isOpened():
        is_working = False
        print("Port %s is not working." %dev_port)
    else:
        is_reading, img = camera.read()
        w = camera.get(3)
        h = camera.get(4)
        if is_reading:
            print("Port %s is working and reads images (%s x %s)" %(dev_port,h,w))
            cv2.imwrite("/tmp/image.jpg", img)
            working_ports.append(dev_port)
        else:
            print("Port %s for camera ( %s x %s) is present but does not reads." %(dev_port,h,w))
            available_ports.append(dev_port)

    return available_ports,working_ports

list_ports()

'''
Created on 10 sty 2015

@author: michalsiatkowski
'''
import time
import logging

def timing(f):
    '''
        Timing annotation from StackOverflow
    '''
    def wrap(*args):
        time1 = time.time()
        ret = f(*args)
        time2 = time.time()
        logging.info('%s function took %0.3f ms' % (f.__name__, (time2 - time1) * 1000.0))
        return ret
    return wrap

def scale(value, top):
    '''
        scale the value of range 0-top
        to HSV green-red
    '''
    # python hsv has 0-1 range!
    # H = 0-120
    # h = 0-0.3
    s = 1
    v = 1
    h = ((0.3) / (top) * (value))
    # invert it
    h = 0.3 - h
    if(h < 0):
        h = 0
    return "%f,%f,%f" % (h, s, v), h

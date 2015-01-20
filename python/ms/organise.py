'''
Created on 16 sty 2015

@author: michalsiatkowski
'''
import os
import operator
import logging
import shutil
import re
import numpy
from matplotlib import pyplot


def organise_output(output):
    logging.info("Cleaning up : " + str(output))
    iters = '/ITERS/'
    
    dirs = [ (int((str(x)[3:])), x) for x in os.listdir(output+iters) if (not x.startswith('.'))]
    dirs = sorted(dirs, key=operator.itemgetter(0))
    
    number = str(dirs[-1][0])
    lastiterdir = dirs[-1][1]+'/'
    
    source1 = number+'.events.xml.gz'
    source2 = number+'.tripdurations.txt'
    dest1 = '/output_events.xml.gz'
    dest2 = '/output_tripdurations.txt'
    
    shutil.move(output+iters+lastiterdir+source1, output+dest1)
    shutil.move(output+iters+lastiterdir+source2, output+dest2)
    logging.info("Moved : " + str(source1) + " to " + str(dest1))
    logging.info("Moved : " + str(source2) + " to " + str(dest2))
    
    tripdurationLines = open(output+dest2).readlines()
    tripduration = tripdurationLines[-1]
    time = re.match( r'.*duration: (\d+\.\d+) seconds.*', tripduration)
    text_file = open(output+"/fitness.txt", "w")
    text_file.write(str(time.group(1)))
    text_file.close()
    logging.info("Created : " + str(output+"/fitness.txt"))
    
    shutil.rmtree(output+iters)
    shutil.rmtree(output+'/tmp/')
    logging.info("Removed : " + str(iters))
    logging.info("Removed : " + str('/tmp/'))
    
    os.remove(output+'/output_plans.xml.gz')
    logging.info("Removed : " + str('/output_plans.xml.gz'))
    
    pass

def organise_best(output):
    distance = open(os.path.join(output, 'fitness.txt'), "r")
    dist = distance.readline()
    distance.close()
    
    os.symlink(output, (output+'/../best'))
    
    root = output+'/../../'

    text_file = open((root+"fitness.txt"), "a+")
    text_file.write(dist)
    text_file.close()

    y1 = numpy.fromfile((root+"/fitness.txt"), sep="\n")
    v = numpy.fromfile((root+"/fitnessInitial.txt"), sep="\n")
    y2 = numpy.linspace(v[0], v[0], num=len(y1))
    
    pyplot.plot(y1)
    pyplot.plot(y2)
    
    pyplot.savefig(os.path.join(root, 'fitness.png'))
    pass

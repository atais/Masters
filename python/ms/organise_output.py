'''
Created on 16 sty 2015

@author: michalsiatkowski
'''
import logging
import operator
import os
import re
import shutil

from matplotlib import pyplot
import numpy

from network import draw_network_graph
from organise_iters import organise_iters_in, organise_iters_best


def organise_output(output):
    ''' 
    cleaning up output folder after matsim simulation
    removing all iteration folder & other not important
    moving the output_tripdurations file & parsing it to fitness.txt
    '''
#     organise_iters_in(output)
    logging.info("Cleaning up : " + str(output))
    iters = '/ITERS/'
    
    dirs = [ (int((str(x)[3:])), x) for x in os.listdir(output + iters) if (not x.startswith('.'))]
    dirs = sorted(dirs, key=operator.itemgetter(0))
    
    number = str(dirs[-1][0])
    lastiterdir = dirs[-1][1] + '/'
    
    source1 = number + '.events.xml.gz'
    source2 = number + '.tripdurations.txt'
    dest1 = '/output_events.xml.gz'
    dest2 = '/output_tripdurations.txt'
    
    shutil.move(output + iters + lastiterdir + source1, output + dest1)
    shutil.move(output + iters + lastiterdir + source2, output + dest2)
    
    tripdurationLines = open(output + dest2).readlines()
    tripduration = tripdurationLines[-1]
    time = re.match(r'.*duration: (\d+\.\d+) seconds.*', tripduration)
    text_file = open(output + "/fitness.txt", "w")
    text_file.write(str(time.group(1)))
    text_file.close()
    logging.info("Created : " + str(output + "fitness.txt"))
    
    draw_network_graph(os.path.join(output, 'network.xml'), os.path.join(output, 'network.png'))
    logging.info("Created a network graph")

    shutil.rmtree(output + iters)
    shutil.rmtree(output + '/tmp/')
    os.remove(output + '/logfile.log')
    os.remove(output + '/logfileWarningsErrors.log')
    os.remove(output + '/output_config.xml.gz')
    os.remove(output + '/output_facilities.xml.gz')
    os.remove(output + '/output_network.xml.gz')
    os.remove(output + '/output_personAttributes.xml.gz')
    os.remove(output + '/output_plans.xml.gz')
    # ## Returns fitness
    return time.group(1);

def remove_output_events(output):
    output = output + '/../'
    dirs = [x for x in os.listdir(output) if (not x.startswith('.') and x != 'best')]
    for dirname in dirs:
        try:
            os.remove(output + dirname + '/output_events.xml.gz')
        except OSError:
            pass
    pass

def organise_best(output):
    '''
    organising the folder of the best chromosome in generation
    creating a symlink to the best chromosome folder
    adding score to fitness graph in main dir
    '''
#     organise_iters_best(output)
    distance = open(os.path.join(output, 'fitness.txt'), "r")
    dist = distance.readline()
    distance.close()
    
    os.symlink(output, (output + '/../best'))
    
    root = output + '/../../'

    text_file = open((root + "fitness.txt"), "a+")
    text_file.write(dist + "\n")
    text_file.close()

    y1 = numpy.fromfile((root + "/fitness.txt"), sep="\n")
    v = numpy.fromfile((root + "/fitnessInitial.txt"), sep="\n")
    y2 = numpy.linspace(v[0], v[0], num=len(y1))
    
    pyplot.plot(y1, label='Najlepsza siec')
    pyplot.plot(y2, label='Wejsciowa siec')
    pyplot.legend()
    pyplot.savefig(os.path.join(root, 'fitness.png'))
    pyplot.close()
    remove_output_events(output)
    pass

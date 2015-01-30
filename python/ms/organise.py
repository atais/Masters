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
from network import draw_network_graph


def organise_output(output):
    ''' 
    cleaning up output folder after matsim simulation
    removing all iteration folder & other not important
    moving the output_tripdurations file & parsing it to fitness.txt
    '''
    
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
    
    tripdurationLines = open(output+dest2).readlines()
    tripduration = tripdurationLines[-1]
    time = re.match( r'.*duration: (\d+\.\d+) seconds.*', tripduration)
    text_file = open(output+"/fitness.txt", "w")
    text_file.write(str(time.group(1)))
    text_file.close()
    logging.info("Created : " + str(output+"/fitness.txt"))
    
    draw_network_graph(output+'/network.xml',output+'/network.png')
    logging.info("Created a network graph")

    shutil.rmtree(output+iters)
    shutil.rmtree(output+'/tmp/')
    os.remove(output+'/logfile.log')
    os.remove(output+'/logfileWarningsErrors.log')
    os.remove(output+'/output_config.xml.gz')
    os.remove(output+'/output_facilities.xml.gz')
    os.remove(output+'/output_network.xml.gz')
    os.remove(output+'/output_personAttributes.xml.gz')
    #os.remove(output+'/output_plans.xml.gz')
    pass

def remove_output_events(output):
    output = output+'/../'
    dirs = [x for x in os.listdir(output) if (not x.startswith('.') and x != 'best')]
    for dirname in dirs:
        os.remove(output+dirname+'/output_events.xml.gz')
    pass

def store_chromosomes(output):
    chromosomes = output+'/../'
    root = output+'/../../'
    
    dirs = [x for x in os.listdir(chromosomes) if (not x.startswith('.') and x != 'best')]
    for dirname in dirs:
        binf = open(os.path.join(chromosomes+dirname, 'chromosome.txt'), "r")
        binary = binf.readline()
        binf.close()
        
        text_file = open((root+"chromosomes.txt"), "a+")
        text_file.write(binary+"\n")
        text_file.close()
    
    lines = open((root+"chromosomes.txt"), "r").readlines()
    slines = set(lines)
    
    r = False
    if(len(lines) != len(slines)):
        r = True
    return r

def organise_best(output):
    '''
    organising the folder of the best chromosome in generation
    creating a symlink to the best chromosome folder
    adding score to fitness graph in main dir
    '''
    distance = open(os.path.join(output, 'fitness.txt'), "r")
    dist = distance.readline()
    distance.close()
    
    os.symlink(output, (output+'/../best'))
    
    root = output+'/../../'

    text_file = open((root+"fitness.txt"), "a+")
    text_file.write(dist+"\n")
    text_file.close()

    y1 = numpy.fromfile((root+"/fitness.txt"), sep="\n")
    v = numpy.fromfile((root+"/fitnessInitial.txt"), sep="\n")
    y2 = numpy.linspace(v[0], v[0], num=len(y1))
    
    pyplot.plot(y1)
    pyplot.plot(y2)
    
    pyplot.savefig(os.path.join(root, 'fitness.png'))
    remove_output_events(output)
    return store_chromosomes(output)

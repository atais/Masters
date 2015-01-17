'''
Created on 16 sty 2015

@author: michalsiatkowski
'''
import os
import operator
import logging
import shutil


def organise_output(output):
    logging.info("Cleaning up : " + str(output))
    iters = 'ITERS/'
    
    dirs = [ (int((str(x)[3:])), x) for x in os.listdir(output+iters) if (not x.startswith('.'))]
    dirs = sorted(dirs, key=operator.itemgetter(0))
    
    number = str(dirs[-1][0])
    lastiterdir = dirs[-1][1]+'/'
    
    source1 = number+'.events.xml.gz'
    source2 = number+'.tripdurations.txt'
    dest1 = 'output_events.xml.gz'
    dest2 = 'output_tripdurations.txt'
    
    shutil.move(output+iters+lastiterdir+source1, output+dest1)
    shutil.move(output+iters+lastiterdir+source2, output+dest2)
    logging.info("Moved : " + str(source1) + " to " + str(dest1))
    logging.info("Moved : " + str(source2) + " to " + str(dest2))
    
    shutil.rmtree(output+iters)
    shutil.rmtree(output+'tmp/')
    logging.info("Removed : " + str(iters))
    logging.info("Removed : " + str('tmp/'))
    
    os.remove(output+'output_plans.xml.gz')
    logging.info("Removed : " + str('output_plans.xml.gz'))
    
    pass

if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')
    organise_output('../../output/siouxfalls/')
    pass
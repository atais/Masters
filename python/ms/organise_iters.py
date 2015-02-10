'''
Created on 9 lut 2015

@author: michalsiatkowski
'''
import logging
import os
import operator
import re
import numpy
from matplotlib import pyplot

def plot_iters_from(folder, idx=""):
    # Fake matsim pass
    try:
        y = numpy.fromfile((folder + "/iters.txt"), sep="\n")
        pyplot.plot(y, label='sr. czas ' + str(idx))
         
        x1, x2, y1, _ = pyplot.axis()
        pyplot.axis((x1, x2, y1, float(y[6])))
        pyplot.legend(loc='center left', bbox_to_anchor=(1, 0.5))
    except IOError:
        pass
    pass

def organise_iters_in(output):
    logging.info("Graphing iters in: " + str(output))
    iters = '/ITERS/'
    
    dirs = [ (int((str(x)[3:])), x) for x in os.listdir(output + iters) if (not x.startswith('.'))]
    dirs = sorted(dirs, key=operator.itemgetter(0))
    
    scores = []
    for no, cdir in dirs:
        tripduration_file = output + iters + cdir + '/' + str(no) + '.tripdurations.txt'
        tripduration_lines = open(tripduration_file).readlines()
        tripduration = tripduration_lines[-1]
        time = re.match(r'.*duration: (\d+\.\d+) seconds.*', tripduration)
        scores.append(str(time.group(1)))
        pass
    
    text_file = open(output + "/iters.txt", "w")
    for i in scores:
        text_file.write(str(i) + '\n')
    text_file.close()
    
    plot_iters_from(output)
    pyplot.savefig(os.path.join(output, 'iters.png'), bbox_inches='tight')
    pyplot.close()
    pyplot.clf()
    pass


def organise_iters_best(output):
    root = output + '/../'
    dirs = [x for x in os.listdir(root) if (not x.startswith('.') and x != 'best')]
    for idx, dirname in enumerate(dirs):
        plot_iters_from(root + dirname, idx)
    pyplot.savefig(os.path.join(output, 'itersBest.png'), bbox_inches='tight')
    pyplot.close()
    pyplot.clf()
    pass

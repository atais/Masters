'''
Created on 9 lut 2015

@author: michalsiatkowski
'''
import logging
import unittest


from tests.utils import r
import os
from matplotlib import pyplot
from ms.organise_iters import plot_iters_from, organise_iters_in, \
    organise_iters_best


class Test(unittest.TestCase):

    def setUp(self):
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')
'''
    def testOrganise(self):
        output = (r('output/'))
        
        all = [(r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/def/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/1/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/2/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/3/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/4/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/5/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/6/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/7/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/8/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/9/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/10/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/11/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/12/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/13/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/14/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/15/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/16/')),
        (r('/Users/michalsiatkowski/Documents/Masters/output/sioux-out/17/'))]
        
        
#         for cc in all:
#             organise_iters_in(cc)
        
        for idx,cc in enumerate(all[:5]):
            plot_iters_from(cc,idx, False)
            
        fig = pyplot.gcf()
        fig.set_size_inches(18.5,10.5)
        x1, x2, y1, _ = pyplot.axis()
        pyplot.axis((x1, x2, y1, 1450))
        
        pyplot.savefig(os.path.join(output, 'iters1.png'), bbox_inches='tight',dpi=200)
        pyplot.close()
        pyplot.clf()
        
        plot_iters_from(all[0],0, False)
        for idx,cc in enumerate(all[5:9]):
            plot_iters_from(cc,idx+5,False)
            
        fig = pyplot.gcf()
        fig.set_size_inches(18.5,10.5)
        x1, x2, y1, _ = pyplot.axis()
        pyplot.axis((x1, x2, y1, 1450))
        
        pyplot.savefig(os.path.join(output, 'iters2.png'), bbox_inches='tight',dpi=200)
        pyplot.close()
        pyplot.clf()
        
        plot_iters_from(all[0],0,False)
        for idx,cc in enumerate(all[9:13]):
            plot_iters_from(cc,idx+9,False)
            
        fig = pyplot.gcf()
        fig.set_size_inches(18.5,10.5)
        x1, x2, y1, _ = pyplot.axis()
        pyplot.axis((x1, x2, y1, 1450))
        
        pyplot.savefig(os.path.join(output, 'iters3.png'), bbox_inches='tight',dpi=200)
        pyplot.close()
        pyplot.clf()
        
        plot_iters_from(all[0],0,False)
        for idx,cc in enumerate(all[13:]):
            plot_iters_from(cc,idx+13,False)
            
        fig = pyplot.gcf()
        fig.set_size_inches(18.5,10.5)
        x1, x2, y1, _ = pyplot.axis()
        pyplot.axis((x1, x2, y1, 1450))
        
        pyplot.savefig(os.path.join(output, 'iters4.png'), bbox_inches='tight',dpi=200)
        pyplot.close()
        pyplot.clf()
        
        plot_iters_from(all[0],0,False)
        plot_iters_from(all[1],1,False)
        plot_iters_from(all[17],17,False)
            
        fig = pyplot.gcf()
        fig.set_size_inches(18.5,10.5)
        x1, x2, y1, _ = pyplot.axis()
        pyplot.axis((x1, x2, y1, 1450))
        
        pyplot.savefig(os.path.join(output, 'iters5.png'), bbox_inches='tight',dpi=200)
        pyplot.close()
        pyplot.clf()
        
  '''      
        pass        


if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()

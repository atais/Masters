'''
Created on 9 lut 2015

@author: michalsiatkowski
'''
import unittest
import os
import shutil
from tests.utils import r
import zipfile
import logging
from ms.organise_iters import organise_iters_in, plot_iters_from
from matplotlib import pyplot


class Test(unittest.TestCase):

    def setUp(self):
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')
        if os.path.exists(r('output/iters_only')):
            shutil.rmtree(r('output/iters_only'))
            
        if os.path.exists(r('output/iters_only2')):
            shutil.rmtree(r('output/iters_only2'))
            
        if os.path.exists(r('output/iters_only3')):
            shutil.rmtree(r('output/iters_only3'))
            
        if os.path.exists(r('output/iters_only4')):
            shutil.rmtree(r('output/iters_only4'))
            
        if os.path.exists(r('output/iters_only5')):
            shutil.rmtree(r('output/iters_only5'))
            
        if os.path.exists(r('output/iters_only6')):
            shutil.rmtree(r('output/iters_only6'))
            
        with zipfile.ZipFile(r('resources/iters_only.zip'), "r") as z:
            z.extractall(r("output/"))

    def testOrganise(self):
        organise_iters_in(r('output/iters_only'))
        pass        

    def testOrganise2(self):
        plot_iters_from(r('output/iters_only2'), 1)
        plot_iters_from(r('output/iters_only3'), 2)
        plot_iters_from(r('output/iters_only4'), 3)
        plot_iters_from(r('output/iters_only5'), 4)
        plot_iters_from(r('output/iters_only6'), 5)
        pyplot.savefig(os.path.join(r('output/iters_only'), 'itersBest.png'))
        pyplot.close()
        pyplot.clf()

if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()

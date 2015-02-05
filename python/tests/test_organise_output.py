'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
import unittest

import os
import shutil
import logging
from ms.organise_output import organise_output, organise_best
import zipfile
from tests.utils import r


class AnalyseTest(unittest.TestCase):

    def setUp(self):
        if os.path.exists(r('output/siouxfalls')):
            shutil.rmtree(r('output/siouxfalls'))
            
        with zipfile.ZipFile(r('resources/dirty_siouxfalls.zip'), "r") as z:
            z.extractall(r("output/"))
            
        with zipfile.ZipFile(r('resources/best-chromosome.zip'), "r") as z:
            z.extractall(r("output/"))

    def testStructureMain(self):
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')
        organise_output(r('output/siouxfalls/'))
        pass        
    
    def testBestFunc(self):
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')
        organise_best(r('output/siouxfalls/ga.0/chromosome'))
    
        print "organise test done"

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()
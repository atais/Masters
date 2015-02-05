'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
import os
import unittest

import ms.network_analyse
from utils import r 


class AnalyseTest(unittest.TestCase):

    def setUp(self):
        if os.path.exists(r('output/network-analised.xml')):
            os.remove(r('output/network-analised.xml'))

    def testStructureMain(self):
        source = (r('resources/default-network.xml'))
        output = (r('output/network-analised.xml'))
        
        ms.network_analyse.analyse_and_save(source, output)
        self.assertTrue(os.path.exists(r('output/network-analised.xml')), "file created")

if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()

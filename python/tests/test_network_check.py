'''
Created on 29 sty 2015

@author: michalsiatkowski
'''
import unittest
from tests.utils import r
from ms import network_check
import logging


class Test(unittest.TestCase):

    def setUp(self):
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')

    def testSample(self):
        binary = "[0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]"
        mother_file = r('resources/default-network.xml')
         
        result = network_check.chromosome_strongly_connected(binary, mother_file)
        self.assertTrue(result  , "false!")
        pass
     
    def testRandomizer(self):
        mother_file = r('resources/default-network.xml')

        binary = network_check.create_randomized_sc_graph(mother_file)
        result = network_check.chromosome_strongly_connected(binary, mother_file)
        self.assertTrue(result  , "false!")
        # print binary
        pass
    
    def testStressTest(self):
        for i in range(0, 1000):
            mother_file = r('resources/network.xml')
            binary = network_check.create_randomized_sc_graph(mother_file)
            


if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testSample']
    unittest.main()

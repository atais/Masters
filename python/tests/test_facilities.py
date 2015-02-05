'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
import unittest
import os
import ms.network_facilities as fac
from utils import r 

class Test(unittest.TestCase):

    def setUp(self):
        if os.path.exists(r('output/facilities.png')):
            os.remove(r('output/facilities.png'))

    def testFacilitiesMain(self):
        network = r('resources/proper-network.xml')
        facilities = r('resources/facilities.xml')
        output = r("output/facilities")
        
        fac.draw_facilities_graph(network, facilities, output)
        self.assertTrue(os.path.exists(r('output/facilities.png')), "file created")
        print "facilities test done"


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testFacilitiesMain']
    unittest.main()
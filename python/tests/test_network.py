'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
import unittest
from ms.network import draw_network_graph, draw_events_graph
import os
import logging
import shutil
from utils import r

class Test(unittest.TestCase):


    def setUp(self):
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')
    
        if os.path.exists(r('output/network.png')):
            os.remove(r('output/network.png'))
        
        if os.path.exists(r('output/test')):
            shutil.rmtree(r('output/test'))
        
        if os.path.exists(r('output/test_big')):
            shutil.rmtree(r('output/test_big'))
        

    def testNetworkGraph(self):
        print "network test start"
        graph = r('resources/ga-network.xml')
        out = r('output/network')
        draw_network_graph(graph, out)
        self.assertTrue(os.path.exists(r('output/network.png')), "file not created")
        print "network test done"
         
        
    def testNetworkEvents(self):
        draw_events_graph(r('resources/proper-network.xml'), r('resources/output_events.xml.gz'), r('output/test'))
        self.assertTrue(os.path.exists(r('output/test')), "path created")
        
        draw_events_graph(r('resources/proper-network.xml'), r('resources/output_events_big.xml.gz'), r('output/test_big'))
        self.assertTrue(os.path.exists(r('output/test')), "path created")
        
        self.assertTrue(True)


if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testNetworkMain']
    unittest.main()

'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
import unittest
from ms.network import draw_network_graph
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
        

    def testNetworkGraph(self):
        print "network test start"
        graph = r('resources/ga-network.xml')
        out = r('output/network')
        draw_network_graph(graph, out)
        self.assertTrue(os.path.exists(r('output/network.png')), "file not created")
        print "network test done"
#     def testNetworkEvents(self):
#         net.draw_events_graph(r('resources/proper-network.xml'), r('resources/output_events.xml.gz'), r('output/test'))
#         self.assertTrue(os.path.exists(r('output/test')), "path created")


if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testNetworkMain']
    unittest.main()

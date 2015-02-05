'''
Created on 26 sty 2015

@author: michalsiatkowski
'''
import unittest

from ms.xml_to_graph import xml_to_graph
from tests.utils import r

def correct_pos(attributes):
    return "%f,%f" % (float(attributes.get('x')), float(attributes.get('y')))

class Test(unittest.TestCase):


    def testName(self):
        xml = r('resources/default-network.xml')
        graph = xml_to_graph(xml)
        
        self.assertNotEqual(len(graph.nodes()), 0, "0 nodes parsed!")
        self.assertNotEqual(graph, None, "graph is None")


if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()

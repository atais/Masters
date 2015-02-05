'''
Created on 19 sty 2015

@author: michalsiatkowski
'''
import unittest
from tests.utils import r
import ms.network as network
import ms.graph_simplify as simple


class Test(unittest.TestCase):

    def testName(self):
        
        xml = r('resources/default-network.xml')
        graph = network.generate_network_graph(xml)
        
        simple_graph = simple.to_simple_graph(graph)
              
        network.save_graph(simple_graph, r('output/test'))
        network.save_graph(graph, r('output/network'))
        pass
     

if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()

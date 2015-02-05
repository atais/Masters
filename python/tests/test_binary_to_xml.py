'''
Created on 5 lut 2015

@author: michalsiatkowski
'''
import unittest
from tests.utils import r
from ms.binary_to_xml import binary_to_xml
from ms.xml_to_graph import lxml_to_graph
from ms import network


class Test(unittest.TestCase):


    def test_binary_to_xml(self):
        binary = "[0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]"
        mother_file = r('resources/default-network.xml')
         
        new_network = binary_to_xml(binary, mother_file) 
        graph = lxml_to_graph(new_network)
        network.save_graph(graph, r('output/network'))


if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.test_binary_to_xml']
    unittest.main()

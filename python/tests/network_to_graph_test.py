'''
Created on 26 sty 2015

@author: michalsiatkowski
'''
import unittest
from ms.network_to_graph import xml_to_graph
from tests.utils import r
from ms.network import generate_network_graph


def correct_pos(attributes):
    return "%f,%f" % (float(attributes.get('x')), float(attributes.get('y')))

class Test(unittest.TestCase):


    def testName(self):
        xml = r('resources/ga-network.xml')
        graph = xml_to_graph(xml)
        
        self.assertNotEqual(len(graph.nodes()), 0, "0 nodes parsed!")
        self.assertNotEqual(graph, None, "graph is None")

    def testWdrozeniowy(self):
        xml = r('resources/ga-network.xml')
        node_attr = {}
        node_attr['style'] = 'solid'
        node_attr['color'] = 'white'
        
        edge_attr = {}
        edge_attr['occupied'] = 0
        edge_attr['penwidth'] = 2
        edge_attr['color'] = 'orange'

        graph = xml_to_graph(xml, node_attr=node_attr, link_attr=edge_attr, pos_function=correct_pos)
        graph2 = generate_network_graph(xml)

        nodes1 = graph.edges(data=True)
        
        for node1 in nodes1:
            node2 = graph2.edge[node1[0]][node1[1]]
            self.assertEqual(node1[2], node2, "Not equal edges!")
        
        print "network to graph test done"

if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.testName']
    unittest.main()

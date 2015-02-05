'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
from lxml import etree

from graph_to_binary import graph_to_binary
import xml_to_graph as xml_to_graph


def xml_to_binary(xml):
    network = etree.parse(xml)
    return lxml_to_binary(network)

def lxml_to_binary(network):
    graph = xml_to_graph.lxml_to_graph(network)
    return graph_to_binary(graph)



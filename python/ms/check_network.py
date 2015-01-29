'''
Created on 26 sty 2015

@author: michalsiatkowski
'''
from lxml import etree
import networkx

from binary_network import fromBinarytoXML
from network_to_graph import network_to_graph
from utils import timing


def is_strongly_connected(network):
    graph = network_to_graph(network)
    result = networkx.is_strongly_connected(graph)
    return result

def xml_strongly_connected(xml):
    network = etree.parse(xml)
    return is_strongly_connected(network)

@timing
def array_strongly_connected(array, oryginal_xml):
    network = fromBinarytoXML(oryginal_xml, array)
    return is_strongly_connected(network)
    
    
    
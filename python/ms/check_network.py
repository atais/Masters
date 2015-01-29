'''
Created on 26 sty 2015

@author: michalsiatkowski
'''
from ms.binary_network import fromBinarytoXML
from lxml import etree
from ms import network_to_graph
import networkx as networkx

def is_strongly_connected(network):
    graph = network_to_graph(network)
    result = networkx.is_strongly_connected(graph)
    return result

def xml_strongly_connected(xml):
    network = etree.parse(xml)
    return is_strongly_connected(network)

def array_strongly_connected(array, oryginal_xml):
    network = fromBinarytoXML(array, oryginal_xml)
    return is_strongly_connected(network)
    
    
    
'''
Created on 26 sty 2015

@author: michalsiatkowski
'''
from lxml import etree
import networkx

from binary_network import fromBinarytoXML
from binary_network import fromXMLtoBinary
from network_to_graph import network_to_graph
from utils import timing
from random import randint
import ast


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
    
def create_randomized_sc_graph(oryginal_xml):
    array = ast.literal_eval(fromXMLtoBinary(oryginal_xml))
    
    start = 0
    stop = len(array)
    
    while start < stop / 2:
        index = randint(0, stop - 1)
        if (array[index] is 1):
            temp = list(array)
            temp[index] = 0
            if (array_strongly_connected(str(temp), oryginal_xml)):
                array = temp
        
        start += 1
        pass

    return str(array)
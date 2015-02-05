'''
Created on 26 sty 2015

@author: michalsiatkowski
'''
import ast
from random import randint

from lxml import etree
import networkx

from binary_to_xml import binary_to_xml
from xml_to_binary import xml_to_binary
from xml_to_graph import lxml_to_graph
from utils import timing


def lxml_strongly_connected(lxml):
    graph = lxml_to_graph(lxml)
    return networkx.is_strongly_connected(graph)

def xml_strongly_connected(xml):
    network = etree.parse(xml)
    return lxml_strongly_connected(network)

@timing
def chromosome_strongly_connected(array, oryginal_xml):
    network = binary_to_xml(array, oryginal_xml)
    return lxml_strongly_connected(network)
    
def create_randomized_sc_graph(oryginal_xml):
    array = ast.literal_eval(xml_to_binary(oryginal_xml))
    
    start = 0
    stop = randint(1, 4)
    
    while start < stop:
        index = randint(0, len(array) - 1)
        if (array[index] is 1):
            temp = list(array)
            temp[index] = 0
            if (chromosome_strongly_connected(str(temp), oryginal_xml)):
                array = temp
        
        start += 1
        pass

    return str(array)

'''
Created on 5 lut 2015

@author: michalsiatkowski
'''
import ast

from lxml import etree

from graph_simplify import to_simple_graph
from xml_to_graph import lxml_to_graph
import logging


def binary_to_xml(binary, xml, save=None):
    ory_network = etree.parse(xml)
    ory_graph = lxml_to_graph(ory_network)
    ory_graph = to_simple_graph(ory_graph)
    
    binary = binary.replace('"', "");
    bArray = ast.literal_eval(binary)

    for idx, val in enumerate(bArray):
        if (val == 0):
            removed_edge = ory_graph.edges()[idx]
            id_to_remove = ory_graph[removed_edge[0]][removed_edge[1]]
            for path in id_to_remove.get('path'):
                try:
                    logging.debug('removing '+str(path))
                    link = (ory_network.xpath("//link[@from='" + str(path[0]) + "' and @to='" + str(path[1]) + "'] "))[0]
                    link.getparent().remove(link)

                except IndexError:
                    logging.warn('could not remove ' + str(path))
       
    # find orphaned nodes
    nodes = ory_network.findall('.//node')
    for node in nodes:
        links_from = (ory_network.xpath("//link[@from='" + node.get('id') + "']"))
        links_to = (ory_network.xpath("//link[@to='" + node.get('id') + "']"))
        
        if ((not links_from) or (not links_to)):
            logging.debug('removing '+etree.tostring(node))
            node.getparent().remove(node) 
        
        
    if (save is not None):
        ory_network.write(save)
    return ory_network

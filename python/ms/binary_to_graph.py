'''
Created on 10 lut 2015

@author: michalsiatkowski
'''
from lxml import etree
import ast
import logging
from xml_to_graph import lxml_to_graph
from graph_simplify import to_simple_graph

def correct_pos(node_attr):
    return "%f,%f" % (float(node_attr.get('x')), float(node_attr.get('y')))

def binary_to_xml_graph(binary, xml):
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
                    link.attrib['color'] = 'red'

                except IndexError:
                    logging.warn('could not remove ' + str(path))
       
    # find orphaned nodes
    return ory_network

def binary_to_graph(binary, xml, node_style=('solid', 'white'), edge_style=(2, 'orange')):
    network = binary_to_xml_graph(binary,xml)
    
    node_attr = {}
    node_attr['style'] = node_style[0]
    node_attr['color'] = node_style[1]
    
    edge_attr = {}
    edge_attr['occupied'] = 0
    edge_attr['penwidth'] = edge_style[0]
#     edge_attr['color'] = edge_style[1]

    graph = lxml_to_graph(network, node_attr=node_attr, link_attr=edge_attr, pos_function=correct_pos)
    return graph
    pass
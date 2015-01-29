'''
Created on 26 sty 2015

@author: michalsiatkowski
'''

import networkx as networkx
from lxml import etree

def default_pos(node_attr):
    return [float(node_attr.get('x')), float(node_attr.get('y'))]

def xml_to_graph(xml, node_attr={}, link_attr={}, pos_function=default_pos):
    network = etree.parse(xml)
    return network_to_graph(network, node_attr=node_attr, link_attr=link_attr, pos_function=pos_function)

def network_to_graph(network, graph=networkx.DiGraph(), node_attr={}, link_attr={}, pos_function=default_pos):
    '''
    converts network (lxml) to networkx DiGraph
    network - lxml
    graph - pass to add more nodes &/OR links to it
    node_attr - additional node attributes as dict (fe. style)
    link_attr - additional link attributes as dict (fe. style)
    pos_function - pass function to add ["pos"] attribute in desired manner manner
                    look @ default_pos
    '''
    nodes = network.findall(".//node")
    
    for node in nodes:
        current_node_attr = {}
        current_node_attr.update(node_attr)
        current_node_attr.update(dict(node.items()))
        current_node_attr['pos'] = pos_function(current_node_attr)
        graph.add_node(current_node_attr.get('id'), current_node_attr)
    
    links = network.findall('.//link')
    for link in links:
        current_link_attr = {}
        current_link_attr.update(link_attr)
        current_link_attr.update(dict(link.items()))
        graph.add_edge(current_link_attr.get('from'), current_link_attr.get('to'), current_link_attr)
    
    return graph

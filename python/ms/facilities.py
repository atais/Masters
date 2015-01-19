'''
Created on 12 sty 2015

@author: michalsiatkowski
'''

from lxml import etree
from utils import timing
import matplotlib.pyplot as plt
from pylab import rcParams
import networkx as nx
import os

@timing
def save_graph(filename):
    plt.axis('off')
    rcParams['figure.figsize'] = 40, 40
    if not os.path.exists(os.path.dirname(filename)):
        os.makedirs(os.path.dirname(filename))
    plt.savefig(filename, dpi=800)

@timing
def draw_graph(graph, labels=False):
    pos = nx.get_node_attributes(graph, 'pos')
    
    node_size = list(nx.get_node_attributes(graph, 'size').values())
    node_color = list(nx.get_node_attributes(graph, 'color').values())
    
    edges = graph.edges()        
    edge_size = [graph[u][v]['size'] for u, v in edges]
    edge_color = [graph[u][v]['color'] for u, v in edges]
        
    
    nx.draw_networkx_nodes(graph, pos,
                           node_size=node_size,
                           node_color=node_color)
    
    nx.draw_networkx_edges(graph, pos,
                           arrows=True,
                           width=edge_size,
                           edge_color=edge_color)
    
    if (labels):
        labels = nx.get_node_attributes(graph, 'id')
        nx.draw_networkx_labels(graph, pos,
                            labels,
                            font_size=6,
                            font_color='red')
    
    pass

@timing
def generate_facilities_graph(graph, xml, style=(1, 'black')):
    """
        Generates a graph of facilities from xml file
            Optional node_style argument (border style, border color)
        
        Requires networkx drawing, because nod takes too long
    """
    network = etree.parse(xml)
    facilities = network.findall(".//facility")

    for facility in facilities:
        attributes = dict(facility.items())
        attributes['size'] = style[0]
        attributes['color'] = style[1]
        attributes['pos'] = [float(attributes.get('x')), float(attributes.get('y'))]
        graph.add_node('fac' + attributes.get('id'), attributes)
    return graph

@timing
def generate_network_graph(graph, xml, node_style=(0, 'white'), edge_style=(1, 'orange')):
    """
        Generates a graph from network xml file
            Optional node_style argument (size, color, alpha)
            Optional edge_style argument (width, color)
            
        Returns a networkx graph
    """
    network = etree.parse(xml)
    nodes = network.findall(".//node")
    links = network.findall('.//link')
    
    for node in nodes:
        attributes = dict(node.items())
        attributes['size'] = node_style[0]
        attributes['color'] = node_style[1]
        attributes['pos'] = [float(attributes.get('x')), float(attributes.get('y'))]
        graph.add_node(attributes.get('id'), attributes)
        
    for link in links:
        attributes = dict(link.items())
        attributes['occupied'] = 0
        attributes['size'] = edge_style[0]
        attributes['color'] = edge_style[1]
        graph.add_edge(attributes.get('from'), attributes.get('to'), attributes)
    
    return graph

def facilities_graph(network, facilities, output):
    graph = nx.DiGraph()
    graph = generate_network_graph(graph, network)
    graph = generate_facilities_graph(graph, facilities)
    
    draw_graph(graph)
    save_graph(output)

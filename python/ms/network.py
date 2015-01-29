'''
Created on 7 sty 2015

@author: michalsiatkowski
'''

from lxml import etree
from utils import timing
import utils
import networkx as nx
import math as math
import logging
import os
from ms.network_to_graph import xml_to_graph

        
@timing
def save_graph(graph, filename):
    A=nx.to_agraph(graph)
    A.layout()
    if not os.path.exists(os.path.dirname(filename)):
        os.makedirs(os.path.dirname(filename))           
    A.draw(filename+'.png')
    
def correct_pos(node_attr):
    return "%f,%f"%(float(node_attr.get('x')),float(node_attr.get('y')))
    
@timing
def generate_network_graph(xml, node_style=('solid', 'white'), edge_style=(2, 'orange')):
    """
        Generates a graph from network xml file
            Optional node_style argument (border style, border color)
            Optional edge_style argument (penwidth, color)
            
        Returns a networkx graph
    """
    node_attr = {}
    node_attr['style'] = node_style[0]
    node_attr['color'] = node_style[1]
    
    edge_attr = {}
    edge_attr['occupied'] = 0
    edge_attr['penwidth'] = edge_style[0]
    edge_attr['color'] = edge_style[1]

    graph = xml_to_graph(xml, node_attr=node_attr, link_attr=edge_attr, pos_function=correct_pos)
    return graph

def count_events(events, i, pointer):
    links = {}
    while (i < len(events)) and ((math.floor(float(events[i].get('time')) / 3600) <= pointer) or (pointer == 23)):
        event = events[i]
        i += 1
        if event.get('link') in links:
            links[event.get('link')] += 1
        else:
            links[event.get('link')] = 0
    return links, i

def fill_graph(network, graph, links):
    for k, v in links.items():
        link = network.xpath("//link[@id='" + k + "']")[0]
        graph[link.get('from')][link.get('to')]['occupied'] = v
        
def color_edge_occupation(graph):
    '''
        Scale edges for traffic
        Returns the smallest Hue value in edges (0-1)
    '''
    scale_min=1
    edges = graph.edges()
    for u, v in edges:
        value = float(graph[u][v].get('occupied'))
        top = float(graph[u][v].get('capacity'))
        graph[u][v]['color'],h = utils.scale(value, top)
        if (h < scale_min):
            scale_min = h
    return scale_min

def draw_events_graph(network_file, events_file, folder='', interval=1, scale_threshold=0.22):
    """
        Generates a graph of events from network and event xml files
        Optional interval argument [hours], default is 1
    """
    logging.info("Starting to generate events graphs...")
    
    logging.info("Loading events... might take some time")
    events_tree = etree.parse(events_file)
    events = (events_tree.xpath("//event[@type='entered link']"))
    
    logging.info("Loading network...")
    network = etree.parse(network_file)
    
    # setting up starting time
    # full hour of the 1st event
    i = 0
    pointer = math.floor(float(events[i].get('time')) / 3600)
    
    while(pointer < 24):
        logging.info("Loading events starting from: " + str(pointer) + " h.")
        graph = generate_network_graph(network_file)
        
        links, i = count_events(events, i, pointer + interval)
        fill_graph(network, graph, links)
        
        scale_min = color_edge_occupation(graph)
        if (scale_min <= scale_threshold):
            save_graph(graph,folder+'/graph' + str(pointer) + '0-' + str(pointer + interval)+'0')
        pointer += interval
    
    logging.info("Finished drawing events graphs...")
    pass

def draw_network_graph(network_file, output_file):
    graph = generate_network_graph(network_file)
    save_graph(graph, output_file)
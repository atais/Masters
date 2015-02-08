'''
Created on 7 sty 2015

@author: michalsiatkowski
'''

import gzip
import logging
import os

from lxml import etree

import math as math
import networkx as nx
from utils import timing
import utils
from xml_to_graph import xml_to_graph


def save_graph(graph, filename):
    A = nx.to_agraph(graph)
    A.layout()
    if not os.path.exists(os.path.dirname(filename)):
        os.makedirs(os.path.dirname(filename))   
    
    if not ".png" in filename: 
        filename = filename + ".png"        
    A.draw(filename)
    
def correct_pos(node_attr):
    return "%f,%f" % (float(node_attr.get('x')), float(node_attr.get('y')))
    
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

def fill_graph(network, graph, links):
    for k, v in links.items():
        ## Fake Matsim walkaround
        try:
            link = network.xpath("//link[@id='" + k + "']")[0]
            graph[link.get('from')][link.get('to')]['occupied'] = v
        except IndexError:
            pass

def color_edge_occupation(graph):
    '''
        Scale edges for traffic
        Returns the smallest Hue value in edges (0-1)
    '''
    scale_min = 1
    edges = graph.edges()
    for u, v in edges:
        value = float(graph[u][v].get('occupied'))
        top = float(graph[u][v].get('capacity'))
        graph[u][v]['color'], h = utils.scale(value, top)
        if (h < scale_min):
            scale_min = h
    return scale_min

def add_event(links, event):
    if event.get('link') in links:
        links[event.get('link')] += 1
    else:
        links[event.get('link')] = 0
    return links

@timing
def draw_events_graph(network_file, events_file, folder='', interval=3600, scale_threshold=0.22):
    """
        Generates a graph of events from network and event xml files
        Optional interval argument [seconds], default is 3600 (1h)
    """
    logging.info("Starting to generate events graphs...")
    
    logging.info("Loading network...")
    network = etree.parse(network_file)
    
    logging.info("Loading events... ")
    xml_fin = gzip.open(events_file)
    context = etree.iterparse(xml_fin, tag='event')

    start_marker = 0 
    end_marker = 0
    marker_22 = 22 * 60 * 60
    links = {}
    for _, element in context:
        if (str(element.get('type')) == 'entered link'):
            current_marker = float(element.get('time'))
            if ((current_marker <= end_marker) or (end_marker > marker_22)):
                pass
            elif ((current_marker > end_marker)):
                graph = generate_network_graph(network_file)
                fill_graph(network, graph, links)
                scale_min = color_edge_occupation(graph)
                if (scale_min <= scale_threshold):
                    save_graph(graph, folder + '/events' + str(start_marker) + '-' + str(end_marker))
                links = {}
                start_marker = (math.floor(current_marker) / 3600) * 3600
                end_marker = start_marker + interval
                pass
            elif ((start_marker == 0) or (end_marker == 0)):
                # start from full hour of the first event
                start_marker = (math.floor(current_marker) / 3600) * 3600
                end_marker = start_marker + interval
                pass
            # add the event anyway
            add_event(links, element)
            element.clear()
            while element.getprevious() is not None:
                del element.getparent()[0]
    del context
    
    logging.info("Finished drawing events graphs...")
    pass

def draw_network_graph(network_file, output_file):
    graph = generate_network_graph(network_file)
    save_graph(graph, output_file)

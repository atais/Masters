'''
Created on 7 sty 2015

@author: michalsiatkowski
'''

from lxml import etree
import networkx as nx
import matplotlib.pyplot as plt
import math as math
import logging
import colorsys
import time

def timing(f):
    def wrap(*args):
        time1 = time.time()
        ret = f(*args)
        time2 = time.time()
        logging.info('     %s function took %0.3f ms' % (f.__name__, (time2 - time1) * 1000.0))
        return ret
    return wrap

def scale(edge):
    # python hsv has 0-1 range!
    # H = 0-120
    # h = 0-0.3
    s = 1
    v = 1
    top = float(edge.get('capacity'))
    value = float(edge.get('occupied'))
    h = ((0.3) / (top) * (value))
    # invert it
    h = 0.3 - h;
    if(h < 0):
        h = 0
    return colorsys.hsv_to_rgb(h, s, v)

def draw_graph(graph, mark_edge_busy=False, labels=False):
    pos = nx.get_node_attributes(graph, 'pos')
    
    node_size = list(nx.get_node_attributes(graph, 'size').values())
    node_color = list(nx.get_node_attributes(graph, 'color').values())
    node_alpha = list(nx.get_node_attributes(graph, 'alpha').values())
    
    edges = graph.edges()
    if (mark_edge_busy):
        for u, v in edges:
            graph[u][v]['color'] = scale(graph[u][v])
        
    edge_size = [graph[u][v]['size'] for u, v in edges]
    edge_color = [graph[u][v]['color'] for u, v in edges]
        
    
    nx.draw_networkx_nodes(graph, pos,
                           node_size=node_size,
                           node_color=node_color,
                           node_alpha=node_alpha)
    
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

def save_graph(filename):
    plt.axis('off')
    plt.savefig(filename, dpi=600)

def generate_network_graph(graph, xml, node_style=(0, 'white', 0), edge_style=(1, 'orange')):
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
        attributes['alpha'] = node_style[2]
        attributes['pos'] = [float(attributes.get('x')), float(attributes.get('y'))]
        graph.add_node(attributes.get('id'), attributes)
        
    for link in links:
        attributes = dict(link.items())
        attributes['occupied'] = 0
        attributes['size'] = edge_style[0]
        attributes['color'] = edge_style[1]
        graph.add_edge(attributes.get('from'), attributes.get('to'), attributes)
    
    return graph


def generate_facilities_graph(graph, xml, style=(1, 'blue', 0.1)):
    """
        Generates a graph of facilities from xml file
            Optional style argument (size, color, alpha)
        
        Returns a networkx graph
    """
    
    network = etree.parse(xml)
    facilities = network.findall(".//facility")

    for facility in facilities:
        attributes = dict(facility.items())
        attributes['size'] = style[0]
        attributes['color'] = style[1]
        attributes['alpha'] = style[2]
        attributes['pos'] = [float(attributes.get('x')), float(attributes.get('y'))]
        graph.add_node('fac' + attributes.get('id'), attributes)
    
    return graph


# @timing
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

# @timing
def fill_graph(network, graph, links):
    for k, v in links.items():
        link = network.xpath("//link[@id='" + k + "']")[0]
        graph[link.get('from')][link.get('to')]['occupied'] = v

def generate_events_graph(network_file, events_file, interval=1):
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
        graph = nx.DiGraph()
        graph = generate_network_graph(graph, network_file)
        
        links, i = count_events(events, i, pointer + interval)
        fill_graph(network, graph, links)
        
        draw_graph(graph, True)
        save_graph('graph' + str(pointer) + '-' + str(pointer + interval) + '.png')
        pointer += interval
    
    logging.info("Finished drawing events graphs...")
    pass




if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')
    graph = nx.DiGraph()
#     graph = generate_network_graph('../../scenarios/siouxfalls/network.xml')
#     graph = generate_network_graph(graph, '../../scenarios/siouxfalls-cut/network.xml')
#     graph = generate_network_graph('../../scenarios/nmbm/network.xml')
#     graph = generate_network_graph('../../scenarios/berlin/network.xml')

#     graph = generate_facilities_graph(graph, '../../scenarios/siouxfalls-cut/facilities.xml')
    
#     graph = generate_events_graph(graph, '../../scenarios/siouxfalls-cut/cut.events.xml')
    
    generate_events_graph('../../scenarios/siouxfalls-cut/network.xml', '../../output/siouxfalls-cut/cut.events.xml')
    
#     draw_graph(graph)
#     save_graph('graph.png')
    pass


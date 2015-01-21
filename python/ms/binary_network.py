'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
from lxml import etree
import ast
import logging
import networkx as nx
from ms.network import network_graph
import ms.network
from tests.utils import r

def fromXMLtoBinary(xml):
    logging.info("fromXMLtoBinary")
    network = etree.parse(xml)
    return fromNetworkToBinary(network)

def fromNetworkToBinary(network, oryg_XML=None):
    logging.info("fromNetworkToBinary")
    binary = []
    if (oryg_XML is None):
        links = network.findall('.//link')
        for link in links:
            binary.append(1)
    else:
        network_o = etree.parse(oryg_XML)
        links_o = network_o.findall('.//link')
        for link in links_o:
            links_c = (network.xpath("//link[@id='" + link.get('id') + "']"))
            if ((not links_c)):
                binary.append(0)
            else:
                binary.append(1)
    return str(binary)

def generate_network_graph(network):
    graph = nx.DiGraph()
    nodes = network.findall(".//node")
    links = network.findall('.//link')
    
    for node in nodes:
        attributes = dict(node.items())
        attributes['pos'] = "%f,%f"%(float(attributes.get('x')),float(attributes.get('y')))
        graph.add_node(attributes.get('id'), attributes)
    
    for link in links:
        attributes = dict(link.items())
        graph.add_edge(attributes.get('from'), attributes.get('to'), attributes)
    
    return graph

def fix_chromosome(chromosome, xml):
    ''' can you go from and to the node? '''
    ''' THISSSSS  NEEEDD MORE WORK'''
    logging.info("fix_chromosome")
    network = fromBinarytoXML(xml, chromosome)
    links = network.findall('.//link')
    
#     bad_changes = 0
#     for link in links:
#         nodes_from = (network.xpath("//node[@id='" + link.get('from') + "']"))
#         nodes_to = (network.xpath("//node[@id='" + link.get('to') + "']"))
#          
#         if ((not nodes_from) or (not nodes_to)):
#             link.getparent().remove(link)
#             bad_changes += 1 
#             print ('removing node' + str(node.get('id')))
    
    graph = generate_network_graph(network)
#     graph = graph.to_undirected()
    ms.network.save_graph(graph, r('resources/proper-network'))
#     graphs = list(nx.connected_component_subgraphs(graph))
    graphs = list(nx.strongly_connected_component_subgraphs(graph))
#     for idx, g in enumerate(graphs):
#         ms.network.save_graph(g, r('resources/proper-network'+str(idx)))

    new_chromosome = None
#     if (bad_changes > 0):
#         new_chromosome = fromNetworkToBinary(network, xml)
    return new_chromosome

def fromBinarytoXML(xml, binary, save=None):
    logging.info("fromBinarytoXML")
    network = etree.parse(xml)
    links = network.findall('.//link')
    
    binary = binary.replace('"', "");
    bArray = ast.literal_eval(binary)
    
    for idx, link in enumerate(links):
        if bArray[idx] == 0:
            link.getparent().remove(link)
#             print ('removing link' + str(link.get('id')))
      
    nodes = network.findall('.//node')
    for node in nodes:
        links_from = (network.xpath("//link[@from='" + node.get('id') + "']"))
        links_to = (network.xpath("//link[@to='" + node.get('id') + "']"))
        
        if ((not links_from) or (not links_to)):
            node.getparent().remove(node) 
#             print ('removing node' + str(node.get('id')))
            pass

    if (save is not None):
        network.write(save)
    return network





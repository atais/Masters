'''
Created on 5 lut 2015

@author: michalsiatkowski
'''
from lxml import etree
import ast
from xml_to_graph import lxml_to_graph
from graph_simplify import to_simple_graph

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
            for node_id in id_to_remove.get('path'):
                node = ory_network.xpath("//node[@id='" + str(node_id) + "']")[0]
                node.getparent().remove(node)
                
                links_from = (ory_network.xpath("//link[@from='" + node_id + "']"))
                for link in links_from:
                    link.getparent().remove(link)
                links_to = (ory_network.xpath("//link[@to='" + node_id + "']"))
                for link in links_to:
                    link.getparent().remove(link)
    
    if (save is not None):
        ory_network.write(save)
    return ory_network

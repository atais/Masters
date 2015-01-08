'''
Created on 7 sty 2015

@author: michalsiatkowski
'''

from lxml import etree
import networkx as nx
import matplotlib.pyplot as plt


def draw_population(f):
    node_size=0
    node_color='white'
    node_alpha=0

    network = etree.parse(f)
    
    nodes = network.findall(".//node")
    links = network.findall('.//link')
    
    G = nx.Graph()
    for node in nodes:
        attributes = dict(node.items())
        G.add_node(attributes.pop('id'), 
                   dict([('pos', [float(x) for x in attributes.values()] )]))
        
    for link in links:
        attributes = dict(link.items())
        G.add_edge(attributes.pop('from'), attributes.pop('to'))
    
    pos = nx.get_node_attributes(G, 'pos')
#     nx.draw_networkx_nodes(G,pos,node_size=node_size, 
#                            alpha=node_alpha, node_color=node_color)
    nx.draw_networkx_edges(G,pos)

    plt.axis('off')
    plt.margins(0,0)
    plt.savefig('graph.png', )
    pass

if __name__ == '__main__':
    
    draw_population('../scenarios/siouxfalls/Siouxfalls_network_PT.xml')
#     draw_network('../scenarios/nmbm/network.xml')
#     draw_network('../scenarios/berlin/network.xml')
    pass


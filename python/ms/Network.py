'''
Created on 7 sty 2015

@author: michalsiatkowski
'''
from lxml import etree
import networkx as nx
import matplotlib.pyplot as plt

def scale(value, top):
    #H = 0-120
    #S = 100
    #V = 100
    h = int((120)/(top)*(value))

def draw_graph(graph, labels=False):
    pos = nx.get_node_attributes(graph, 'pos')
    
    node_size=list(x[0] for x in nx.get_node_attributes(graph, 'style').values())
    node_color=list(x[1] for x in nx.get_node_attributes(graph, 'style').values())
    node_alpha=list(x[2] for x in nx.get_node_attributes(graph, 'style').values())
    
    edges = graph.edges()
    edge_size = [graph[u][v]['style'][0] for u,v in edges]
    edge_color = [graph[u][v]['style'][1] for u,v in edges]
    
    nx.draw_networkx_nodes(graph,pos,
                           node_size=node_size,
                           node_color=node_color,
                           node_alpha=node_alpha)
    
    nx.draw_networkx_edges(graph,pos,
                           arrows=True,
                           width=edge_size,
                           edge_color=edge_color)
    
    if (labels):
        labels=nx.get_node_attributes(graph,'id')
        nx.draw_networkx_labels(graph,pos,
                            labels,
                            font_size=6,
                            font_color='red')
    
    pass

def save_graph(filename):
    plt.axis('off')
    plt.margins(0,0)
    plt.savefig(filename, dpi=600)

def generate_network_graph(f, node_style=(1,'black',1), edge_style=(1,'blue')):
    """
        Generates a graph from file (f)
            Optional node_style argument, used as 'style' attribute in nodes
                    (size, color, alpha)
            Optional edge_style argument, used as 'style' attribute in edges
                    (width, color)
        
        Returns a networkx graph
    """
    network = etree.parse(f)
    
    nodes = network.findall(".//node")
    links = network.findall('.//link')
    
    graph = nx.DiGraph()
    for node in nodes:
        attributes = dict(node.items())
        attributes['style'] = node_style
        attributes['pos'] = [float(attributes.get('x')),float(attributes.get('y'))]
        graph.add_node(attributes.get('id'), attributes)
        
    for link in links:
        attributes = dict(link.items())
        attributes['style'] = edge_style
        graph.add_edge(attributes.get('from'), attributes.get('to'), attributes)
    
    return graph

if __name__ == '__main__':
#     graph = generate_network_graph('../../scenarios/siouxfalls/network.xml')
    graph = generate_network_graph('../../scenarios/siouxfalls-cut/network.xml')
#     graph = generate_network_graph('../../scenarios/nmbm/network.xml')
#     graph = generate_network_graph('../../scenarios/berlin/network.xml')

    draw_graph(graph, True)
    save_graph('graph.png')
    pass


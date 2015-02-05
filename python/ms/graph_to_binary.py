'''
Created on 5 lut 2015

@author: michalsiatkowski
'''
from ms import graph_simplify

def simple_graph_to_binary(graph):
    binary = []
    links_o = graph.edges()
    for _ in links_o:
        binary.append(1)
    return str(binary)

def graph_to_binary(graph):
    graph = graph_simplify.to_simple_graph(graph)
    return simple_graph_to_binary(graph)

'''
Created on 5 lut 2015

@author: michalsiatkowski
'''
import networkx as nx

def to_simple_graph(graph):
    simple_graph = nx.DiGraph()
    
    main_nodes = find_main_nodes(graph)
    simple_graph.add_nodes_from(main_nodes)
    
    for node, _ in main_nodes:
        node_edges = dfs_main_edges(graph, node, main_nodes)
        for edge, path in node_edges:
            attr = {'path':path}
            simple_graph.add_edge(node, edge, attr)
    
    return simple_graph

def find_main_nodes(graph):
    ''' 
    find main nodes in graph,
    they are chosen if they have more than 2 neighbors
    '''
    main_nodes = []
    for node in graph.nodes():
        neighbours = set(nx.all_neighbors(graph, node))
        if (len(neighbours) > 2):
            main_nodes.append((node, graph.node[node]))
    
    return main_nodes

def dfs_main_edges(G, source, mainNodes):
    '''
    edges between main nodes,
    with remembering of the skipped edges
    '''
    mainNeighbours = []
    nodes = [source]
    visited = set()
    for start in nodes:
        if start in visited:
            continue
        visited.add(start)
        stack = [(start, iter(G[start]))]
        
        # Path is to remember what edges are between main nodes
        node_from = start
        node_to = None
        path = []
        while stack:
            _, children = stack[-1]
            try:
                child = next(children)
                if child not in visited:
                    # we do not want a generator
                    # yield parent, child
                    visited.add(child)
                    node_to = child
                    path.append((node_from,node_to))
                    node_from = node_to
                    stack.append((child, iter(G[child])))
                if  child in zip(*mainNodes)[0] and child != source:
                    mainNeighbours.append((child, path))
                    node_from = source
                    node_to = None
                    path = []
                    stack.pop()
                    
            except StopIteration:
                stack.pop()
    return mainNeighbours
#             
                

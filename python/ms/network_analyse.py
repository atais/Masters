'''
Created on 10 sty 2015

@author: michalsiatkowski
'''
import logging

from lxml import etree


def same_pos_crossroad(network):
    nodes = network.findall(".//node")
    same = {}
    for node in nodes:
        if same.has_key(node):
            continue
        else:
            samex = (network.xpath("//node[@x='" + node.get('x') + "']"))
            samey = (network.xpath("//node[@y='" + node.get('y') + "']"))
            
            samexy = list(set(samex) & set(samey))
            samexy.remove(node)
            for loser in samexy:
                same[loser] = node
    
    for copy, oryginal in same.items():
        links_from = (network.xpath("//link[@from='" + copy.get('id') + "']"))
        links_to = (network.xpath("//link[@to='" + copy.get('id') + "']"))
        
        for link in links_from:
            link.set('from', oryginal.get('id'))
        
        for link in links_to:
            link.set('to', oryginal.get('id'))
        
        copy.getparent().remove(copy)
        
    logging.info("Removed {} nodes, which had the same positions".format(len(same)))
    return network


def not_one_way(network):
    links = network.findall(".//link")
    
    notoneway = []
    for link in links:
        if (link.get('oneway') != '1'):
            notoneway.append(link)
            
    for link in notoneway:
        link.set('oneway', '1')

        newlink = link.deepcopy()
        newlink.set('from', link.get('to'))
        newlink.set('to', link.get('from'))
        link.getparent().append(newlink)

    logging.info("Found {} edges, which were not one way!".format(len(notoneway)))
    return network

def analyse_and_save(source, dist):
    network = etree.parse(source)
        
    network = same_pos_crossroad(network)
    network = not_one_way(network)
    
    network.write(dist)
    pass


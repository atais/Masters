'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
from lxml import etree
import ast
import logging

def fromXMLtoBinary(xml):
    network = etree.parse(xml)
    return fromNetworkToBinary(network)

def fromNetworkToBinary(network, oryg_XML=None):
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

def fromBinarytoXML(xml, binary, save=None):
    network = etree.parse(xml)
    links = network.findall('.//link')
    
    binary = binary.replace('"', "");
    bArray = ast.literal_eval(binary)
    
    for idx, link in enumerate(links):
        if bArray[idx] == 0:
            link.getparent().remove(link)
      
    #removing nodes with 0 links, matsim thing
    nodes = network.findall('.//node')
    for node in nodes:
        links_from = (network.xpath("//link[@from='" + node.get('id') + "']"))
        links_to = (network.xpath("//link[@to='" + node.get('id') + "']"))
        
        if ((not links_from) or (not links_to)):
            node.getparent().remove(node) 

    if (save is not None):
        network.write(save)
    return network





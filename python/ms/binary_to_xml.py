'''
Created on 5 lut 2015

@author: michalsiatkowski
'''
from lxml import etree
import ast

def binary_to_xml(binary, xml, save=None):
    network = etree.parse(xml)
    links = network.findall('.//link')
    
    binary = binary.replace('"', "");
    bArray = ast.literal_eval(binary)
    for idx, link in enumerate(links):
        if bArray[idx] == 0:
            link.getparent().remove(link)
      
    # removing nodes with 0 links, matsim thing
    nodes = network.findall('.//node')
    for node in nodes:
        links_from = (network.xpath("//link[@from='" + node.get('id') + "']"))
        links_to = (network.xpath("//link[@to='" + node.get('id') + "']"))
        
        if ((not links_from) or (not links_to)):
            node.getparent().remove(node) 

    if (save is not None):
        network.write(save)
    return network

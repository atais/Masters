'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
from lxml import etree


def fromXMLtoBinary(xml):
    network = etree.parse(xml)
    links = network.findall('.//link')
    
    binary = []
    for link in links:
        binary.append(1)
        
    return str(binary)


def fromBinarytoXML(xml, binary, save):
    network = etree.parse(xml)
    links = network.findall('.//link')
    
    bArray = eval(binary)
    for idx, link in enumerate(links):
        if bArray[idx] == 0:
            link.getparent().remove(link)
#             print 'removing link' + str(link.get('id'))
      
    nodes = network.findall('.//node')
    for node in nodes:
        links_from = (network.xpath("//link[@from='" + node.get('id') + "']"))
        links_to = (network.xpath("//link[@to='" + node.get('id') + "']"))
        
        if ((not links_from) or (not links_to)):
            node.getparent().remove(node) 
#             print 'removing node' + str(node.get('id'))

    network.write(save)
    pass





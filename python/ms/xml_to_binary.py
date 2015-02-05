'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
from lxml import etree

def xml_to_binary(xml, oryginal_xml=None):
    network = etree.parse(xml)
    o_network = None
    if (oryginal_xml is not None):
        o_network = etree.parse(oryginal_xml)
    return lxml_to_binary(network, o_network)

def lxml_to_binary(lxml, oryginal_lxml=None):
    binary = []
    if (oryginal_lxml is None):
        links = lxml.findall('.//link')
        for link in links:
            binary.append(1)
    else:
        network_o = etree.parse(oryginal_lxml)
        links_o = network_o.findall('.//link')
        for link in links_o:
            links_c = (lxml.xpath("//link[@id='" + link.get('id') + "']"))
            if ((not links_c)):
                binary.append(0)
            else:
                binary.append(1)
    return str(binary)



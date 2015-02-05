'''
Created on 19 sty 2015

@author: michalsiatkowski
'''
from lxml import etree


def customize_config(config, facilities, network, population, output, iterations):
    xml = etree.parse(config)
    
    f = xml.xpath("//param[@name='inputFacilitiesFile']")[0]
    f.set('value', str(facilities))
    
    n = xml.xpath("//param[@name='inputNetworkFile']")[0] 
    n.set('value', str(network))
    
    p = xml.xpath("//param[@name='inputPlansFile']")[0] 
    p.set('value', str(population))
    
    o = xml.xpath("//param[@name='outputDirectory']")[0] 
    o.set('value', str(output))
    
    i1 = xml.xpath("//param[@name='lastIteration']")[0]
    i1.set('value', str(iterations))
    
    i2 = xml.xpath("//param[@name='writeEventsInterval']")[0]
    i2.set('value', str(iterations))
    
    xml.write(config)
    pass
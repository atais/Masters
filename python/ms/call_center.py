'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
from inspect import getmembers, isfunction
import sys

from ms import binary_to_xml
from ms import network_analyse
from ms import network_check
from ms import network_facilities
from ms import graph_simplify
from ms import network
from ms import organise_config
from ms import organise_output
from ms import xml_to_binary
from ms import xml_to_graph

import utils


def get_all_members():
    members = []
    members.extend(getmembers(binary_to_xml, isfunction))
    members.extend(getmembers(network_analyse, isfunction))
    members.extend(getmembers(network_check, isfunction))
    members.extend(getmembers(network_facilities, isfunction))
    members.extend(getmembers(graph_simplify, isfunction))
    members.extend(getmembers(network, isfunction))
    members.extend(getmembers(organise_config, isfunction))
    members.extend(getmembers(organise_output, isfunction))
    members.extend(getmembers(xml_to_binary, isfunction))
    members.extend(getmembers(xml_to_graph, isfunction))
    members.extend(getmembers(utils, isfunction))
    return dict(members)

def main(argv):
#     logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')
    members = get_all_members()
    if (argv):
        command = argv[0]
        parameters = argv[1:]
        
        f = members[command]
        return f(*parameters)
    else:
        print "available commands:" + str(members)

if __name__ == '__main__':
    print_return = main(sys.argv[1:])
    if print_return is not None:
        print 'return:'+str(print_return)

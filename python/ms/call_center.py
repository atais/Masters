'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
from inspect import getmembers, isfunction
import logging
import sys

import analyse

import binary_network
import check_network
import config
import facilities
import network
import network_to_graph
import organise
import utils


def get_all_members():
    members = []
    members.extend(getmembers(analyse, isfunction))
    members.extend(getmembers(binary_network, isfunction))
    members.extend(getmembers(check_network, isfunction))
    members.extend(getmembers(config, isfunction))
    members.extend(getmembers(facilities, isfunction))
    members.extend(getmembers(network, isfunction))
    members.extend(getmembers(network_to_graph, isfunction))
    members.extend(getmembers(organise, isfunction))
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

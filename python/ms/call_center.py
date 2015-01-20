'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
import sys
import analyse as analyse
import binary_network as binary_network
import facilities as facilities
import network as network
import organise as organise
import config as config
from inspect import getmembers, isfunction


def get_all_members():
    members = []
    members.extend(getmembers(analyse, isfunction))
    members.extend(getmembers(binary_network, isfunction))
    members.extend(getmembers(facilities, isfunction))
    members.extend(getmembers(network, isfunction))
    members.extend(getmembers(organise, isfunction))
    members.extend(getmembers(config, isfunction))
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

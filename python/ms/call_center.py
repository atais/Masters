'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
import sys
import ms.analyse as analyse
import ms.binary_network as binary_network
import ms.facilities as facilities
import ms.network as network
import ms.organise as organise
import ms.config as config
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
    print 'return:'+str(print_return)

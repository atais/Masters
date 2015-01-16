'''
Created on 10 sty 2015

@author: michalsiatkowski
'''
# import logging
import sys
import analyse
import logging

def main():
    analyse.not_one_way('../scenarios/siouxfalls/network.xml')
    analyse.same_pos_crossroad('../scenarios/siouxfalls/network.xml')
    print 'Number of arguments:', len(sys.argv), 'arguments.'

if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')
    main()
   



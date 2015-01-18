'''
Created on 10 sty 2015

@author: michalsiatkowski

THIS FILE IS FOR JAVA PROJECT TEST
TO TEST THE APACHE EXEC COMMANDS

'''
import sys
import logging


def main():
    logging.warn("warn")
    logging.info("info")
    logging.debug("debug")
    print 'Number of arguments:', len(sys.argv), 'arguments.'
    logging.fatal("fatal")
    return "[0,1,1,0]"

if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')
    val = main()
    print "return: "+val



'''
Created on 10 lut 2015

@author: michalsiatkowski
'''
import logging
import os
import unittest

from ms.binary_to_graph import binary_to_graph
import ms.network as network
from tests.utils import r
import shutil
import zipfile


class Test(unittest.TestCase):

    def setUp(self):
        logging.basicConfig(level=logging.DEBUG, format='%(asctime)s %(message)s')
        if os.path.exists(r('output/output')):
            shutil.rmtree(r('output/output'))
            
        with zipfile.ZipFile(r('resources/output.zip'), "r") as z:
            z.extractall(r("output/"))
            

    def test_binary_to_xml(self):
        output = r('output/output')

        dirs = [ x for x in os.listdir(output) if (not x.startswith('.'))]
        for cdir in dirs:
            chromo = open(output + '/' + cdir + "/chromosome.txt", "r").readline()
            mother_file = r('resources/network.xml')
            graph = binary_to_graph(chromo, mother_file) 
            network.save_graph(graph, r(output + '/' + cdir + '/network2'))

if __name__ == "__main__":
    # import sys;sys.argv = ['', 'Test.test_binary_to_xml']
    unittest.main()

'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
import unittest
from ms import xml_to_binary
from tests.utils import r
import os
import logging


class Test(unittest.TestCase):

    def setUp(self):
        logging.basicConfig(level=logging.INFO, format='%(asctime)s %(message)s')
        if os.path.exists(r('output/binary-network.xml')):
            os.remove(r('output/binary-network.xml'))

    def test_xml_to_binary(self):
        result = xml_to_binary.xml_to_binary(r('resources/default-network.xml'))
        
        print result
        self.assertIsNotNone(result, "parser error")
        self.assertEqual(76, result.count("1"), "wrong size!")
    


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testFromXMLtoBinary']
    unittest.main()
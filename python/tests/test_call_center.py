'''
Created on 18 sty 2015

@author: michalsiatkowski
'''
import unittest
from ms import call_center
from tests.utils import r


class Test(unittest.TestCase):


    def testMain(self):
        source = (r('resources/default-network.xml'))
        output = (r('output/network-analised.xml'))
        
        call_center.main(['analyse_and_save', str(source), str(output)])

    def testMain2(self):
        source = (r('resources/default-network.xml'))
        
        result = call_center.main(['xml_to_binary', str(source)])
        self.assertIsNotNone(result)





if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testMain']
    unittest.main()
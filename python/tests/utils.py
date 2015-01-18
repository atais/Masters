'''
Created on 18 sty 2015

@author: michalsiatkowski
'''

import os
def r(name):
    '''
    just for stupid import fix
    '''
    script_dir = os.path.dirname(__file__)
    return os.path.join(script_dir, name)

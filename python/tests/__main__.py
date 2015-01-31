import unittest
from . import prepare_load_tests_function, __path__

load_tests = prepare_load_tests_function(__path__)
unittest.main()
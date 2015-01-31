import unittest

def prepare_load_tests_function (the__path__):
    test_suite = unittest.TestLoader().discover(the__path__[0])
    def load_tests (_a, _b, _c):
        return test_suite
    return load_tests
import unittest

class HelloWorldTest(unittest.TestCase):
    
    def setUp(self):
        self.msg = "Hello World!"

    def test_message(self):
        self.assertEquals("Hello World!", self.msg)




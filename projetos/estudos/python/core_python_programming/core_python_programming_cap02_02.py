#!usr/bin/env python

def foo(debug=True):
	if debug:
		print 'in debug mode'
	print 'done'

foo()
foo(False)

class FooClass(object):
	"""My very first class: Fooclass"""
	version = 0.1 # class (data) attribute
	def __init__(self, nm='Bob'):
		"""constructor"""
		self.name = nm
		print 'created a class instance'
	
	def showname(self):
		"""display instance attribute name"""
		print "Your name is ", self.name
		print "My name is", self.__class__.__name__
	
	def showver(self):
		"""display class(static) attrinute"""
		print self.version
	
	def addMe2Me(self, x):
		"""apply + operation to argument"""
		return x + x

obj = FooClass()
obj.showname()
obj.showver()
print obj.addMe2Me(2)
#help(FooClass)	
	
import sys
print ''
sys.stdout.write('Hello world!')
print sys.platform
print sys.version

print '-' * 20
print dir(obj)	

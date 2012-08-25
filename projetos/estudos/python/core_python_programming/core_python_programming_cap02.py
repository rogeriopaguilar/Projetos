Python 2.7.3 (default, Aug  1 2012, 05:14:39) 
[GCC 4.6.3] on linux2
Type "copyright", "credits" or "license()" for more information.
==== No Subprocess ====
>>> print 'Hello World'
Hello World
>>> print '%s is number %d' % ('Python',1)
Python is number 1
>>> _
Traceback (most recent call last):
  File "<pyshell#2>", line 1, in <module>
    _
NameError: name '_' is not defined
>>> import sys
>>> print >> sys.stderr, 'Fatal Error: invalid input!'
Fatal Error: invalid input!
>>> logfile = open('/tmp/mylog.txt','a')
>>> print >> logfile, 'Fatal Error: Invalid input!'
>>> logfile.close()
>>> from __future__ import print_function
>>> print('Fatal error: invalid input!',file=sys.stderr)
Fatal error: invalid input!
>>> user = raw_input('Enter login name:')
Enter login name:teste
>>> print 'Your login is:', user
SyntaxError: invalid syntax
>>> print('Your login is: %s' % user)
Your login is: teste
>>> num = raw_input('Now enter a number:')
Now enter a number:10
>>> print 'Doubling your number: %d' % (int(num) * 2)
SyntaxError: invalid syntax
>>> print('Doubling your number: %d' % (int(num) * 2))
Doubling your number: 20
>>> #one comment
>>> print 'Hello World!' #another comment
SyntaxError: invalid syntax
>>> print ('Hello World!') #another comment
Hello World!
>>> def foo():
	"This is a doc string."
	return Ture

>>> def foo():
	"This is a doc string."
	return True

>>> help(foo)
Help on function foo in module __main__:

foo()
    This is a doc string.

>>> print(-2*4+3**2)
1
>>> 2<4
True
>>> 2==4
False
>>> 2>4
False
>>> 6.2<=6
False
>>> 6.2<=6.2
True
>>> 6.2<=6.20001
True
>>> 2<4 and 2==4
False
>>> 2>4 or 2<4
True
>>> not 6.2<=6
True
>>> 3<4<5
True
>>> counter = 0
>>> miles = 1000.0
>>> name = 'Bob'
>>> counter = counter + 1
>>> kilometers = 1.609 * miles
>>> print '%f miles is the same as %f km' % (miles, kilometers)
SyntaxError: invalid syntax
>>> print('%f miles is the same as %f km' % (miles, kilometers))
1000.000000 miles is the same as 1609.000000 km
>>> pystr='Python'
>>> iscool='is cool!'
>>> pystr[0]
'P'
>>> pystr[2:5]
'tho'
>>> iscool[:2]
'is'
>>> iscool[3:]
'cool!'
>>> iscool[-1]
'!'
>>> pystr + iscool
'Pythonis cool!'
>>> 
>>> pystr + ' ' + iscool
'Python is cool!'
>>> pystr * 2
'PythonPython'
>>> '-'*20
'--------------------'
>>> pystr='''python
is cool'''
>>> pystr
'python\nis cool'
>>> print pystr
SyntaxError: invalid syntax
>>> print(pystr)
python
is cool
>>> 
>>> iscool[:2]
SyntaxError: invalid syntax
>>> 
>>> 
>>> aList=[1,2,3,4,5]
>>> 
>>> aList
[1, 2, 3, 4, 5]
>>> aList[0]
1
>>> aList[2:]
[3, 4, 5]
>>> aList[:3]
[1, 2, 3]
>>> aList[1]=5
>>> aList
[1, 5, 3, 4, 5]
>>> aTuple=('robots', 77, 93, 'try')
>>> aTuple
('robots', 77, 93, 'try')
>>> aTuple[:3]
('robots', 77, 93)
>>> aTuple[1]=5
Traceback (most recent call last):
  File "<pyshell#77>", line 1, in <module>
    aTuple[1]=5
TypeError: 'tuple' object does not support item assignment
>>> aDict={'host':'earth'}
>>> aDict['port']=80
>>> aDict
{'host': 'earth', 'port': 80}
>>> aDict.keys()
['host', 'port']
>>> adict['host']
Traceback (most recent call last):
  File "<pyshell#82>", line 1, in <module>
    adict['host']
NameError: name 'adict' is not defined
>>> aDict['host']
'earth'
>>> for key in aDict:
	print key, aDict[key]
	
SyntaxError: invalid syntax
>>> for key in aDict:
	print(key, aDict[key])

	
host earth
port 80
>>> x=0
>>> if x < 0:
	print "x<0"
	
SyntaxError: invalid syntax
>>> if x<0:
	print(x<0)
elif x == 0:
	print "x=0"
	
SyntaxError: invalid syntax
>>> if x<0:
	print "x<0"
	
SyntaxError: invalid syntax
>>> if x<0:
	print("x<0")
elif x ==0:
	print("x=0")
else:
	print("x>0")

x=0
>>> counter = 0
>>> while counter < 3:
	print 'loop #%d' % counter
	
SyntaxError: invalid syntax
>>> while counter < 3:
	print('loop #%d' % counter)

	
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
loop #0
Traceback (most recent call last):
  File "<pyshell#110>", line 2, in <module>
    print('loop #%d' % counter)
  File "/usr/lib/python2.7/idlelib/PyShell.py", line 1263, in write
    self.shell.write(s, self.tags)
  File "/usr/lib/python2.7/idlelib/PyShell.py", line 1252, in write
    raise KeyboardInterrupt
KeyboardInterrupt
>>> while counter < 3:
	print("counter #%d" % counter)
	counter = counter + 1

	
counter #0
counter #1
counter #2
>>> for item in ['e-mail','chat','homework']:
	print(item)

	
e-mail
chat
homework
>>> for item in ['chat','homework','e-mail']:
	print item,
	
SyntaxError: invalid syntax
>>> for item in ['chat','homework','r-mail']:
	print(item,)

	
chat
homework
r-mail
>>> print
<built-in function print>
>>> for eachNum in range(3):
	print(eachNum)

	
0
1
2
>>> foo='abc'
>>> for c in foo
SyntaxError: invalid syntax
>>> for c in foo:
	print c
	
SyntaxError: invalid syntax
>>> for c in foo:
	print(c)

	
a
b
c
>>> for c in range(len(foo)):
	print foo[c], '(%d)' % i
	
SyntaxError: invalid syntax
>>> for c in range(len(foo)):
	print (foo[c], '(%d)' % c)

	
a (0)
b (1)
c (2)
>>> enumerate(foo)
<enumerate object at 0x273f500>
>>> print enumerate(foo)
SyntaxError: invalid syntax
>>> print(enumerate(foo))
<enumerate object at 0x273f550>
>>> for i, ch in enumerate(foo):
	print (ch, '(%d)' % i)

	
a (0)
b (1)
c (2)
>>> squared=[x**2 for x in range(4)]
>>> for i in squared:
	print(i)

	
0
1
4
9
>>> sqdEvents=[x**2 for x in range(8) if not x % 2]
>>> for i in sqdEvents:
	print(i)

	
0
4
16
36
>>> filename=raw_input('Enter file name:')
Enter file name:/tmp/mylog.txt
>>> fobj = open(filename,'r')
>>> data=fojb.readlines()
Traceback (most recent call last):
  File "<pyshell#155>", line 1, in <module>
    data=fojb.readlines()
NameError: name 'fojb' is not defined
>>> data=fobj.readlines()
>>> fobj.close()
>>> for eachline in data:
	print(eachline,)

	
Fatal Error: Invalid input!

>>> try:
	filename=raw_input('Enter filename:')
	fobj=open(filename,'r')
	for eachline in fobj:
		print(eachline,)

	fobj.close()
except IOError, e:
	print 'file open error:', e
	
SyntaxError: invalid syntax
>>>  try:
	filename=raw_input('Enter filename:')
	fobj=open(filename,'r')
	for eachline in fobj:
		print(eachline,)

	fobj.close()
except IOError, e:
	
  File "<pyshell#170>", line 1
    try:
   ^
IndentationError: unexpected indent
>>> try:
	filename=raw_input('enter filename:')
	fobj=open(filename,'r')
	data=fobj.readlines()
	fobj.close()
	for eachline if data:
		
SyntaxError: invalid syntax
>>> def foo(debug=True):
      'determine if in debug mode'
      if debug:
        print('In debug mode')
      print('done')



















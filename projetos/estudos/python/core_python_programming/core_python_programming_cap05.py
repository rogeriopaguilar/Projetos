#!/usr/bin/env python
#-*- encoding: utf-8 -*-

#from __future__ import division #to turn floor division to true division, as it will be in python 3
from decimal import Decimal

def printType(number):
    "prints the number's type name"
    print type(number).__name__
    if isinstance(number, complex):
        print "Complex number --> ", number.real, number.imag, number.conjugate()
         
aInt = -1
aLong = -9999999999999999999999L
aFloat = 3.14984984398493849348934849348
aComplex = 1.23+4.56J

printType(aInt)
printType(aLong)
printType(aFloat)
printType(aComplex)

del aInt
print 9999 ** 8
print 0b010 #binary
print 0o010 #octal
print 0x010 #hexadecimal
print (2 << 1) #shifting



print 1 / 2 #floor division
print 1.0 / 2 #true division

print 1 // 2 #forces floor division


print 3 ** 2
print -3 ** 2 #caution with precedence!
print (-3) ** 2
print 4 ** -1   


print ~3 # -(num + 1)
print 3 << 1 
print 3 << 2
print 4 >> 1
print 1 & 0
print 1 ^ 1 #xor
print 1 | 1 #or

print cmp(1,1)
print str(1)
print int(2.1)
print long(2)
print float(2)
print complex(2)
print int('0b01',2) #(number, base)
 
print abs(-1)
print coerce(1.3, 134L)
print divmod(10, 3) #will return (classic division, modulos operation)
print divmod(2.5, 10)
print pow(2, 5)
print round(3.49999)
print round(3.49999, 1)

import math
for eachNum in range(10):
    print round(math.pi, eachNum)

for eachNum in (.2, .7, 1.2, 1.7, -.2, -.7, -1.2, -1.7):
    print "int (%1f)\t%+.1f" % (eachNum, float(int(eachNum)))
    print "floor (%1f)\t%+.1f" % (eachNum, math.floor(eachNum))
    print "round (%1f)\t%+.1f" % (eachNum, round(eachNum))
    print "-" * 20

print hex(255)
print oct(255)

print ord('a')
print chr(97)
print bool(1), bool(0), bool('1'), bool('0'), bool([]), bool((1,))



class ABC(object):
    def __nonzero__(self):
        return False

class DEF(object):
    pass

obj = ABC()
print bool(obj)

obj = DEF()
print bool(obj)

print Decimal('.1') + Decimal('1.0')    




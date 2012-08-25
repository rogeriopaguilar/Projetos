#!/usr/bin/env python
#-*- encoding: utf-8 -*-

def printBooleanValue(obj):
    "imprime o valor booleano correspondente ao objeto"
    
    if obj:
        print "True"
    else:
        print "False"

def displayNumtype(num):
    "imprime o tipo caso seja um valor numérico"
    
    print num, " is ",
    if isinstance(num,(int, long, float, complex)):
        print " a number of type", type(num).__name__
    else:
        print " not a number att all"

print type(42)
print type('áè')

print type(type(42))

#objetos que possuem valor False no contexto de um objeto Boolean
printBooleanValue(False)
printBooleanValue(None)
printBooleanValue(0)
printBooleanValue(0.0)
printBooleanValue(0L)
printBooleanValue("")
printBooleanValue([])
printBooleanValue(())
printBooleanValue({})



printBooleanValue((1))





foostr="abcde"
print foostr[::-1]
print foostr[::-2]

foolist=[123,'xba',342.23,'abc']
print foolist[::-1]

print 2 == 2
print 2.46 <= 8.33
print [3, 'abc'] == [3, 'abc']
print 3 < 4 < 7 # 3 < 4 and 4 < 7




a = b = 5
print a is b
print a is not b
print a == b
print id(a) == id(b)
print id(a)
print id(b)


displayNumtype(10)
displayNumtype(100000000000000000000000000)
displayNumtype(10j)
displayNumtype('aaa')

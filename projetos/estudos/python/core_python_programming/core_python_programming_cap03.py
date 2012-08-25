#!/usr/bin/env python
#-*- encoding: utf-8 -*-

#string contendo múltiplas linhas
print '''Exemplo de string contendo
múltiplas linhas'''

go_surf, get_a_tan_while, boat_size, toll_money = (1,
'windsurfing', 40.0, -2.0)

#múltiplos comandos na mesma linha
import sys;x='foo';sys.stdout.write(x + '\n')

#swapping
x, y = 1, 2
x, y = y, x
print x, y	

def teste():
	print "Teste"

print "__name__ = %s" % __name__

if __name__ == 'main':
	teste()

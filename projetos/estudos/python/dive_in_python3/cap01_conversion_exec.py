#!/usr/bin/env/python
#Exemplo do livro dive in python 3 - cap01. Escrito por Rogério de Paula Aguilar - 07/2012


import sys
sys.path.insert(0, '/home/duque/Dropbox/estudos/python/dive_in_python3')
import cap01_conversion as cap01

print(__name__)
if __name__ == '__main__':
	print(cap01.approximate_size.__doc__)
	print(cap01.__name__)
	print(cap01.approximate_size(1000000000000, False))
	print(cap01.approximate_size(1000000000000))
		
	try:
		import abcaaaaaaa
	except ImportError:
		print "Erro ao importar!"

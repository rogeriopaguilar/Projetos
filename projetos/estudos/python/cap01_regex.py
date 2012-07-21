#/usr/bin/env python
#-*- coding: utf-8 -*-

#Exemplos do capítulo sobre expressões regulares do livro Core Python Applications Programming 3 edição com algumas alterações
#07/2012 - Rogério de Paula Aguilar - rogeriodpaguilarbr@gmail.com

import re
import os

def comeco(titulo):
	print "\n--------------------------------------------------------"
	print titulo
	print "----------------------------------------------------------\n"

def final():
	print "----------------------------------------------------------\n"

#Math irá procurar a RE à partir do começo da String, já search vai tentar encontrar em qualquer lugar da string

comeco("exemplo de expressão regular básica com match")
m = re.match('foo','foo') #tenta a er com uma string
print m #m será um objeto do tipo match
if m is not None: #se foi encontrado o padrão, retorna um valor <> None
	print "Resultado: %s" % m.group()
final()

comeco("segundo exemplo com match")
try:
	#m = re.match('foo',raw_input('Informe uma RE (para dar certo deve começar com foo):')).group()
	m = re.match('foo','foo aaaa').group()
	print 'Segundo exemplo:', m
except Exception,e:
	print "Erro! -->", e
finally:
	print "Exemplo bloco finally"
final()


comeco("Primeiro exemplo com search, que irá procurar a RE em qualquer lugar da string")
m = re.match('foo','seafood') #match irá falhar porque irá procurar à partir do começo da string
if m is not None: print m.group()
else: print "Padrão não encontrado com match!"

m=re.search('foo','seafood') #Irá encontrar porque match procura por toda a string
if m is not None: print "Padrão com sdearch:", m.group()
else: print "Padrão não encontrado com search!"
final()


comeco("Exemplos com mais de uma opção (|)")
bt = "bat|bet|bit"
m = re.match(bt,"bat")
if m is not None: print "Padrão encontrado com match:", m.group()
else: print "Padrão não encontrado!"

m = re.match(bt,"blt")
if m is not None: print "Padrão encontrado com match:", m.group()
else: print "Padrão não encontrado!"

m = re.match(bt,"He bit me!");
if m is not None: print "Padrão encontrado com match:", m.group()
else: print "Padrão não encontrado!"

m = re.search(bt,"He bit me!");
if m is not None: print "Padrão encontrado com search:", m.group()
else: print "Padrão não encontrado!"

anyend = '.end' #qualquer palavra terminada com end. O "." significa qualquer caracter menos o \n

comeco("Exemplos com o caracter . que significa qualquer caracter")
m = re.match(anyend, 'bend')
if m is not None: print "Padrão encontrado com match:", m.group()
else: print "Padrão não encontrado!"

m = re.match(anyend, 'end')
if m is not None: print "Padrão encontrado com match:", m.group()
else: print "Padrão não encontrado!"

m = re.match(anyend, '\nend') # 
if m is not None: print "Padrão encontrado com match:", m.group()
else: print "Padrão não encontrado!"

m = re.search('.end', 'The end.')
if m is not None: print "Padrão encontrado com search:", m.group()
else: print "Padrão não encontrado!"

patt314 = '3.14'              # regex dot
pi_patt = '3\.14'             # literal dot (dec. point)

m = re.match(pi_patt, '3.14')  # exact match
if m is not None: print "Padrão encontrado com search:", m.group()
else: print "Padrão não encontrado!"

m = re.match(patt314, '3014')  # dot matches '0'
if m is not None: print "Padrão encontrado com search:", m.group()
else: print "Padrão não encontrado!"

m = re.match(patt314, '3.14')  # dot matches '.'
if m is not None: print "Padrão encontrado com search:", m.group()
else: print "Padrão não encontrado!"

final()

comeco("Classes de caracteres utilizando []")
m = re.match('[cr][23][dp][o2]','c3po')
if m is not None: print "Padrão encontrado com match", m.group()
else: print "Padrão não encontrado com match!"

m = re.match('[cr][23][dp][o2]','c2do')
if m is not None: print "Padrão encontrado com match", m.group()
else: print "Padrão não encontrado com match!"

m = re.match('c3po|r2d2','c3po')
if m is not None: print "Padrão encontrado com match", m.group()
else: print "Padrão não encontrado com match!"

m = re.match('c3po|r2d2','r2d2')
if m is not None: print "Padrão encontrado com match", m.group()
else: print "Padrão não encontrado com match!"

comeco("Exemplos com repetição, caracteres especiais e grupos")
#regexpemail='\w+@(\w+\.)?\w+\.com'
regexpemail='\w+@(\w+\.)*\w+\.com'
print "RegExp: %s" % regexpemail
print re.match(regexpemail, "rogeriopaguilar@terra.aaa.com.br").group()

#exemplo com grupos
m = re.match("(\w\w\w)-(\d\d\d)","abc-123")
print "RegExp: %s" % "(\w\w\w)-(\d\d\d)", 
print os.linesep, m.group(), os.linesep, m.group(1),os.linesep, m.group(2),os.linesep, m.groups()

m = re.match('ab', 'ab')  
print m.group()                       # entire match
print m.groups()                      # all subgroups

m = re.match('(ab)', 'ab')      # one subgroup
print m.group()                       # entire match
print m.group(1)                      # subgroup 1
print m.groups()                      # all subgroups

m = re.match('(a)(b)', 'ab')    # two subgroups
print m.group()                       # entire match
print m.group(1)                      # subgroup 1
print m.group(2)                      # subgroup 2
print m.groups()                      # all subgroups

m = re.match('(a(b))', 'ab')    # two subgroups
print m.group()                       # entire match
print m.group(1)                      # subgroup 1
print m.group(2)                      # subgroup 2
print m.groups()                      # all subgroups

final()

comeco("Exemplos com símbolos para capturar à aprtir do início ou final das palavras")

m = re.search("^The", "The end.") #^ significa início da string
if m is not None:
	print m.group()
else:
	print "Não encontrado com search!"

m = re.search("^The", "end. The") #Não irá encontrar porque o The não está no início
if m is not None:	print m.group()
else: print "Não encontrado!"

m = re.search(r"\bthe", "bite the dog") #limite de uma palavra
if m is not None:	print m.group()
else: print "Não encontrado!"

m = re.search(r"\bthe", "bitethe dog")
if m is not None:	print m.group()
else: print "Não encontrado!"

m = re.search(r"\Bthe", "bitethe dog") #\B é o contrário de \b
if m is not None:	print m.group()
else: print "Não encontrado!"


final()

#exemplos com findall e finditer
comeco("exemplos com findall e finditer")

print re.findall('car','car') #findall retorna uma lista contendo todas as ocorrências da RE ou uma lista vazia
print re.findall('car','scary') 
print re.findall('car','carry the barcardi to the car')

s = 'This and that'
print re.findall(r'(th\w+) and (th\w+)', s, re.I)
print re.finditer(r'(th\w+) and (th\w+)', s, re.I).next().groups()
print re.finditer(r'(th\w+) and (th\w+)', s, re.I).next().group(1)
print re.finditer(r'(th\w+) and (th\w+)', s, re.I).next().group(2)
print [g.groups() for g in re.finditer(r'(th\w+) and (th\w+)', s, re.I)]
print re.findall(r'(th\w+)', s, re.I)
it = re.finditer(r'(th\w+)', s, re.I)
g = it.next()
print g.groups()
print g.group(1)
g = it.next()
print g.groups()
print g.group(1)
print [g.group(1) for g in re.finditer(r'(th\w+)', s, re.I)]

final()

comeco("Exemplos com substituição utilizando sub e subn")

print re.sub('X', 'Mr. Smith', 'attn: X\n\nDear X,\n')
print re.subn('X', 'Mr. Smith', 'attn: X\n\nDear X,\n')
print re.sub('[ae]', 'X', 'abcdef')
print re.subn('[ae]', 'X', 'abcdef')

#exemplo convertendo datas do formato americano para o brasileiro
print re.sub(r'(\d{1,2})/(\d{1,2})/(\d{2}|\d{4})',r'\2/\1/\3','2/20/91')
print re.sub(r'(\d{1,2})/(\d{1,2})/(\d{2}|\d{4})',r'\2/\1/\3','2/20/1991')

final()




comeco("Exemplos com split")
DATA = (
     'Mountain View, CA 94040',
     'Sunnyvale, CA',
     'Los Altos, 94023',
     'Cupertino 95014',
    'Palo Alto CA',
 )
for datum in DATA:
     print re.split(', |(?= (?:\d{5}|[A-Z]{2})) ', datum)
final()

#exemplos com a notação (?...) que especifica as flags da RE na própria RE ao invés de um parâmetro

comeco("Exemplos com a notação (?...)")
print re.findall(r'(?i)yes','yes. Yes? YES!!!') #(?i) é o mesmo que re.I, ou seja, case insensitive
print re.findall(r'(?i)th\w+', 'The quickest way is through this tunnel.')
print re.findall(r'(?im)(th[\w ]+)',
"""
 This line is the first,
 another line,
 that line, it's the best
""")

#sem o caracter de nova linha (ver exemplo abaixo)
print re.findall(r'th.+', '''
 The first line
 the second line
 the third line
''')

#(?s) indica que o caracter de nova linha (\n) irá ser recuperado também
print re.findall(r'(?s)th.+', '''
 The first line
 the second line
 the third line
''')


final()


raw_input();

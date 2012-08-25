#!/usr/bin/env python

'Cria arquivos texto'

import os

while True:
    fname = raw_input('Enter file name:')
    if os.path.exists(fname):
        print "ERROR. File %s already exists." % fname
    else:
        break;

all = []
print "\nEnter lines (. to quit)\n."

while True:
    line = raw_input("> ")
    if line == ".":
        break
    else:
        all.append(line)

fobj = open(fname, 'w')
fobj.write('\n'.join(all));
fobj.close()

print "Done!"


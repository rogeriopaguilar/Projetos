#!/usr/bin/env python

'Shows file content'

import os

fname = raw_input('Enter filename:')
print


try:
    fobj = open(fname, 'r')
except IOError, e:
    print '*** file open error ***', e
else:
    for line in fobj:
        print line,
    fobj.close()


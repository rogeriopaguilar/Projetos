#!/usr/bin/env python
#-*- encoding: utf-8 -*-

class QuickUnionWF(object):
    """This class implements the weighted QuickUnion operation as an exercise
    for the course Algorithms 1 from the website www.coursera.org
    2013 - Rogério de Paula Aguilar
    """
    
    def __init__(self, numNodes):
        """Constructor. Sets the number of nodes ant initializes an array in which each member
        has the array index id."""
        
        if numNodes < 1: raise ValueError("numNodes must be > 0!")
        self.nodes = range(numNodes)
        self.sizes = [0 for n in self.nodes]

    def printSizes(self):
        """prints the self.sizes contents"""
        
        print self.sizes;

    
    def printNodes(self):
        """prints the self.nodes contents"""
        
        print self.nodes;
        
    def checkNodes(self, nodeStart, nodeEnd):
        """Checks the size of the array self.nodes against the provided indexes 
            and throws a ValueError in case these indexes are invalid
            nodeStart must be >= 0
            nodeEnd must be <= len(self.nodes)-1
            """
            
        size = len(self.nodes)-1;
        valid = ( nodeStart >= 0 and nodeEnd <= size  );
        if not valid: 
            raise ValueError("nodeStart must be >= 0 and nodeEnd must be <= " + str(size)); 
    
    def union(self, nodeStart, nodeEnd):
        """ This method creates a connection between two nodes. The root of nodeStart 
        becomes the root of nodeEnd """
        
        self.checkNodes(nodeStart, nodeEnd)
        rootNodeStart = self.root(nodeStart)
        rootNodeEnd = self.root(nodeEnd)
        self.nodes[rootNodeStart] = self.nodes[rootNodeEnd]
        
    
    def connected(self, nodeStart, nodeEnd):
        """This method returns True if the nodes are connected or false otherwise. Two nodes
        are connected if they have the same root. This operation makes 2 array accesses."""
        
        self.checkNodes(nodeStart, nodeEnd)
        return self.root(nodeStart) == self.root(nodeEnd)

    def root(self, nodeIndex):
        """Returns the root of the self.nodes[nodeIndex]"""
        
        self.checkNodes(nodeIndex,nodeIndex)
        while nodeIndex <> self.nodes[nodeIndex]:
            nodeIndex = self.nodes[nodeIndex]
        return nodeIndex
        
        
if __name__ == "__main__":
    q = QuickUnionWF(10)
    q.printNodes()
    q.printSizes()
    """
    q.union(4,3)
    
    q.printNodes()
    q.union(3,8)
    q.printNodes()
    q.union(6,5)
    q.printNodes()
    q.union(9,4)
    q.printNodes()
    q.union(2,1)
    q.printNodes()
    print q.connected(3,4)
    print q.connected(4,5)
    q.union(5,0)
    q.printNodes()
    q.union(7,2)
    q.printNodes()
    q.union(6,1)
    q.printNodes()
    q.union(7,3)
    q.printNodes()
    """
    

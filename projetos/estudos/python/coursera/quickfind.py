#!/usr/bin/env python
#-*- encoding: utf-8 -*-

class QuickFind(object):
    """This class implements the QuickFind operation as an exercise
    for the course Algorithms 1 from the website www.coursera.org
    2012 - Rogério de Paula Aguilar
    """
    
    def __init__(self, numNodes):
        """Constructor. Sets the number of nodes ant initializes an array in which each member
        has the array index id."""
        
        if numNodes < 1: raise ValueError("numNodes mest be > 0!")
        self.nodes = range(numNodes)
    
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
        """ This method creates a connection between two nodes. Two nodes are connected
        if they have the same id. To do this operation, all the nodes that have the id
        of nodeStart are updated to have the nodeEnd id. This operation makes at most 2N + 2
        array accesses"""
        
        self.checkNodes(nodeStart, nodeEnd);
        cont = 0;
        nodeStartId = self.nodes[nodeStart]
        nodeEndId = self.nodes[nodeEnd]
        for cont,node in enumerate(self.nodes):
            if node == nodeStartId:
                self.nodes[cont] = nodeEndId
            #cont = cont + 1
                
    def connected(self, nodeStart, nodeEnd):
        """This method returns True if the nodes are connected or false otherwise. Two nodes
        are connected if they have the same id. This operation makes 2 array accesses."""
        
        self.checkNodes(nodeStart, nodeEnd);
        return self.nodes[nodeStart] == self.nodes[nodeEnd]
    
if __name__ == "__main__":
    q = QuickFind(10)
    q.printNodes()
    q.union(4,3)
    q.printNodes()
    q.union(3,8)
    q.printNodes()
    print q.connected(4,8)
    print q.connected(6,5)
    q.union(1,8)
    print q.connected(1,3)
    
    
    #9,4
    #2,1

    

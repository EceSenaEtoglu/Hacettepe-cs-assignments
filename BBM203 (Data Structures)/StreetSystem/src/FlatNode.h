//
// Created by b2210356016 on 14.11.2022.
//

#ifndef ASM2_FLATNODE_H
#define ASM2_FLATNODE_H


#include "Flat.h"

class FlatNode {

public:

    Flat data;

    FlatNode* prev = nullptr;
    FlatNode* next = nullptr;

    // constructor
    FlatNode(int id,int initialBandWidth) : data(id,initialBandWidth) {
        prev = nullptr;
        next = nullptr;
    }

    // destructor
    ~FlatNode() {
        FlatNode::prev = nullptr;
        FlatNode::next = nullptr;
    }

};


#endif //ASM2_FLATNODE_H

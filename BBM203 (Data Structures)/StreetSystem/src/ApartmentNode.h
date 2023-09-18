//
// Created by b2210356016 on 14.11.2022.
//

#ifndef ASM2_APARTMENTNODE_H
#define ASM2_APARTMENTNODE_H


// circular linkedlist node of data type Apartment

#include "Apartment.h"

class ApartmentNode {

public:

    Apartment data;

    ApartmentNode* next = nullptr;

    // constructor
    ApartmentNode(std::string apartmentName,int maxBandValue): data(apartmentName,maxBandValue) {
        next = nullptr;
    }

    //destructor
    ~ ApartmentNode() {
        next = nullptr;
    }


};


#endif //ASM2_APARTMENTNODE_H

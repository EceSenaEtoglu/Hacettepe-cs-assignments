//
// Created by b2210356016 on 6.12.2022.
//

#ifndef ASM3V2_CUSTOMERNODE_H
#define ASM3V2_CUSTOMERNODE_H


#include "Customer.h"

class CustomerNode {

public:

    Customer data;
    CustomerNode* next;


    CustomerNode(Customer givenData) : data(givenData) {

        next = nullptr;
    }


};

#endif //ASM3V2_CUSTOMERNODE_H

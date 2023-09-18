//
// Created by b2210356016 on 3.12.2022.
//

#ifndef ASM3V2_CASHIER_H
#define ASM3V2_CASHIER_H

#include <cfloat>
#include "Customer.h"

class Cashier {

public:
    double calculatedIdleTime = DBL_MAX;
    double totalWorkTime = 0;

    int isIdleFlag = 1;

    int id;

    Customer currCustomer;

    Cashier(int id) {

        this->id = id;

        //default
        totalWorkTime = 0;

        makeIdle();
    }

    void makeIdle() {

        calculatedIdleTime = DBL_MAX;
        isIdleFlag = 1;
        currCustomer = Customer();

    }

};



#endif //ASM3V2_CASHIER_H

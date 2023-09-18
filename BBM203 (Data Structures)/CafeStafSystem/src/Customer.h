//
// Created by b2210356016 on 3.12.2022.
//

#ifndef ASM3V2_CUSTOMER_H
#define ASM3V2_CUSTOMER_H

#include <string>

class Customer {

public:

    //arrival to coffee shop
    double arrivTimeToCs = 0;
    double orderTime = 0;
    double brewTime = 0;
    double priceOfCoffe = 0;

    // arrival to barista time
    double arrivTimeToBarista =0;
    double timeCoffeeDone = 0;

    Customer() {

        arrivTimeToCs = 0;
        orderTime = 0;
        brewTime = 0;
        priceOfCoffe = 0;

        timeCoffeeDone = 0;

    }

    Customer(double arrivalToCsTime,double orderTime,double brewTime,double priceOfCoffe) {

        this->arrivTimeToCs = arrivalToCsTime;
        this->orderTime = orderTime;
        this->brewTime = brewTime;
        this->priceOfCoffe = priceOfCoffe;

        arrivTimeToBarista = 0;
    }


    static void orderByArrival(std::vector<Customer>& v ) {

        for (int i = v.size()-1; i>=0;i--) {

            for(int j = 0; j<i;j++) {

                //swap
                if(v[j].arrivTimeToCs > v[j+1].arrivTimeToCs) {

                    Customer temp = v[j+1];
                    v[j+1] = v[j];
                    v[j] = temp;

                }
            }
        }

    }

};



#endif //ASM3V2_CUSTOMER_H

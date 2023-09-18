//
// Created by b2210356016 on 6.12.2022.
//


#ifndef ASM3V2_COFFEESHOPMODEL1_H
#define ASM3V2_COFFEESHOPMODEL1_H

#include <vector>
#include "Customer.h"
#include "CashierSystem.h"
#include "BaristaSystem.h"
#include "Helpers.h"


class CoffeeShopModel1{

private:

    std::vector<Customer> customers;
    int cashierNum;

    double totalRunningTime = 0;
    int maxQueCash = 0;
    int maxQueBarista = 0;

    std::vector<double> cashUnitUtils;
    std::vector<double> baristaUnitUtils;
    std::vector<double> turnAroundTimes;

    std::vector<double> outputs;

public:

    CoffeeShopModel1(int cashierNum,std::vector<Customer> customers) {

        this->customers = customers;
        this->cashierNum = cashierNum;
    }


public:

    void startModel1() {

        //cashier system
        CashierSystem cashierSystem(cashierNum,customers, false);
        cashierSystem.start();
        maxQueCash = cashierSystem.getMaxQueLength();
        std::vector<Customer> toBarista = cashierSystem.getCustomersToBarista()[0];


        //barista system
        BaristaSystem baristaSystem(cashierNum/3,toBarista);
        baristaSystem.start();
        std::vector<Customer> toLeave = baristaSystem.getCustomersToLeave();
        maxQueBarista = baristaSystem.getMaxQueLength();

        //check the time of last person to leave
        totalRunningTime = baristaSystem.getLastExitTime();

        //calc utils
        cashUnitUtils = cashierSystem.getUnitUtil(totalRunningTime);
        baristaUnitUtils = baristaSystem.getUnitUtil(totalRunningTime);


        //order customers by their arrival. first arrived comes first
        std::vector<Customer> orderedByArrival = toLeave;
        Customer::orderByArrival(orderedByArrival);

        //calc turn arounds
        for(auto p: orderedByArrival) {

            turnAroundTimes.push_back(p.timeCoffeeDone-p.arrivTimeToCs);
        }

    }

    //rounds all data to 2decimal digit
    void roundData() {

        for(double & cashUnitUtil : cashUnitUtils) {

            cashUnitUtil = Helpers::round(cashUnitUtil);
        }

        for(double & baristaUnitUtil: baristaUnitUtils) {

            baristaUnitUtil = Helpers::round(baristaUnitUtil);
        }

        for(double & turnAroundTime:turnAroundTimes) {

            turnAroundTime = Helpers::round(turnAroundTime);
        }
    }

    void storeOutputs() {

        outputs.clear();

        outputs.push_back(totalRunningTime);
        outputs.push_back(maxQueCash);
        outputs.push_back(maxQueBarista);


        for (auto p: cashUnitUtils) {outputs.push_back(p);}
        for(auto p:baristaUnitUtils) {outputs.push_back(p);}
        for(auto p:turnAroundTimes){outputs.push_back(p);}
    }


    std::vector<double> getOutputs() {

        storeOutputs();
        return outputs;

    }
};

#endif //ASM3V2_COFFEESHOPMODEL1_H

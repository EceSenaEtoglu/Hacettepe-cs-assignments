//
// Created by b2210356016 on 6.12.2022.
//

#ifndef ASM3V2_COFFEESHOPMODEL2_H
#define ASM3V2_COFFEESHOPMODEL2_H

#include "CashierSystem.h"
#include "BaristaSystem.h"

class CoffeeShopModel2{

private:

    int cashierNum = 0;
    std::vector<Customer> incomingCustomers;

    double totalRunningTime = 0;
    int maxQueCash = 0;
    std::vector<int> maxQuesBarista;

    std::vector<double> cashUnitUtils;
    std::vector<double> baristaUnitUtils;
    std::vector<double> turnAroundTimes;

    std::vector<double> outputs;


public:

    CoffeeShopModel2(int cashierNum,std::vector<Customer> incomingCustomers) {

        this->cashierNum = cashierNum;
        this->incomingCustomers = incomingCustomers;
    }


    void startModel2() {

        //cashier system
        CashierSystem cashierSystemModel2(cashierNum,this->incomingCustomers,true);
        cashierSystemModel2.start();
        maxQueCash = cashierSystemModel2.getMaxQueLength();
        std::vector<std::vector<Customer>> customersToBarista = cashierSystemModel2.getCustomersToBarista();

        //output of barista system
        std::vector<Customer> customersToLeaveCs;


        //barista system
        for(auto p:customersToBarista) {

            //construct barista system
            BaristaSystem baristaSystem(1,p);
            baristaSystem.start();

            //update total running time
            double tempRunningTime = baristaSystem.getLastExitTime();
            if (tempRunningTime > totalRunningTime) {totalRunningTime = tempRunningTime;}

            //store max ques
            maxQuesBarista.push_back(baristaSystem.getMaxQueLength());

            // store customers that are leaving
            for(auto d:baristaSystem.getCustomersToLeave()) {
                customersToLeaveCs.push_back(d);
            }

            //get busy time
            baristaUnitUtils.push_back(baristaSystem.baristas[0]->totalWorkTime);
        }


        std::vector<Customer> orderedByArrival = customersToLeaveCs;
        Customer::orderByArrival(orderedByArrival);

        for(auto p: orderedByArrival) {

            turnAroundTimes.push_back(p.timeCoffeeDone - p.arrivTimeToCs);
        }

        //unit utils
        cashUnitUtils = cashierSystemModel2.getUnitUtil(totalRunningTime);

        for(double & baristaUnitUtil : baristaUnitUtils) {

            baristaUnitUtil = baristaUnitUtil / totalRunningTime;
        }

    }

    //rounds all data to have max 2 decimal digit
    void roundData() {

        for(double& p:cashUnitUtils) { p = Helpers::round(p);}
        for(double& p:baristaUnitUtils) { p = Helpers::round(p);}
        for(double&p :turnAroundTimes) { p = Helpers::round(p);}
    }

    //stores outputs of system to outputs vect
    void storeOutputs() {

        outputs.clear();

        outputs.push_back(totalRunningTime);
        outputs.push_back(maxQueCash);

        for(auto p:maxQuesBarista){outputs.push_back(p);}
        for (auto p: cashUnitUtils) {outputs.push_back(p);}
        for(auto p:baristaUnitUtils) {outputs.push_back(p);}
        for(auto p:turnAroundTimes){outputs.push_back(p);}
    }

    //return outputs
    std::vector<double> getOutputs() {

        storeOutputs();
        return outputs;
    }


};

#endif //ASM3V2_COFFEESHOPMODEL2_H

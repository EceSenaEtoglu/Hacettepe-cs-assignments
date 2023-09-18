//
// Created by b2210356016 on 3.12.2022.
//

#ifndef ASM3V2_CASHIERSYSTEM_H
#define ASM3V2_CASHIERSYSTEM_H

#include <vector>
#include "Cashier.h"
#include "CustomerQueue.h"

class CashierSystem {

private:

    bool onModel2 = false;
    std::vector<Customer> model1CustomersToBarista{};
    int maxQueueNum = 0;
    std::vector<std::vector<Customer>> twoDimCustomersToBarista{};

    std::vector<Cashier*> cashiers;
    std::vector<Customer> incCustomers;

    CustomerQueue customerQueue{};

    double currTime = 0; // END OF GLOBALS

    // add customer to system ( to cashiers or to line)
    void addCustomer(Customer& customer);

    //make cashier idle
    //check line, get customer from line
    void makeCashierIdle(Cashier* cashier);

    //make cashiers in param idle
    //transfer their customers to barista one by one
    // after each transfer, check the line to catch the time
    void makeCashiersIdle(std::vector<Cashier*> cashiersToBeIdled);

    //gets min idle time of a cashier
    double getMinIdleTime();

    //gets time base sorted vector of cashiers to be idled
    std::vector<Cashier*> getCashiersToBeIdled();

    //sortInTime given cashiers idleTimeBased
    static std::vector<Cashier*> sortInTime(const std::vector<Cashier*>& cashiers);

    bool allCashiersAreIdle();

    //model based transfer func
    //adds customer to barista list
    void transferCustomer(Customer customer,int cashId);


    //HELPERS
    static int getTransferIndex(int id) {

        return getCeil3rd(id) / 3 - 1;
    }

    static int getCeil3rd(int id) {

        if(id % 3 != 0) {

            if ((id + 1) % 3 == 0 ) {
                return id + 1;
            }

            else {
                return id + 2;
            }

        }

        return id;

    }

public:

    CashierSystem() = default;

    CashierSystem(int cashierNum, std::vector<Customer>& incCustomers,bool onModel2) {

        for(int i = 0; i<cashierNum; i++) {
            this->cashiers.push_back(new Cashier(i+1));
        }

        this->incCustomers = incCustomers;
        this->onModel2 = onModel2;

        //model2 output will be 2d vector
        //construct inner vectors
        if(onModel2) {

            for(int i = 0;i<cashierNum/3;i++) {

                std::vector<Customer> inner;
                twoDimCustomersToBarista.push_back(inner);
            }

        }

    }

    ~CashierSystem() {

        for (auto cashier : cashiers)
        {
            delete cashier;
        }
    }

    ///core function to start the system!
    void start();

    //returns n sized vector for n cashiers, each elem = busy time / totalrunning time
    std::vector<double> getUnitUtil(double totalRunningTime) {

        std::vector<double> cashUnitUtil;

        for(auto cashier:cashiers) {

            cashUnitUtil.push_back(cashier->totalWorkTime/totalRunningTime);
        }

        return cashUnitUtil;
    }

    //get data of customers to be transfered to barista
    std::vector<std::vector<Customer>> getCustomersToBarista() {

        if (onModel2) { return twoDimCustomersToBarista;}

        else { twoDimCustomersToBarista.push_back(model1CustomersToBarista);
            return twoDimCustomersToBarista;}
    }

    int getMaxQueLength() const {
        return maxQueueNum;
    }

};


#endif //ASM3V2_CASHIERSYSTEM_H

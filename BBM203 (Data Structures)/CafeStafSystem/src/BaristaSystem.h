//
// Created by b2210356016 on 4.12.2022.
//

#ifndef ASM3V2_BARISTASYSTEM_H
#define ASM3V2_BARISTASYSTEM_H


#include <vector>
#include "Barista.h"
#include "CustomerQueue.h"

class BaristaSystem {

private:

    std::vector<Customer> customersToLeave{};
    std::vector<Customer> incCustomers;
    CustomerQueue priorityQueue;

    double currTime = 0;
    double lastCustomerToLeaveTime = 0;
    int maxQueueNum = 0;// END OF GLOBALS

    // add customer to system ( to baristas or to line)
    void addCustomer(Customer& customer);

    //gets time base sorted vector of baristas to be idled
    std::vector<Barista*> getBaristasToBeIdled();

    //make barista idle, check line
    // if line is not empty, assign that customer to barista
    void makeBaristaIdle(Barista* barista);

    //make baristas in param idle
    // transfer their customers to one by one
    // after each transfer, check the line to catch the time
    void makeBaristasIdle(std::vector<Barista*> baristasToBeIdled);

    //gets min idle time from idles
    double getMinIdleTime() const;

    //sort given baristas idleTimeBased wo modifiying param.
    // return sorted vector
    static std::vector<Barista*> sortInTime(const std::vector<Barista*>& baristas);

    bool allBaristasAreIdle();

public:

    std::vector<Barista*> baristas;

    ///core function, starts the system
    void start();

    BaristaSystem() = default;

    BaristaSystem(int baristaNum, std::vector<Customer>& incCustomers) {

        for(int i = 0; i<baristaNum; i++) {

            this->baristas.push_back(new Barista());
        }

        this->incCustomers = incCustomers;

    }

    ~BaristaSystem() {

        for (auto barista : baristas)
        {
            delete barista;
        }
    }

    //returns n sized vector for n cashiers, each elem = busy time / totalrunning time
    std::vector<double> getUnitUtil(double totalRunningTime) {

        std::vector<double> baristaUnitUtil;

        for(auto barista:baristas) {

            baristaUnitUtil.push_back(barista->totalWorkTime/totalRunningTime);
        }

        return baristaUnitUtil;
    }

    //get customers to leave coffee shop
    std::vector<Customer> getCustomersToLeave() {

        return customersToLeave;
    }

    //return the time of last exit of a customer
    double getLastExitTime() const {
        return lastCustomerToLeaveTime;
    }

    int getMaxQueLength() const {
        return maxQueueNum;
    }

};


#endif //ASM3V2_BARISTASYSTEM_H

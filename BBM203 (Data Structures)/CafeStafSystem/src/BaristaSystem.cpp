//
// Created by b2210356016 on 4.12.2022.
//

#include "BaristaSystem.h"

double BaristaSystem::getMinIdleTime() const {

    return sortInTime(baristas)[0]->calculatedIdleTime;
}

//adds customer to idle barista or to line
void BaristaSystem::addCustomer(Customer& customer) {

    //traverse all baristas, if idle exists add customer to that barista
    for(auto barista:baristas) {

        if (barista->isIdleFlag == 1) {

            barista->calculatedIdleTime = currTime + customer.brewTime;
            customer.timeCoffeeDone= barista->calculatedIdleTime;

            barista->isIdleFlag = 0;
            barista->currCustomer = customer;

            return;
        }

    }

    // if no avail barista exists add to queue
    priorityQueue.enqueueWithPriorityPrice(customer);

    //update max queue num
    if(priorityQueue.getLength() > maxQueueNum) {
        maxQueueNum = priorityQueue.getLength();
    }
}

void BaristaSystem::start() {

    for (Customer customer : incCustomers) {

        double tempEventArrival = customer.arrivTimeToBarista;

        double tempEventGoingIdle = getMinIdleTime();

        //shift time
        currTime = tempEventArrival;

        // if there is an idle barista or no idles happened before this time
        //directly add customer
        if (tempEventGoingIdle == DBL_MAX || tempEventArrival < tempEventGoingIdle) {

            //add customer to system
            addCustomer(customer);
        }

        // some idles happened <= this time
        else {

            //process idles
            makeBaristasIdle(getBaristasToBeIdled());

            addCustomer(customer);
        }
    }

    //after all customers arrived, if there are still baristas to be idled
    // make them idle
    while(!allBaristasAreIdle()) {

        std::vector<Barista*> baristasSortedInIdleTime = sortInTime(baristas);

        Barista* firstBaristaToBeIdled = baristasSortedInIdleTime[0];

        makeBaristaIdle(firstBaristaToBeIdled);

    }

    if (!customersToLeave.empty()) {
        //update last leave
        lastCustomerToLeaveTime = customersToLeave[customersToLeave.size()-1].timeCoffeeDone;
    }

}

//sorts baristas based on their calculatedIdleTime
// return sorted vector
std::vector<Barista*> BaristaSystem::sortInTime(const std::vector<Barista*>& baristas) {

    std::vector<Barista*> tempV = baristas;

    // bubble sort
    for(int i = baristas.size() - 1; i >= 0; i--) {

        for(int j = 0; j<i;j++) {

            //swap
            if(tempV[j]->calculatedIdleTime > tempV[j + 1]->calculatedIdleTime) {

                Barista* temp = tempV[j+1];
                tempV[j+1] = tempV[j];
                tempV[j] = temp;
            }
        }
    }


    return tempV;

}

// currtime must be updated before this func!
std::vector<Barista*> BaristaSystem::getBaristasToBeIdled() {

    std::vector<Barista*> baristasToBeIdled;

    for (int i = 0; i<baristas.size();i++) {

        Barista* barista = baristas[i];

        // if barista needs to be idled, add barista
        // currtime must be updated before this func!
        if (barista->calculatedIdleTime <= currTime && barista->isIdleFlag == 0) {

            baristasToBeIdled.push_back(barista);

        }
    }

    return sortInTime(baristasToBeIdled);
}

void BaristaSystem::makeBaristaIdle(Barista* barista) {

    double tempTime = barista->calculatedIdleTime;

    //give customer their coffee
    barista->currCustomer.timeCoffeeDone = barista->calculatedIdleTime;
    customersToLeave.push_back(barista->currCustomer);

    //save work data
    barista->totalWorkTime += barista->currCustomer.brewTime;

    //make barista idle
    barista->makeIdle();

    // if queue is not empty
    // assign waiting customer to barista
    if (priorityQueue.getLength() != 0) {

        //delete customer from queue, get customer
        Customer waitingCustomer = priorityQueue.dequeue();

        //update barista
        barista->calculatedIdleTime = tempTime + waitingCustomer.brewTime;
        barista->isIdleFlag = 0;
        barista->currCustomer = waitingCustomer;

    }

}

//transfers customers of the given barista list
// check queue after each transfer
void BaristaSystem::makeBaristasIdle(std::vector<Barista*> baristasToBeIdled) {

    for (int i = 0; i < baristasToBeIdled.size(); i++) {

        Barista* barista = baristasToBeIdled[i];

        //if barista is currently not idle
        //significant check!
        if (barista->isIdleFlag == 0) {

            makeBaristaIdle(barista);
        }
    }
}

bool BaristaSystem::allBaristasAreIdle() {

    for(int i = 0; i<baristas.size();i++) {

        // if one barista is not idle, return false
        if(baristas[i]->isIdleFlag == 0) {
            return false;
        }
    }

    return true;
}


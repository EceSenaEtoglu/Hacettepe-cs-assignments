//
// Created by b2210356016 on 3.12.2022.
//

#include "CashierSystem.h"


double CashierSystem::getMinIdleTime() {

    return sortInTime(cashiers)[0]->calculatedIdleTime;
}

//adds customer to system and its componenents
void CashierSystem::addCustomer(Customer& customer) {

    //traverse all cashiers, if avail exists add customer to that cashier
    for(auto cashier:cashiers) {

        if (cashier->isIdleFlag == 1) {

            cashier->calculatedIdleTime = currTime + customer.orderTime;
            customer.arrivTimeToBarista = cashier->calculatedIdleTime;

            cashier->isIdleFlag = 0;
            cashier->currCustomer = customer;

            return;
        }

    }

    // if no avail cashier exists add to queue
    customerQueue.enqueue(customer);

    //update max queue length
    if(customerQueue.getLength() > maxQueueNum) {
        maxQueueNum = customerQueue.getLength();
    }

}

//core function
void CashierSystem::start() {

    for (Customer customer : incCustomers) {

        double tempEventArrival = customer.arrivTimeToCs;

        double tempEventGoingIdle = getMinIdleTime();

        //shift time
        currTime = tempEventArrival;

        // if there is an idle cashier or no idles happened before this time
        // directly add customer
        if(tempEventGoingIdle == DBL_MAX || tempEventArrival < tempEventGoingIdle) {

            //add customer to system
            addCustomer(customer);
        }

        // some idles happened <= this time
        else {

            //process idles
            makeCashiersIdle(getCashiersToBeIdled());

            addCustomer(customer);
        }

    }

    //after all customers arrived, if there are cashiers to be idled
    // make them idle
    while(!allCashiersAreIdle()) {

        std::vector<Cashier*> cashiersSortedInIdleTime = sortInTime(cashiers);

        Cashier* nextCashierToBeIdled = cashiersSortedInIdleTime[0];

        makeCashierIdle(nextCashierToBeIdled);

    }

}

//sorts cashiers based on their calculatedIdleTime
// returns copy of the given cashiers vector
std::vector<Cashier*> CashierSystem::sortInTime(const std::vector<Cashier*>& cashiers) {

    std::vector<Cashier*> tempV = cashiers;

    // bubble sort
    for(int i = cashiers.size() - 1; i >= 0; i--) {

        for(int j = 0; j<i;j++) {

            //swap
            if(tempV[j]->calculatedIdleTime > tempV[j + 1]->calculatedIdleTime) {

                Cashier* temp = tempV[j+1];
                tempV[j+1] = tempV[j];
                tempV[j] = temp;


            }
        }
    }

    return tempV;

}

std::vector<Cashier*> CashierSystem::getCashiersToBeIdled() {

    std::vector<Cashier*> cashiersToBeIdled;

    for (int i = 0; i<cashiers.size();i++) {

        Cashier* cashier = cashiers[i];

        if (cashier->calculatedIdleTime <= currTime && cashier->isIdleFlag == 0) {

            cashiersToBeIdled.push_back(cashier);

        }
    }

    return sortInTime(cashiersToBeIdled);
}

void CashierSystem::makeCashierIdle(Cashier *cashier) {

    //go back in time
    double tempTime = cashier->calculatedIdleTime;

    //transfer customer of cashier to barista
    cashier->currCustomer.arrivTimeToBarista = cashier->calculatedIdleTime;
    transferCustomer(cashier->currCustomer,cashier->id);

    //save work data
    cashier->totalWorkTime += cashier->currCustomer.orderTime;

    //make cashier avail
    cashier->makeIdle();


    //check queue
    // bekleyen müşteriyi boşalan kasiyere ata

    if(customerQueue.getLength() != 0) {

        Customer waitingCustomer = customerQueue.dequeue();
        cashier->calculatedIdleTime = tempTime + waitingCustomer.orderTime;
        cashier->isIdleFlag = 0;
        cashier->currCustomer = waitingCustomer;

    }
}

//transfers customers of the given cashier list
void CashierSystem::makeCashiersIdle(std::vector<Cashier*> cashiersToBeIdled) {

    for (int i = 0; i<cashiersToBeIdled.size();i++) {

        Cashier* cashier = cashiersToBeIdled[i];

        //if cashier is currently not idle
        if (cashier->isIdleFlag == 0) {

            makeCashierIdle(cashier);
        }
    }
}


//transfers customer according to model
void CashierSystem::transferCustomer(Customer customer,int cashId) {

    // if on model1, directly add customer
    if (!this->onModel2) {

        model1CustomersToBarista.push_back(customer);
    }

    // if on model 2, add to proper que
    else {

        int transferIndex = getTransferIndex(cashId);
        twoDimCustomersToBarista[transferIndex].push_back(customer);

    }
}


bool CashierSystem::allCashiersAreIdle() {

    for(int i = 0; i<cashiers.size();i++) {

        // if one cashier is not idle, return false
        if(cashiers[i]->isIdleFlag == 0) {
            return false;
        }
    }

    return true;
}
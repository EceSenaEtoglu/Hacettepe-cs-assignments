//
// Created by b2210356016 on 6.12.2022.
//

#ifndef ASM3V2_CUSTOMERQUEUE_H
#define ASM3V2_CUSTOMERQUEUE_H


#include <iostream>
#include "CustomerNode.h"

class CustomerQueue {

private:

    CustomerNode* front;
    CustomerNode* rear;

    int length;


public:

    CustomerQueue() {

        front = nullptr;
        rear = nullptr;
        length = 0;

    }

    ~CustomerQueue() {

        clear();

    }

    void clear() {

        CustomerNode* temp = front;

        while(temp != nullptr) {

            //get next dest
            CustomerNode* nextDest = temp->next;

            //deallocate memory
            delete temp;

            //reassign temp
            temp = nextDest;
        }

        front = nullptr;
        rear = nullptr;
        length = 0;
    }

    // remove first element
    // return removed element
    // undefined behav. if queue is empty
    Customer dequeue() {

        if (front != nullptr) {

            //get datas
            Customer data = front->data;
            CustomerNode* next = front->next;

            delete front;

            front = next;

            if (front == nullptr) {
                rear = nullptr;
            }

            length--;

            return data;
        }
    }

    // add to queue
    void enqueue(Customer customer) {

        CustomerNode* newNode = new CustomerNode(customer);

        if(rear != nullptr) {
            rear->next = newNode;
        }

        rear = newNode;

        if (front== nullptr) {
            front = rear;
        }

        length++;
    }

    // add to queue based on price priority
    // greater price locates at front
    void enqueueWithPriorityPrice(Customer customer) {

        double priceIncoming = customer.priceOfCoffe;
        CustomerNode* newNode = new CustomerNode(customer);

        // if queue is empty, directly add
        if (front == nullptr) {

            front = newNode;
            rear = newNode;

            length ++;

            return;
        }

        CustomerNode* temp = front;
        CustomerNode* prev = nullptr;

        while(temp != nullptr) {

            //locate node between prev and temp
            if (priceIncoming > temp->data.priceOfCoffe) {

                // if value of newnode is greater than front node
                // make newnode front
                if(temp == front) {

                    newNode->next = temp;
                    front = newNode;
                }

                else {
                    prev->next = newNode;
                    newNode->next = temp;

                }

                length++;
                return;
            }

            prev = temp;
            temp = temp->next;
        }

        //if no allocation is made
        // assign node as last node
        prev->next = newNode;
        rear = newNode;

        length++;

    }

    int getLength() const{return length;}

};


#endif //ASM3V2_CUSTOMERQUEUE_H

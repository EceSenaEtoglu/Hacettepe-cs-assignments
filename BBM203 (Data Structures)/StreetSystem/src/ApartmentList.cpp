//
// Created by b2210356016 on 14.11.2022.
//

#include "ApartmentList.h"


ApartmentList::ApartmentList() {

    ApartmentList::head = nullptr;
    ApartmentList::tail = nullptr;
    length = 0;

}

ApartmentList::~ApartmentList() {

    clear();
}

// index must be 0 <=index<=len-1
Apartment* ApartmentList::getApartmentByIndex(int apartmentIndex) {

    ApartmentNode* temp = head;
    int ct = 0;

    // if list is not empty
    if (temp != nullptr) {

        do {

            if(ct == apartmentIndex) {
                return &temp->data;
            }

            ct ++;
            temp = temp->next;

        }  while(temp != head);

    }

}


Apartment& ApartmentList::getApartment(const std::string& apartmentName) {

    ApartmentNode* temp = head;

    // if list is not empty
    if (temp != nullptr) {

        do {
            if (temp->data.getName()== apartmentName) {
                return temp->data;
            }

            temp = temp->next;
        }  while(temp != head);

    }
}


void ApartmentList::addApartmentAtFront(std::string apartmentName, int maxBandWith) {

   ApartmentNode* newNode= new ApartmentNode(apartmentName,maxBandWith);

   // if list is empty
   if(head== nullptr) {

       head = newNode;
       tail = newNode;

       head->next= head;
       tail->next = head;

       length ++;
   }

   else {
       newNode->next = head;
       tail->next = newNode;

       head = newNode;

       length ++;
   }
}

void ApartmentList::addApartmentAfterX(const std::string& apartmentXName, std::string apartmentName, int maxBandWith) {

    ApartmentNode* newNode = new ApartmentNode(apartmentName,maxBandWith);

    ApartmentNode* temp = head;

    // locate the node (X), the node to be inserted after
    while(temp->data.getName()!= apartmentXName) {
        temp = temp->next;
    }

    newNode->next = temp->next;
    temp->next = newNode;

    // if insertion is after tail, update tail as newNode
    if(temp == tail) {
        tail = newNode;
    }

    length ++;

}

void ApartmentList::addApartmentBeforeX(const std::string& apartmentXName, const std::string& apartmentName, int maxBandWith) {

    ApartmentNode* newNode = new ApartmentNode(apartmentName,maxBandWith);

    // locate the node prev node of the node to be inserted before ( prev of X)
    ApartmentNode* temp = head;

    //if insertion is before current head, make newnode head in the list
    if(temp->data.getName() == apartmentXName) {

        addApartmentAtFront(apartmentName,maxBandWith);
        return;
    }

    while(temp->next->data.getName()!= apartmentXName) {

        temp = temp->next;
    }

    newNode->next = temp->next;
    temp->next = newNode;

    length ++;

}

void ApartmentList::freeNode(ApartmentNode *node) {

    /*if(node != nullptr) {
        delete node;
        node = nullptr;
    }*/

    delete node;
    node = nullptr;

}

//removes the item from the front of the list, returns the changed list
//if list is empty before change, return nullptr
ApartmentList* ApartmentList::removeFromFront() {

    // if list is empty, do nothing, return null
    if (head == nullptr) {
        return nullptr;
    }

    // if list has only 1 element
    if( head == tail) {

        // free head and tail
        freeNode(head);

        //wrong ! freeNode(tail);

        // TODO why does this line has to exist and line in freeNode does not get called?
        head = nullptr;
        tail = nullptr;

        length--;

        // After remove operation, if there is not any apartment in the apartment linked list function must return NULL.
        return nullptr;

    }

    else {

        ApartmentNode* newHead = head->next;
        tail->next = newHead;

        // deallocate memory before reassigning head
        freeNode(head);

        head = newHead;

        length--;

        return this;
    }

}

ApartmentList* ApartmentList::removeApartment(const std::string& apartmentName) {

    // if list is empty, do nothing, return null
    if (head == nullptr) {
        return nullptr;
    }

    ApartmentNode* prev = head;

    // remove head O(1), return null
    if(prev->data.getName() == apartmentName) {

        return removeFromFront();
    }

    else {

        // locate prev node
        while(prev->next->data.getName()!= apartmentName) {
            prev = prev->next;
        }

        ApartmentNode* current = prev->next;

        // unlink current
        prev->next = current->next;

        if(tail == current) {
            tail = prev;
        }

        //free current
        freeNode(current);

        length--;

        return this;
    }

}


void ApartmentList::clear() {

    // if list is empty
    if (head == nullptr) {
        return;
    }

    ApartmentNode* temp = head;

    ApartmentNode* nextDestination;

    do {

        nextDestination = temp->next;

        delete temp;

        temp = nextDestination;

    } while (temp != head);

    head = nullptr;
    tail = nullptr;
    length = 0;

}

bool ApartmentList::isEmpty() {

    return head == nullptr;
}

int ApartmentList::getSumOfMaxBandwidths() {

    int sum = 0;

    if (head == nullptr) {
        return sum;
    }

    else {

        ApartmentNode* temp = head;

        do {
            sum += temp->data.getMaxBandWidthValue();
            temp = temp->next;

        }
        while(temp != head);

        return sum;

    }
}


std::string ApartmentList::toString() {

    std:: string s = "";

    // if apartment is empty
    if (head == nullptr) {
        return s;

    }

    ApartmentNode* temp = head;

    do {

        // append apartment's string
        s.append(temp->data.toString());

        // if not on last node, add newline
        if (temp->next != head) {
            s.append("\n");
        }

        temp = temp->next;

    } while(temp != head);

    return s;

}



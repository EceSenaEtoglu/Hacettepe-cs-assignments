//
// Created by b2210356016 on 14.11.2022.
//

#ifndef ASM2_APARTMENTLIST_H
#define ASM2_APARTMENTLIST_H

#include "ApartmentNode.h"
#include "Apartment.h"

//Circular Linked List of node ApartmentNode

class ApartmentList {

private:

    ApartmentNode* head;
    ApartmentNode* tail;
    int length;

public:

    ApartmentList();
    ~ApartmentList();

    //returns the apartment with given name
    // undef. behaviour if apartment does not exist or apartment list is empty
    Apartment& getApartment(const std::string& apartmentName);

    Apartment* getApartmentByIndex(int apartmentIndex);

    int getLength() const {
        return this->length;
    }

    // add an Apartment to the list as Head
    void addApartmentAtFront(std::string apartmentName,int maxBandWith);

    // add an Aparment after given name
    void addApartmentAfterX(const std::string& apartmentXName,std::string apartmentName,int maxBandWith);

    // add an Aparment before given name
    void addApartmentBeforeX(const std::string& apartmentXName,const std::string& apartmentName,int maxBandWith);

    // remove aparment with name apartmentName
    // return the changed list
    // if no aparment is in changed list, return null
    ApartmentList* removeApartment(const std::string& apartmentName);

    // remove head of the list
    ApartmentList* removeFromFront();

    // deallocate the memory of the given node
    static void freeNode(ApartmentNode* node);

    //deallocates memory of pointers
    // set length,head,tail to default values
    void clear();

    bool isEmpty();

    // returns sum of bandwidth of apartments
    int getSumOfMaxBandwidths();

    // return string represantation of apartmentlist
    //X(105)\tFlat1(25)
    std::string toString();

};


#endif //ASM2_APARTMENTLIST_H

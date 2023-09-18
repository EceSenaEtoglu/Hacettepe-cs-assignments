//
// Created by b2210356016 on 14.11.2022.
//

#ifndef ASM2_FLATLIST_H
#define ASM2_FLATLIST_H

#include "Flat.h"
#include "FlatNode.h"

//Doubly LinkedList

class FlatList {

private:
    FlatNode* head = nullptr;

    //remove node with flatId from this list by key, key = flat Id
    // return removed node
    FlatNode* unlinkNode(int flatId);

    // X is a node in this list
    //insert newnode before X
    void insertNodeBeforeX(FlatNode* X, FlatNode* newNode);


    FlatNode* getNode(int index);
    FlatNode* getNodeByKey(int flatId);

public:

    FlatList() {
        head = nullptr;
    }

    ~FlatList() {
        clear();
    }

    bool isEmpty() const {
        return head == nullptr;
    }


    //appends flats of list2 to the end of list1
    // makes list2 empty
    // returns changed list1
    static FlatList& merge(FlatList& list1,FlatList& list2);

    // adds a new flat to the given index
    //0 <= index <= length must satisfied
    void addFlat(int insertionIndex,int flatId,int flatBandWidth);

    // moves Flat with Id movedFlatId from listToBeRemoved to destFlatList
    // location after moving is: prev flat of destFlatId
    static void moveFlatToBeforeX(int movedFlatId, FlatList& listToBeRemoved,int destFlatId,FlatList& destFlatList);

    //checks if there is a flat in this list with given flatId
    bool FlatExists(int flatId);

    // return the Flat with given keyFlatId
    // if no such Flat exists undefined behaviour
    Flat& getFlatByKey(int keyFlatId);

    //deallocates memory of pointers
    // set length,head to default values
    void clear();

    // returns string rep. of this list
    // Flat1(25)\tFlat4(20)
    std::string toString();

    //returns total bandwidth of flats
    int getSumOfFlatBandWidths();


};


#endif //ASM2_FLATLIST_H

//
// Created by b2210356016 on 14.11.2022.
//

#include "FlatList.h"
#include "FlatNode.h"


//returns Node with given FlatId
// if list is empty or such node does not exist, function is erroneous
FlatNode* FlatList::getNodeByKey(int flatId) {

    FlatNode* temp = head;

    while(temp != nullptr) {

        if (temp->data.getId() == flatId) {
            return temp;
        }

        temp = temp->next;

    }
}


// returns the node in the given index
//undefined behaviour if given index >= length or index <0
FlatNode* FlatList::getNode(int flatIndex) {

    FlatNode* temp = head;
    int ct = 0;

    while(temp != nullptr && ct != flatIndex) {

        temp = temp->next;
        ct ++;

    }

    return temp;

}


//Given index must always be 0 <= index <= length
// if list is empty, locates flat as first item no matter what the insertionIndex is

void FlatList::addFlat(int insertionIndex, int flatId, int flatBandWidth) {

    FlatNode* newNode = new FlatNode(flatId,flatBandWidth);

    // empty list
    if (head == nullptr) {

        head = newNode;
        return;
    }

    //insert at front
    if (insertionIndex == 0) {

        newNode->next = head;
        head->prev = newNode;

        head = newNode;

        return;

    }


    FlatNode* prevNode = getNode(insertionIndex-1);

    newNode->next = prevNode->next;

    //if prevnode is not last node
    if(prevNode->next != nullptr) {
        prevNode->next->prev = newNode;
    }

    prevNode->next = newNode;
    newNode->prev = prevNode;

}

int FlatList::getSumOfFlatBandWidths() {

    FlatNode* temp = head;

    int sum = 0;

    // if list is empty return 0
    if (temp == nullptr) {

        return sum;
    }

    while(temp != nullptr) {

        sum += temp->data.getInitialBandWidth();
        temp = temp->next;
    }

    return sum;

}

//Flat1(25)\tFlat4(20)
std::string FlatList::toString() {

    std::string s = "";

    // if list is empty
    if (head == nullptr) {
        return s;
    }

    FlatNode* temp = head;

    while(temp != nullptr) {

        s.append("Flat");
        s.append(std::to_string(temp->data.getId()));
        s.append("(");
        s.append(std::to_string(temp->data.getInitialBandWidth()));
        s.append(")");

        // if not on last element append tab
        if (temp->next != nullptr) {

            s.append("\t");

        }

        temp = temp->next;

    }

    return s;

}


// inserts newNode before X node of this list
// X must be a node of this list
void FlatList::insertNodeBeforeX(FlatNode* X, FlatNode *newNode) {

    //start linking
    newNode->prev = X->prev;

    if (newNode->prev != nullptr) {
        newNode->prev->next = newNode;
    }

    newNode->next = X;
    X->prev = newNode; // end of linking

    // update head if X is head
    if(head == X) {
        head = newNode;
    }

}


//deallocates memory of pointers
// set length,head,tail to default values
void FlatList::clear() {

    FlatNode* temp = head;
    FlatNode* nextDestination;

    while(temp != nullptr) {

        //locate next destination before deletion
        nextDestination = temp->next;

        delete temp;

        temp = nextDestination;

    }

    head = nullptr;

}

// returns the flat with given key
// Flat with given key should exist
Flat& FlatList::getFlatByKey(int keyFlatId) {

    FlatNode* temp = head;

    while(temp!= nullptr) {

        if (temp->data.getId() == keyFlatId) {
            return temp->data;
        }

        temp = temp->next;
    }

}

//checks if there is a flat in this list with given flatId
bool FlatList::FlatExists(int flatId) {

    FlatNode* temp = head;

    while(temp!= nullptr) {

        if (temp->data.getId() == flatId) {
            return true;
        }

        temp = temp->next;
    }

    return false;

}

void FlatList::moveFlatToBeforeX(int movedFlatId, FlatList& listToBeRemoved,int destFlatId,FlatList& destFlatList) {

    //unlink the node from it's list and get it
    FlatNode* movedNode = listToBeRemoved.unlinkNode(movedFlatId);

    // get destNode by it's flatId
    FlatNode* destNode = destFlatList.getNodeByKey(destFlatId);

    //insert the node before, (create links)
    destFlatList.insertNodeBeforeX(destNode,movedNode);


}

// cases: both list1 or list2 inputs can be empty or not
FlatList& FlatList::merge(FlatList &list1, FlatList &list2) {

    //link nodes

    //list1.head = nullptr
    if (list1.isEmpty()) {

        list1.head = list2.head;

        //make list2 empty
        list2.head = nullptr;

        return list1;

    }

    //list2.head = nullptr
    else if (list2.isEmpty()) {

        // do nothing
        return list1;
    }

    else {

        FlatNode* tailOfList1 = list1.head;

        while(tailOfList1->next != nullptr) {

            tailOfList1 = tailOfList1->next;
        }

        //link nodes

        // tail and head are not nullptr
        tailOfList1->next = list2.head;
        list2.head->prev = tailOfList1;

        //make list2 empty
        list2.head = nullptr;

        return list1;

    }

}


// unlinks node from this list
//node with given flatıd must exist
FlatNode* FlatList::unlinkNode(int flatId) {

    FlatNode* temp = head;
    FlatNode* prevNode;

    if(temp != nullptr) {

        //if searched node is head
        if (temp->data.getId() == flatId) {

            // unlink
            if(temp->next != nullptr) {
                temp->next->prev = nullptr;
            }

            head = temp->next;

            return temp;

        }

        // searched node is not head
        while (temp != nullptr) {

            if (temp->data.getId() == flatId) {
                break;
            }

            temp = temp->next;
        }

        //locate prev
        prevNode = temp->prev;

        //unlink
        prevNode->next = temp->next;

        if (temp->next != nullptr) {
            temp->next->prev = prevNode;
        }

        //clean
        temp->prev = nullptr;
        temp->next = nullptr;


        return temp;

    }

}

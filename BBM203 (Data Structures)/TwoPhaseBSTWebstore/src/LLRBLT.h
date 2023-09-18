//
// Created by b2210356016 on 24.12.2022.
//

#ifndef LLRBLT_LLRBLT_H
#define LLRBLT_LLRBLT_H

#include <string>
#include <cstring>
#include "StoreItem.h"

class nodeRBL{

    nodeRBL* left;
    nodeRBL* right;

    //item.name = search searchKey for the tree
    std::string key;

    //item.price =  data
    int data;

    bool isRed;

    explicit nodeRBL(const StoreItem& item) {

        left = nullptr;
        right = nullptr;

        this->key = item.name;
        this->data = item.price;

        isRed = true;

    }

    //"searchKey":"data"
    std::string toString() {

        std::string s;

        s.append("\"");
        s.append(key);
        s.append("\":\"");
        s.append(std::to_string(data));
        s.append("\"");

        return s;
    }


    friend class LLRBLT;
};


class LLRBLT {

    nodeRBL* root;


public:

    LLRBLT() {

        root = nullptr;
    }

    ~LLRBLT() {
        clear(root);
    }

    void insert(const StoreItem& item) {

        root = insert(root,item);

        root->isRed = false;
    }

    //updates data of the node with searchKey as searchKey with given data
    //if node does not exist do nothing
    void updateData(const std::string& searchKey,int data) {

        nodeRBL* nodeToUpdate = search(root,searchKey);

        if(nodeToUpdate == nullptr) { return;}

        nodeToUpdate->data = data;

    }

    //deletes the node from tree
    //if node does not exist do nothing
    void deleteItem(const std::string& searchKey) {

        if(search(root,searchKey) == nullptr) { return;}

        if(root != nullptr) {

            root = deleteNode(root,searchKey);
            //fix color
            if(root != nullptr) {

                root->isRed = false;
            }

        }
    }

    //gets string rep. of the node
    //"searchKey":"data"
    //return "" if not exist
    std::string  getStringOfNode(const std::string& searchKey) {

        nodeRBL* searched = search(root,searchKey);
        if(searched == nullptr) {return "";}

        return searched->toString();
    }

    //gets level order string of nodes
    std::string getLevelOrderString() {
        return getLevelOrderString(root);
    }

    bool isEmpty() {return root == nullptr;}

    //checks if a node with given searchKey exists in tree
    bool contains(const std::string &searchKey) {

        return search(root,searchKey) != nullptr;
    }


protected:

    //SEARCH RELATED

    //returns node with key as searchKey
    //returns nullptr if not exist
    nodeRBL* search(nodeRBL* node,const std::string& searchKey);

    //returns minNode
    static nodeRBL* getMinNode(nodeRBL*node);

    //deletes min element of tree with this node
    nodeRBL* deleteMinNode(nodeRBL* node);


    //MODIFY
    static nodeRBL* insert(nodeRBL* node, const StoreItem& item);
    nodeRBL* deleteNode(nodeRBL* node, const std::string& searchKey);
    void clear(nodeRBL*& ptr);



    //GEOMERTY
    static nodeRBL* rotateRight(nodeRBL* root);
    static nodeRBL* rotateLeft(nodeRBL* root);

    //create a LLRBLT such that red link is moved left of the node
    static nodeRBL* moveRedLeft(nodeRBL* node);

    //create a LLRBLT such that red link is moved right of the node
    static nodeRBL* moveRedRight(nodeRBL* node);

    //applies color flipping rules of LLRBLT if necessary
    static void flipColors(nodeRBL* node);

    //checks color of the given node
    //returns false if node is null
    //else returns based on color
    static bool isRed(nodeRBL* node);

    static nodeRBL* fixTree(nodeRBL* root);

    //HELPER
    static int compare(const std::string& key,nodeRBL* node) {

        return strcmp(key.c_str(),node->key.c_str());
    }

    static std::string getLevelOrderString(nodeRBL* root);
};


#endif //LLRBLT_LLRBLT_H

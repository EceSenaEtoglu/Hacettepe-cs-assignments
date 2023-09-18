#ifndef ASM4_AVLT_H
#define ASM4_AVLT_H

#include <string>
#include <iostream>
#include <queue>
#include <cstring>
#include "StoreItem.h"

class avlNode{

    avlNode* left;
    avlNode* right;

    //item.name
    std::string key;

    //item.price
    int data;

public:

    explicit avlNode(const StoreItem& item) {

        this->key = item.name;
        this->data = item.price;

        left = nullptr;
        right = nullptr;

    }

    //"s_key_11":"data_11"
    std::string toString() {

        std::string s; s.append("\"");s.append(key);s.append("\":\"");
        s.append(std::to_string(data));
        s.append("\"");
        return s;
    }


    friend class AVLT;
    friend class ItemDataBasePart1;
};


class AVLT {

    //UTILITY CLASS

private:

    //returns min node in the tree that starts root
    static avlNode* getMinNode(avlNode* root);


public:

    //insers newnode into tree with innerRoot as parameter innerRoot
    static avlNode* insert(avlNode* root, avlNode* newNode);

    //removes the node with searchkey from innerRoot, if such node does not exist does nothing
    //returns updated tree (innerRoot)
    static avlNode* remove(avlNode* root, const std::string& searchKey);

    //returns the node with given searchkey
    //returns nullptr if such node does not exist
    static avlNode* search(avlNode* root,const std::string& searchKey);


    static void deleteTree(avlNode*&);

    static std::string getLevelOrderString(avlNode* root);



    //GEOMETRY FUNCTIONS
    static avlNode* rotateRight(avlNode*);
    static avlNode* rotateLeft(avlNode*);


    //return balance factor
    static int getBalanceFactor(avlNode* node) {

        if (node == nullptr)
            return -1;

        return height(node->left) - height(node->right);
    }

    //HELPERS for comparison
    static int compare(avlNode* n0,avlNode* n1) {

        return strcmp(n0->key.c_str(),n1->key.c_str());
    }

    static int compare(const std::string& key, avlNode* n0) {

        return strcmp(key.c_str(),n0->key.c_str());
    }

    //return height of node
    static int height(avlNode* node) {

        if (node == nullptr)
            return -1;

        else {

            //find each subtree's height

            int lheight = height(node -> left);
            int rheight = height(node -> right);

            //return larger one
            if (lheight > rheight)
                return (lheight + 1);
            else return (rheight + 1);
        }
    }

};

#endif //ASM4_AVLT_H

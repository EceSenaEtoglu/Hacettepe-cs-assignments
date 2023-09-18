//
// Created by b2210356016 on 22.12.2022.
//

#ifndef ASM4_ITEMDATABASEPART1_H
#define ASM4_ITEMDATABASEPART1_H

#include <string>
#include <cstring>
#include "LLRBLT.h"
#include "AVLT.h"

class primaryNode1 {

    primaryNode1* left;
    primaryNode1* right;

    std::string searchKey;

    avlNode* innerRoot;


public:

    explicit primaryNode1(std::string searchKey) {

        this->searchKey = searchKey;
        left = nullptr;
        right = nullptr;

        innerRoot = nullptr;
    }

    ~primaryNode1(){

        AVLT::deleteTree(innerRoot);
    }


    //returns string rep. of a category with specified rules in pdf
    std::string toString() {

        std::string s;
        s.append("\"");
        s.append(searchKey);
        s.append("\":");

        if (innerRoot == nullptr) {
            s.append("{}");
            return s;
        }

        else {
            s.append("\n");
            s.append(AVLT::getLevelOrderString(innerRoot));
            return s;

        }
    }

    friend class ItemDataBasePart1;
};


//uses a two phase binary search tree
//inner tree is an AVL TREE

class ItemDataBasePart1 {

private:

    primaryNode1* root;


    //HELPERS
    static int compare(const std::string& key, primaryNode1* node) {

        return strcmp(key.c_str(),node->searchKey.c_str());
    }

    //return node with key as searchKey, if node does not exist return nullptr
    primaryNode1* search(primaryNode1* node, const std::string& searchKey);

public:


    ItemDataBasePart1() {
        root = nullptr;
    }

    ~ItemDataBasePart1() {

        clear(root);
    }


    //creates output for given database with given inputLines instructions
    static void init(ItemDataBasePart1,const std::vector<std::string>& inputLines, const std::string&fileName);

    //inserts given item to database
    // primaryKey: item.category secondaryKey: item.name
    void insert(const StoreItem& item) {

        insert(root,item);
    }

    //removes the item with category as category and name itemName
    //does nothing if such item does not exist
    void remove(const std::string& category,const std::string& itemName);

    //updates item defined with name and category's price to newPrice
    //item is defined with category and name
    // does nothing if such item does not exist
    void updateData(const std::string& category,const std::string& name, int newPrice);


    //if tree is empty return {}
    // else return level order string of each category
    std::string getStringOfAllItems();

    std::string getStringOfAllItemsInCategory(const std::string& searchKey);

    std::string getStringOfItem(const std::string& primaryKey, const std::string& secondaryKey);



protected:

    void insert(primaryNode1*& node, const StoreItem& item);
    void clear(primaryNode1*& node);

};

#endif //ASM4_ITEMDATABASEPART1_H

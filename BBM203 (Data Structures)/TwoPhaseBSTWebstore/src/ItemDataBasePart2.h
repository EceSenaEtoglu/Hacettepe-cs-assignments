//
// Created by b2210356016 on 18.12.2022.
//

#ifndef ASM4_ITEMDATABASEPART2_H
#define ASM4_ITEMDATABASEPART2_H

#include <string>
#include <cstring>
#include "LLRBLT.h"
#include <vector>

class primaryNode2 {

    primaryNode2* left;
    primaryNode2* right;

    std::string searchKey;

    LLRBLT innerTree;

public:

    explicit primaryNode2(std::string key) : innerTree() {

        this->searchKey = key;
        left = nullptr;
        right = nullptr;
    }

    //returns string rep. of a category with specified rules in pdf
    std::string toString() {

        std::string s;
        s.append("\"");
        s.append(searchKey);

        if (innerTree.isEmpty()) {
            s.append("\":{}");
            return s;
        }

        else {

            s.append("\":\n");
            s.append(innerTree.getLevelOrderString());
            return s;

        }
    }

 friend class ItemDataBasePart2;
};


//uses a two phase binary search tree
// inner tree is LLRBLT

class ItemDataBasePart2 {

private:

    primaryNode2* root;


    //HELPERS
    static int compare(const std::string& key, primaryNode2* node) {

        return strcmp(key.c_str(),node->searchKey.c_str());
    }

    //return node with key as searchKey, if node does not exist return nullptr
    primaryNode2* search(primaryNode2* node,const std::string& searchKey);


public:

    ItemDataBasePart2() {
        root = nullptr;
    }

    ~ItemDataBasePart2() {

        clear(root);
    }


    //creates output for given database with given inputLines instructions
    static void init(ItemDataBasePart2,const std::vector<std::string>& inputLines, const std::string&fileName);

     //inserts given item to database
     //item.category is primaryKey
     //item.name is secondaryKey

    void insert(const StoreItem& item) {

        insert(root,item);
    }

    //removes the item with category as category and name itemName
    //does nothing if such item or category does not exist
    void remove(const std::string& category,const std::string& itemName);


    //updates item's price to newPrice
    //item is defined with category and name
    //does nothing if such item or category does not exist
    void updateData(const std::string& category,const std::string& name, int newPrice);

    std::string getStringOfAllItems();

    std::string getStringOfAllItemsInCategory(const std::string& category);

    std::string getStringOfItem(const std::string& primaryKey, const std::string& secondaryKey);


protected:

    void insert(primaryNode2*& node, const StoreItem& item);

    void clear(primaryNode2*& node);

};


#endif //ASM4_ITEMDATABASEPART2_H

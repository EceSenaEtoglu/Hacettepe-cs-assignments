//
// Created by b2210356016 on 18.12.2022.
//

#include <queue>
#include "ItemDataBasePart2.h"
#include "Helpers.h"

primaryNode2* ItemDataBasePart2::search(primaryNode2 *node, const std::string& searchKey) {

    //such node does not exist || node is found
    if(node == nullptr || (compare(searchKey,node) == 0)) {

        return node;
    }

    if (compare(searchKey,node) < 0) {

        return search(node->left,searchKey);
    }

    else if (compare(searchKey,node) > 0) {
        return search(node->right,searchKey);
    }
}


//primaryKey: item.category
void ItemDataBasePart2::insert(primaryNode2 *&node, const StoreItem& item) {

    //if node with search searchKey does not exist, create node
    if(node == nullptr) {

        node = new primaryNode2(item.category);

        //insert item to inner tree
        node->innerTree.insert(item);

        return;
    }

    std::string key = item.category;

    int comparison = strcmp(key.c_str(),node->searchKey.c_str());

    //node is found
    if (comparison == 0) {

        //add item to inner tree
        node->innerTree.insert(item);
        return;

    }
    //search for node
    if (comparison < 0) {

        insert(node->left,item);
    }

    else {

        insert(node->right,item);

    }
}

//searchs for node with primaryKey (main node)
//if node with searchKey (primaryKey) does not exist do nothing
// if node with secondaryKey exists in main node's inner tree. remove that node
void ItemDataBasePart2::remove(const std::string& primaryKey, const std::string& secondaryKey) {

    primaryNode2* temp = search(root,primaryKey);

    //if node with searchKey (primaryKey) does not exist do nothing
    if(temp == nullptr) {
        return;
    }

    temp->innerTree.deleteItem(secondaryKey);
}

void ItemDataBasePart2::updateData(const std::string& primaryKey, const std::string& secondaryKey, int newPrice)
{

    primaryNode2* temp = search(root,primaryKey);

    //if node with searchKey (primaryKey) does not exist do nothing
    if(temp == nullptr) {
        return;
    }

    temp->innerTree.updateData(secondaryKey,newPrice);
}


std::string ItemDataBasePart2::getStringOfAllItems() {

    if (root == nullptr) {
        return "{}";
    }

    std::string s;
    s.append("{\n");

    std::queue<primaryNode2*> nodeQ;
    nodeQ.push(root);


    while (!nodeQ.empty()) {

        primaryNode2* temp = nodeQ.front();
        s.append(temp->toString());
        s.append("\n");
        nodeQ.pop();

        if (temp->left != nullptr)
            nodeQ.push(temp->left);

        if (temp->right != nullptr)
            nodeQ.push(temp->right);
    }

    s.append("}");
    return s;
}


std::string ItemDataBasePart2::getStringOfAllItemsInCategory(const std::string& key) {

    primaryNode2* temp = search(root,key);

    //no such category exists
    if(temp == nullptr) {
        return "{}";
    }

    std::string s;
    s.append("{\n");
    s.append(temp->toString());
    s.append("\n}");

    return s;

}


std::string ItemDataBasePart2::getStringOfItem(const std::string &primaryKey, const std::string &secondaryKey) {

    primaryNode2 *temp = search(root, primaryKey);

    //if no such category exists
    if (temp == nullptr) {
        return "{}";
    }

    //no such item exists defined with category and name
    if (!temp->innerTree.contains(secondaryKey)) {
        return "{}";
    }


    //{
    //"clothes":
    //	"tie":"35"
    //}

    std::string s;
    s.append("{\n\"");
    s.append(primaryKey);
    s.append("\":\n\t");
    s.append(temp->innerTree.getStringOfNode(secondaryKey));
    s.append("\n}");
    return s;}



void ItemDataBasePart2::clear(primaryNode2 *&node) {

        if (node == nullptr) {
            return;
        }

        else {

            clear(node->left);
            clear(node->right);
            delete node;
            node = nullptr;
        }

        //innerRoot = nullptr;

}


void ItemDataBasePart2::init(ItemDataBasePart2 database, const std::vector<std::string> &inputLines, const std::string &fileName) {

    int i = 0;

    for(const auto& line: inputLines) {

        i++;

        std::vector<std::string> inputV = Helpers::split(line);

        std::string commandId = inputV[0];

        if (commandId == "insert") {

            database.insert(StoreItem(inputV[1],inputV[2],std::stoi(inputV[3])));

        }

        else if (commandId == "updateData") {

            database.updateData(inputV[1],inputV[2],std::stoi(inputV[3]));
        }

        else if (commandId == "remove") {

            database.remove(inputV[1],inputV[2]);
        }

        else if (commandId == "printAllItems") {

            std::string text; text.append("command:");text.append(line);text.append("\n");

            text.append(database.getStringOfAllItems());

            Helpers::appendToFile(fileName,text);
        }

        else if (commandId == "printAllItemsInCategory") {

            std::string text; text.append("command:");text.append(line);text.append("\n");

            text.append(database.getStringOfAllItemsInCategory(inputV[1]));

            Helpers::appendToFile(fileName,text);
        }

        else if (commandId == "printItem" || commandId =="find") {

            std::string text; text.append("command:");text.append(line);text.append("\n");

            text.append(database.getStringOfItem(inputV[1],inputV[2]));

            Helpers::appendToFile(fileName,text);
        }

        else {

            std::string s; s.append("command on line:"); s.append(std::to_string(i));
            s.append("is not valid!");

            Helpers::appendToFile(fileName,s);
        }
    }
}



#include <queue>
#include "ItemDataBasePart1.h"
#include "Helpers.h"


void ItemDataBasePart1::clear(primaryNode1 *&node) {

    if(node == nullptr) {
        return;
    }

    else {

        clear(node->left);
        clear(node->right);
        delete node;
        node = nullptr;

    }
}

primaryNode1* ItemDataBasePart1::search(primaryNode1* node, const std::string& searchKey) {

    //such node does not exist || node is found
    if (node == nullptr || compare(searchKey,node) == 0) {

        return node;
    }

    if (compare(searchKey,node) <0) {

        return search(node->left,searchKey);
    }

    else if (compare(searchKey,node) > 0 ) {

        return search(node->right,searchKey);
    }
}


void ItemDataBasePart1::insert(primaryNode1 *&node, const StoreItem &item) {

    //node did not exist, and location is found
    if (node == nullptr) {

        //create new primary node with searchKey as item.category
        node = new primaryNode1(item.category);

        auto* secondaryNode = new avlNode(item);

        node->innerRoot = AVLT::insert(node->innerRoot,secondaryNode);

        return;

    }

    if(compare(item.category,node) == 0) {

        //append new secondary node
        auto* secondaryNode = new avlNode(item);

        node->innerRoot =  AVLT::insert(node->innerRoot,secondaryNode);

        return;
    }

    if (compare(item.category,node) <0) {

        insert(node->left,item);
    }

    else if (compare(item.category,node) >0 ) {

        insert(node->right,item);
    }
}

void ItemDataBasePart1::remove(const std::string &primaryKey, const std::string &secondaryKey) {

    primaryNode1* temp = search(root, primaryKey);

    //such primaryKey does not exist do nothing
    if(temp == nullptr) {
        return;
    }

    //safe delete
    if(AVLT::search(temp->innerRoot,secondaryKey) != nullptr) {

        //remove item, update root
        temp->innerRoot = AVLT::remove(temp->innerRoot,secondaryKey);
    }

}

void ItemDataBasePart1::updateData(const std::string &primaryKey, const std::string &secondaryKey,int newPrice){

    primaryNode1* temp = search(root, primaryKey);

    //such primaryKey does not exist
    if(temp == nullptr) {
        return;
    }

    avlNode* nodeToUpdate = AVLT::search(temp->innerRoot,secondaryKey);

    if (nodeToUpdate != nullptr) {

        nodeToUpdate->data = newPrice;
    }
}


std::string ItemDataBasePart1::getStringOfAllItems() {

    //empty tree
    if(root == nullptr) {
        return "{}";
    }

    std::queue<primaryNode1*> nodeQ;
    nodeQ.push(root);

    std::string str;
    str.append("{\n");

    while(!nodeQ.empty()) {

        primaryNode1* temp = nodeQ.front();
        str.append(temp->toString());
        str.append("\n");

        nodeQ.pop();

        //TODO WARNING

        if(temp->left != nullptr) {
            nodeQ.push(temp->left);
        }

        if(temp->right != nullptr) {
            nodeQ.push(temp->right);
        }
    }

    str.append("}");
    return str;

}

std::string ItemDataBasePart1::getStringOfAllItemsInCategory(const std::string &searchKey) {

    primaryNode1* temp = search(root, searchKey);

    //no such category exists
    if (temp == nullptr) {
        return "{}";
    }

    std::string s;
    s.append("{\n");
    s.append(temp->toString());
    s.append("\n}");
    return s;
}

std::string ItemDataBasePart1::getStringOfItem(const std::string &primaryKey,const std::string &secondaryKey) {

    primaryNode1* temp = search(root, primaryKey);

    if (temp == nullptr) {
        return "{}";
    }

    avlNode* node = AVLT::search(temp->innerRoot,secondaryKey);

    if(node == nullptr) {
        return "{}";
    }

    std::string s; s.append("{\n"); s.append("\"");s.append(primaryKey);s.append("\":\n");

    s.append("\t");s.append(node->toString());s.append("\n}");

    return s;

}

 void ItemDataBasePart1::init(ItemDataBasePart1 database,const std::vector<std::string>& inputLines, const std::string &fileName) {

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


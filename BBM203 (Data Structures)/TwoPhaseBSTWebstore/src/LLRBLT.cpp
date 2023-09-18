//
// Created by b2210356016 on 24.12.2022.
//

#include <queue>
#include "LLRBLT.h"


bool LLRBLT::isRed(nodeRBL *node) {

    return node != nullptr && node->isRed;

}

//toggles colors with childs
void LLRBLT::flipColors(nodeRBL* node) {

    node->isRed = !node->isRed;

    if (node->left != nullptr) {

        node->left->isRed = !node->left->isRed;
    }

    if (node->right != nullptr) {
        node->right->isRed = !node->right->isRed;
    }

}

nodeRBL *LLRBLT::rotateRight(nodeRBL *root) {

    //rotate
    nodeRBL *temp = root->left;
    root->left = temp->right;
    temp->right = root;

    //color fix
    temp->isRed = root->isRed;
    root->isRed = true;
    return temp;

}

nodeRBL *LLRBLT::rotateLeft(nodeRBL *root) {

    //rotate
    nodeRBL *temp = root->right;
    root->right = temp->left;

    //color fix
    temp->left = root;
    temp->isRed = root->isRed;
    root->isRed = true;

    return temp;
}

nodeRBL *LLRBLT::moveRedLeft(nodeRBL* node) {

    //toggle colors
    flipColors(node);

    if ((node->right != nullptr) && isRed(node->right->left))
    {
        node->right = rotateRight(node->right);
        node = rotateLeft(node);
        flipColors(node);
    }
    return node;
}

nodeRBL *LLRBLT::moveRedRight(nodeRBL *node) {

    //toggle colors
    flipColors(node);

    if (( node->left != nullptr) && isRed(node->left->left))
    {
        node = rotateRight(node);
        flipColors(node);
    }
    return node;

}

nodeRBL* LLRBLT::getMinNode(nodeRBL *node) {

    //search for min
    while (node->left != nullptr) {

        node = node->left;
    }

    return node;

}

nodeRBL* LLRBLT::deleteMinNode(nodeRBL *node) {

    //node has no childs
    //delete the node
    if (node->left == nullptr) {

        delete node;

        return nullptr;
    }

    //rearrange the tree
    if ((!isRed(node->left)) && (!isRed(node->left->left)))
    {
        node = moveRedLeft(node);
    }

    //reconnect
    node->left = deleteMinNode(node->left);

    return fixTree(node);
}


//main goal: move red links in the tree such that node we want to delete becomes red
nodeRBL* LLRBLT::deleteNode(nodeRBL *node, const std::string &searchKey) {

    if (compare(searchKey,node) < 0) {

        //important check! was not on Sedgewick slides !
        if(node->left != nullptr) {

            if (!isRed(node->left) && (!isRed(node->left->left))) {

                node = moveRedLeft(node); }

            node->left = deleteNode(node->left, searchKey);
        }
    }

    else {


        //make the right child red
        if (isRed(node->left)) {
            node = rotateRight(node);
        }

        //searched node is a leaf node
        if ((compare(searchKey,node) == 0) && (node->right == nullptr))
        {
            delete node;

            return nullptr;
        }

        //searched node has 2 childs
        if (node->right != nullptr) {

            if (!isRed(node->right) && (!isRed(node->right->left)))
            {
                node = moveRedRight(node);
            }

            if (compare(searchKey,node) == 0) {

                //find min node of right subtree
                nodeRBL* min = getMinNode(node->right);

                //swap node's info with min node of right subtree
                node->key = min->key;
                node->data = min->data;

                //delete min node
                node->right = deleteMinNode(node->right);
            }

            else {
                node->right = deleteNode(node->right, searchKey);
            }

        }

    }

    //fix the tree on the way up
    return fixTree(node);

}

nodeRBL* LLRBLT::fixTree(nodeRBL *node) {

    //lean left
    if (isRed(node->right)) {

        node = rotateLeft(node);
    }

    //two red links in a row
    if(isRed(node->left) && isRed(node->left->left)) {

        node = rotateRight(node);
    }

    //flip colors
    if(isRed(node->left) && isRed(node->right)) {

        flipColors(node);
    }

    return node;

}

nodeRBL* LLRBLT::insert(nodeRBL *node, const StoreItem &item) {

    //location is found
    if(node == nullptr) {

        //create the node
        return new nodeRBL(item);
    }

    //go left
    if(compare(item.name,node) < 0) {

        node->left = insert(node->left,item);
    }

        //go right
    else if (compare(item.name,node) > 0) {

        node->right = insert(node->right,item);
    }

    return fixTree(node);

}

nodeRBL *LLRBLT::search(nodeRBL*node,const std::string& searchKey) {

    //node does not exist || node is found
    if(node == nullptr || (compare(searchKey,node) == 0)) {

        return node;
    }

    if (compare(searchKey,node) < 0) {
        return search(node->left,searchKey);
    }

    else {
        return search(node->right,searchKey);
    }
}

//delete entire tree
void LLRBLT::clear(nodeRBL*& ptr) {

    if(ptr == nullptr) { return;}

    else {
        clear(ptr->left);
        clear(ptr->right);
        delete ptr;
        ptr = nullptr;
    }

}

std::string LLRBLT::getLevelOrderString(nodeRBL* root) {

    //if tree is empty
    if (root == nullptr)
        return "";

    // create queue
    std::queue<nodeRBL*> q;

    q.push(root);

    std::string str;

    while (!q.empty()) {

        //node count is for tracking nodes in current level
        int nodeCount = q.size();
        str.append("\t");


        while (nodeCount > 0) {

            nodeRBL* node = q.front();
            str.append(node->toString());
            str.append(",");

            q.pop();

            if (node->left != nullptr)
                q.push(node->left);
            if (node->right != nullptr)
                q.push(node->right);

            nodeCount--;
        }

        //delete last ","
        str.erase(str.size()-1,1);
        str.append("\n");

    }

    //remove last \n
    str.erase(str.size()-1,1);
    return str;
}
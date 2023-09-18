//
// Created by b2210356016 on 22.12.2022.
//

#include "AVLT.h"

avlNode* AVLT::rotateRight(avlNode* node) {

    //find
    avlNode *temp = node->left;
    avlNode *temp2 = temp->right;

    //rotate
    temp->right = node;
    node->left = temp2;

    return temp;
}

avlNode* AVLT::rotateLeft(avlNode *node) {

    //find
    avlNode *temp = node->right;
    avlNode *temp2 = temp->left;

    //rotate
    temp->left = node;
    node->right = temp2;


    return temp;
}


avlNode* AVLT::insert(avlNode * node, avlNode* newNode) {

    //position is located
    if (node == nullptr) {
        node = newNode;
        return node;
    }

    int cmp = compare(newNode,node);

    if (cmp < 0) {
        node -> left = insert(node -> left, newNode);
    }

    else if (cmp > 0) {
        node -> right = insert(node -> right, newNode);
    }


    int balanceFactor = getBalanceFactor(node);

    // case left left
    if (balanceFactor > 1 && (compare(newNode, node->left) < 0))
        return rotateRight(node);

    // case rr
    if (balanceFactor < -1 && (compare(newNode, node->right) > 0))
        return rotateLeft(node);

    // case lr
    if (balanceFactor > 1 && (compare(newNode, node->left) > 0)) {
        node -> left = rotateLeft(node -> left);
        return rotateRight(node);
    }

    // case rl
    if (balanceFactor < -1 && (compare(newNode, node->right) < 0)) {
        node -> right = rotateRight(node -> right);
        return rotateLeft(node);
    }

    /* return the node pointer */
    return node;

}


avlNode* AVLT::remove(avlNode *node, const std::string& key) {

    //PERFORM CLASSIC BST DELETION

    // base case
    if (node == nullptr) {
        return nullptr;
    }

    else if (compare(key,node) < 0) {

        node->left = remove(node->left,key);
    }

    else if (compare(key,node) > 0) {

        node->right = remove(node->right,key);
    }

    //node is located
    else {

        //node with only right child or no child
        if (node -> left == nullptr) {
            avlNode * temp = node -> right;
            delete node;
            return temp;
        }

        else if (node -> right == nullptr) {
            avlNode * temp = node -> left;
            delete node;
            return temp;
        }


        // node with two children
        else {
            // locate the inorder successor
            avlNode * temp = getMinNode(node->right);

            //swap data
            node ->key = temp->key;
            node->data = temp->data;

            // Delete the inorder successor
            node -> right = remove(node -> right, temp -> key);
        }
    }


    //BALANCE THE TREE

    int bf = getBalanceFactor(node);

    // left left imbalance // right rotation
    if (bf == 2 && getBalanceFactor(node -> left) >= 0)
        return rotateRight(node);


    // left right imbalance //
    else if (bf == 2 && getBalanceFactor(node -> left) == -1) {
        node -> left = rotateLeft(node -> left);
        return rotateRight(node);
    }

    // right right imbalance // left rotation
    else if (bf == -2 && getBalanceFactor(node -> right) <= 0)
        return rotateLeft(node);


    // left left imbalance // right left rotation
    else if (bf == -2 && getBalanceFactor(node -> right) == 1) {
        node-> right = rotateRight(node -> right);
        return rotateLeft(node);
    }

    return node;

}


avlNode *AVLT::getMinNode(avlNode * root) {

    avlNode* temp = root;

    while(temp->left != nullptr) {

        temp = temp->left;
    }

    return temp;

}

avlNode* AVLT::search(avlNode* node,const std::string& searchKey) {

    if (node == nullptr || (compare(searchKey,node) == 0))
        return node;

    else if ((compare(searchKey,node) < 0))
        return search(node -> left, searchKey);

    else
        return search(node->right, searchKey);
}


std::string AVLT::getLevelOrderString(avlNode* root) {

    //if tree is empty
    if (root == nullptr)
        return "";

    // create queue
    std::queue<avlNode*> q;

    q.push(root);

    std::string str;

    while (!q.empty()) {

        //node count is for tracking nodes in current level
        int nodeCount = q.size();
        str.append("\t");


        while (nodeCount > 0) {

            avlNode* node = q.front();
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

void AVLT::deleteTree(avlNode*& root) {

    if (root == nullptr) {
        return;
    }

    else {

        deleteTree(root->left);
        deleteTree(root->right);
        delete root;
        root = nullptr;
    }

}

//
// Created by b2210356016 on 18.12.2022.
//

#ifndef ASM4_STOREITEM_H
#define ASM4_STOREITEM_H

#include <string>
#include <utility>

class StoreItem {

public:

    const std::string category;
    const std::string name;
    int price;

    StoreItem(std::string category, std::string name, int price): category(std::move(category)), name(std::move(name)) {
        this->price = price;
    }


};

#endif //ASM4_STOREITEM_H

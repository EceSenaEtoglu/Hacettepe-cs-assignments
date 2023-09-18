//
// Created by b2210356016 on 14.11.2022.
//

#ifndef ASM2_APARTMENT_H
#define ASM2_APARTMENT_H
#include <string>
#include "FlatList.h"


class Apartment {

private:

    std::string name;
    int maxBandWidthValue;

public:

    FlatList flatList;

    // constructor
    Apartment(std::string apartmentName,int maxBandValue) {

        Apartment::name = apartmentName;
        Apartment::maxBandWidthValue = maxBandValue;
    }

    //GETTERS AND SETTERS
    int getMaxBandWidthValue() const {
        return maxBandWidthValue;
    }

    void setMaxBandWidthValue(int maxBandWidthValue) {
        Apartment::maxBandWidthValue = maxBandWidthValue;
    }

    const std::string &getName() const {
        return name;
    }
    void setName(const std::string &name) {
        Apartment::name = name;
    }


    //T(175\t
    std::string toString() {

        std::string s = "";

        s.append(name);
        s.append("(");
        s.append(std::to_string(maxBandWidthValue));
        s.append(")");
        s.append("\t");

        s.append(flatList.toString());

        return s;
    }

};


#endif //ASM2_APARTMENT_H

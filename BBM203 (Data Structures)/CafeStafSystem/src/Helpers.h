//
// Created by b2210356016 on 7.12.2022.
//

#ifndef ASM3V2_HELPERS_H
#define ASM3V2_HELPERS_H

#include <cmath>

class Helpers {

public:

    static float round(double num) {

        int c = lround(num*100.0);
        float b = c/100.0;
        return b;
    }
};

#endif //ASM3V2_HELPERS_H

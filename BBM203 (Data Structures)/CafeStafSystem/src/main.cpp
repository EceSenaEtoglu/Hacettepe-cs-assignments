#include <vector>
#include "Cashier.h"
#include "CoffeeShopModel1.h"
#include "CoffeeShopModel2.h"
#include "FileOperations.h"

using namespace std;

int main(int argc,char** argv) {

    string inputFileName = argv[1];
    string outputFileName = argv[2];

    int cashierNum;

    vector<Customer> incomingCustomersM1;
    vector<Customer> incomingCustomersM2;

    //GET DATA FROM FILE
    FileOperations::readFileData(inputFileName, cashierNum, incomingCustomersM1);
    incomingCustomersM2 = incomingCustomersM1;


    //MODEL 1
    CoffeeShopModel1 model1(cashierNum, incomingCustomersM1);
    model1.startModel1();
    model1.roundData();
    std::vector<double> outputs0 = model1.getOutputs();

    //write to file
    FileOperations::writeToFile(outputFileName, outputs0);


    //MODEL2
    CoffeeShopModel2 model2(cashierNum,incomingCustomersM2);
    model2.startModel2();
    model2.roundData();

    std::vector<double> outputs1 = model2.getOutputs();


    //write to file again
    FileOperations::writeToFile(outputFileName, outputs0,outputs1);

    return 0;
}

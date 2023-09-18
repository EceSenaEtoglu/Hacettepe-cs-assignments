#include "ItemDataBasePart2.h"
#include "ItemDataBasePart1.h"
#include "Helpers.h"

using namespace std;

int main(int argc,char** argv) {

    string inputFileName = argv[1];
    string outputPart1 = argv[2];
    string outputPart2 = argv[3];

    //get inputs
    std::vector<std::string> inputLines = Helpers::readFromFile(inputFileName);

    //start part1
    Helpers::truncateFile(outputPart1);
    ItemDataBasePart1 dataBasePart1;
    ItemDataBasePart1::init(dataBasePart1,inputLines,outputPart1);

    //start part2
    Helpers::truncateFile(outputPart2);
    ItemDataBasePart2 dataBasePart2;
    ItemDataBasePart2::init(dataBasePart2,inputLines,outputPart2);

    return 0;
}

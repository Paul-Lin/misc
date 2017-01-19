#include "operators.h"
#include <assert.h>

bool tina::Operators::left = true;
bool tina::Operators::right = false;
std::map<std::string,tina::Precedence*> tina::Operators::operators =std::map<std::string, tina::Precedence*>();

void tina::Operators::add(std::string name, int prec, bool leftAssoc) {
	Precedence precedence;
	precedence.value = prec;
	precedence.leftAssoc = leftAssoc;
	operators.insert(std::make_pair(name, &precedence));
}

void test_operators() {
	tina::Operators op;
	op.add(std::string("<"), 2, true);
	printf("======= test ===========");
}
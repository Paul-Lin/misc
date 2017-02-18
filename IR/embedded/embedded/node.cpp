#include "node.h"

void tina::VectMathNode::print() {
	std::string str = (token != nullptr) ? token->toString() : "<null>";
	std::cout << str ;
}

void tina::AssignNode::print() {
	id->print();
	std::cout << " = ";
	value->print();
	std::cout << std::endl;
}

void tina::AddNode::print() {
	left->print();
	std::cout << "+" ;
	right->print();
}
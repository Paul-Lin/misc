#include "node.h"

void tina::VectNode::print() {
	std::string str = (token != nullptr) ? token->toString() : "<null>";
	std::cout << str << std::endl;
}
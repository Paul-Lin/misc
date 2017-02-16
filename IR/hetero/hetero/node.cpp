#include "node.h"

tina::AddNode::AddNode(tina::ExprNode* l, tina::Token* addToken, tina::ExprNode* r) :ExprNode(addToken), left(l), right(r) {
}

std::string tina::AddNode::toStringTree() {
	if (left == nullptr || right == nullptr)
		return this->toStringTree();
	std::string str;
	str = str + "(";
	str = str + this->toString();
	str = str + " ";
	str = str + left->toStringTree();
	str = str + " ";
	str = str + right->toStringTree();
	str = str + ")";
	return str;
}

void tina::ExprNode::walk() {
	std::cout << token->toString() << std::endl;
}

void tina::IntNode::walk() {
	std::cout << token->toString() <<" ";
}

void tina::AddNode::walk() {
	left->walk();

	right->walk();

	std::cout << token->toString() << " ";

	std::cout << std::endl;
}
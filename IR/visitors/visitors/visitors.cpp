#include "visitors.h"

void tina::VecMathVisitor::visit(tina::AddNode* node) {
	node->left->print(this);
	std::cout << " + ";
	node->right->print(this);
}

void tina::VecMathVisitor::visit(tina::AssignNode* node) {
	node->id->print(this);
	std::cout << " = ";
	node->value->print(this);
	std::cout << std::endl;
}

void tina::VecMathVisitor::visit(tina::IntNode* node) {
	std::cout << node->toString();
}

void tina::VecMathVisitor::visit(tina::VarNode* node) {
	std::cout << node->toString();
}

void tina::IntNode::print(VecMathVisitor *visitor){ visitor->visit(this); }
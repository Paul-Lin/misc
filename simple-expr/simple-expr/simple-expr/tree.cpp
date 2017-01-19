#include "tree.h"

tina::Tree* tina::Tree::createTree(int op, Type& type, Tree& left, Tree& right) {
	auto tree = new Tree();
	tree->kids[0] = &left;
	tree->kids[1] = &right;
	tree->type = &type;
	tree->op = op;
	return tree;
}

tina::Tree* tina::Tree::expr(int k) {
	auto tree = new Tree();
	return tree;
}

void tina::Tree::factor() {

}

int tina::Tree::getToken() {
	return 0;
}

tina::Tree* tina::Tree::unary() {
	auto tree = new Tree();
	switch (t) {
	case '*':
		break;
	case '-':
		break;
	case '+':
		break;
	case '/':
		break;
	case '(':
		t = getToken();
		break;
	}
	return tree;
}
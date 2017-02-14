#include "ast.h"

std::string tina::Token::toString() {
	return text;
}

int tina::Ast::getNodeType() {
	return token->type;
}

void tina::Ast::addChild(Ast* t) {
	if (children == NULL) {
		children = new std::vector<Ast*>();
	}
	children->push_back(t);
}

bool tina::Ast::isNil() {
	return token == NULL;
}

std::string tina::Ast::toString() {
	return token != NULL ? token->toString() : "nil";
}

std::string tina::Ast::toStringTree() {
	if (children == NULL || children->size() == 0)
		return this->toString();
	std::string str;
	if (!isNil()) {
		str = str + "(";
		str = str + this->toString();
		str = str + " ";
	}
	
	for (size_t i = 0; i < children->size(); i++) {
		Ast *t = children->at(i);
		if (i > 0)
			str = str + " ";
		str = str + t->toStringTree();
	}
	if (!isNil())
		str = str + ")";
	return str;
}
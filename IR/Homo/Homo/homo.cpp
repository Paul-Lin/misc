#include "homo.h"

t::Ast::Ast() {
	children = new std::vector<Ast*>();
	token = NULL;
}

t::Ast::Ast(Token* tk):token(tk) {
	children = new std::vector<Ast*>();
}
inline bool t::Ast::isNil() {
	return token == NULL;
}

std::string t::Ast::toString() {
	return isNil() ? "nil" : token->text;
}

void t::Ast::addChild(t::Ast* t) {
	children->push_back(t);
}

std::string t::Ast::toStringTree() {
	if (children == NULL||children->size() == 0)
		return toString();
	std::string str;
	
	if (!isNil()) {
		str = str + "(";
		str = str+ this->toString();
		str = str + " ";		
	}

	for (size_t i = 0; i < children->size(); i++) {
		Ast* t = children->at(i);
		str = str + " ";
		str = str + t->toStringTree();
	}
	
	if (!isNil()) {
		str = str + ")";
	}
	return str;
}


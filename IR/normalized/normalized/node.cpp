#include "node.h"

std::string tina::ExprNode::toString() {
	if (evalType != T_INVALID) {
		return Ast::toString() + "<type=" + (evalType == T_INTEGER ? "T_INTEGER" : "T_VECTOR") + ">";
	}
	return Ast::toString();
}


tina::AddNode::AddNode(tina::ExprNode* left, tina::Token* addToken, tina::ExprNode* right):ExprNode(addToken){
	ExprNode::addChild(left);
	ExprNode::addChild(right);
}

int tina::AddNode::getEvalType() {
	ExprNode* left = (ExprNode*)list->at(0);
	ExprNode* right = (ExprNode*)list->at(1);
	if (left->getEvalType() == T_INTEGER&&right->getEvalType() == T_INTEGER)
		return T_INTEGER;
	if (left->getEvalType() == T_VECTOR&&right->getEvalType() == T_VECTOR)
		return T_VECTOR;
	return T_INVALID;
}


tina::VectorNode::VectorNode(tina::Token* t, std::vector<ExprNode*> elements):ExprNode::ExprNode(t) {
	ExprNode::evalType = T_VECTOR;
	for (ExprNode* e : elements) {
		addChild(e);
	}
}
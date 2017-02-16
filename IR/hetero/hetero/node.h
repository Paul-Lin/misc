#include "hetero.h"

namespace tina {
	class ExprNode :public HeteroAst{
	public:
		ExprNode(Token* t):HeteroAst(t){}
	};

	class IntNode :public ExprNode {
	public:
		IntNode(Token* t):ExprNode(t){}
	};

	class AddNode :public ExprNode {
	public:
		AddNode(ExprNode* l, Token* addToken, ExprNode* r);
		std::string toStringTree();
	private:
		ExprNode* left;
		ExprNode* right;

	};
}
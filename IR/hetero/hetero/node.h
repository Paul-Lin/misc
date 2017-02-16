#include "hetero.h"

namespace tina {
	class ExprNode :public HeteroAst{
	public:
		ExprNode(Token* t):HeteroAst(t){}
		virtual void walk();
	};

	class IntNode :public ExprNode {
	public:
		IntNode(Token* t):ExprNode(t){}
		void walk();
	};

	class AddNode :public ExprNode {
	public:
		AddNode(ExprNode* l, Token* addToken, ExprNode* r);
		std::string toStringTree();
		void walk();
	private:
		ExprNode* left;
		ExprNode* right;

	};
}